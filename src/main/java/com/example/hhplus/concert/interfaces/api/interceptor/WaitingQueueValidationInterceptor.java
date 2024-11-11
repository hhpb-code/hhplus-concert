package com.example.hhplus.concert.interfaces.api.interceptor;

import com.example.hhplus.concert.application.WaitingQueueFacade;
import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.interfaces.api.support.CommonHttpHeader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

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

    waitingQueueFacade.validateWaitingQueueProcessing(waitingQueueTokenUuid);

    return true;
  }
}
