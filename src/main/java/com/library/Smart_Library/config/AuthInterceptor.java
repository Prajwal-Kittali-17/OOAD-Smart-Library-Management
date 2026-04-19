package com.library.Smart_Library.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Owner: Prajwal Kittali
 * SRN: PES2UG23CS419
 * Purpose: Blocks protected routes when user session is not authenticated.
 * GRASP: Controller support by centralizing access control at web boundary.
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    HttpSession session = request.getSession(false);
    boolean loggedIn = session != null && session.getAttribute("loggedInUser") != null;
    if (loggedIn) {
      return true;
    }

    response.sendRedirect(request.getContextPath() + "/login?authRequired=true");
    return false;
  }
}
