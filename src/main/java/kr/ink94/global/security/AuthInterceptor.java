package kr.ink94.global.security;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

  private final JwtProvider jwtProvider;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    if (!(handler instanceof HandlerMethod)) {
      return true;
    }

    HandlerMethod handlerMethod = (HandlerMethod) handler;
    boolean hasLoginRequired = handlerMethod.getMethodAnnotation(LoginRequired.class) != null
        || handlerMethod.getBeanType().getAnnotation(LoginRequired.class) != null;

    if (!hasLoginRequired) {
      return true; // No authentication required, allow the request to proceed
    }

    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
      return false; // Unauthorized, stop the request
    }

    String token = authHeader.substring(7); // Extract the token from the header
    if (jwtProvider.validateToken(token) == false) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token");
      return false; // Unauthorized, stop the request
    }

    Claims claims = jwtProvider.getClaims(token);
    Number memberIdNumber = claims.get("id", Number.class);
    String memberId = claims.getSubject();

    if (memberIdNumber != null) {
      request.setAttribute("id", memberIdNumber.longValue());
      request.setAttribute("memberId", memberId);
    }
    return true; // Allow the request to proceed
  }

}
