package com.effectivemobile.taskmanagement.security;

import com.effectivemobile.taskmanagement.model.Account;
import com.effectivemobile.taskmanagement.service.AccountServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
  public static final String BEARER_PREFIX = "Bearer ";
  public static final String HEADER_NAME = "Authorization";

  private final JwtService jwtService;
  private final AccountServiceImpl accountService;

  public AuthTokenFilter(JwtService jwtService, AccountServiceImpl accountService) {
    this.jwtService = jwtService;
    this.accountService = accountService;
  }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    var authHeader = request.getHeader(HEADER_NAME);
    if (authHeader == null || !StringUtils.startsWithIgnoreCase(authHeader,
        BEARER_PREFIX)) {
      filterChain.doFilter(request, response);
      return;
    }
    var jwt = authHeader.substring(BEARER_PREFIX.length());
    var email = jwtService.extractEmail(jwt);
    if (!email.isEmpty() && SecurityContextHolder.getContext()
        .getAuthentication() == null) {
      Account account = accountService.loadUserByEmail(email);
      if (jwtService.isTokenValid(jwt, account)) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            account,
            null,
            account.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
      }
    }
    filterChain.doFilter(request, response);
  }
}
