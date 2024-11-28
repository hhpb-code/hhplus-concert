import http from 'k6/http';
import {check, sleep} from 'k6';

export const options = {
  scenarios: {
    queue_test: {
      executor: 'ramping-vus',
      startVUs: 0, // 초기 사용자 0명
      stages: [
        {duration: '30s', target: 500},  // 30초 동안 500명까지 증가
        {duration: '1m', target: 2000},  // 1분 동안 2000명까지 증가
        {duration: '1m', target: 5000},  // 1분 동안 5000명까지 증가
        {duration: '30s', target: 5000}, // 30초 동안 5000명 유지
        {duration: '1m', target: 0},     // 1분 동안 0명으로 감소
      ],
    },
  },
};

export default function () {
  // Step 1: 대기열 토큰 발급
  const createResponse = http.post(
      'http://host.docker.internal:8080/api/v1/waiting-queues/tokens',
      {
        headers: {
          'Content-Type': 'application/json',
        },
      }
  );

  check(createResponse, {
    'Token creation status is 201': (r) => r.status === 201,
  });

  let tokenId = createResponse.json()?.waitingQueue.uuid;

  // Step 2: 5초 간격으로 대기열 조회
  let waitingQueue = null;

  while (true) {
    const queryResponse = http.get(
        'http://host.docker.internal:8080/api/v1/waiting-queues/position',
        {
          headers: {
            'Content-Type': 'application/json',
            'X-Waiting-Queue-Token-uuid': tokenId,
          },
        }
    );

    check(queryResponse, {
      'Query status is 200': (r) => r.status === 200,
    });

    waitingQueue = queryResponse.json().waitingQueue;

    if (waitingQueue?.position === 0) {
      console.log(`Token ${tokenId} entered the queue.`);
      break;
    }

    // 5초 대기
    sleep(5);
  }
}
