package com.example.hhplus.concert.interfaces.api.interceptor;

import com.example.hhplus.concert.application.WaitingQueueFacade;
import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.interfaces.api.support.CommonHttpHeader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

@Component
@RequiredArgsConstructor
public class WaitingQueueValidationInterceptor implements HandlerInterceptor {

  private final WaitingQueueFacade waitingQueueFacade;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String waitingQueueTokenUuid = request.getHeader(CommonHttpHeader.X_WAITING_QUEUE_TOKEN_UUID);

    if (waitingQueueTokenUuid == null || waitingQueueTokenUuid.isEmpty()) {
      throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_TOKEN_UUID_REQUIRED);
    }

    Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(
        HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

    if (pathVariables.containsKey("concertId")) {
      Long concertId = Long.parseLong(pathVariables.get("concertId"));
      waitingQueueFacade.validateWaitingQueueProcessingAndConcertId(waitingQueueTokenUuid,
          concertId);
    } else if (pathVariables.containsKey("concertScheduleId")) {
      Long concertScheduleId = Long.parseLong(pathVariables.get("concertScheduleId"));
      waitingQueueFacade.validateWaitingQueueProcessingAndScheduleId(waitingQueueTokenUuid,
          concertScheduleId);
    } else if (pathVariables.containsKey("concertSeatId")) {
      Long concertSeatId = Long.parseLong(pathVariables.get("concertSeatId"));
      waitingQueueFacade.validateWaitingQueueProcessingAndSeatId(waitingQueueTokenUuid,
          concertSeatId);
    } else {
      throw new CoreException(ErrorType.INVALID_REQUEST);
    }

    return true;
  }
}
