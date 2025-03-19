package com.effectivemobile.taskmanagement.security;

import com.effectivemobile.taskmanagement.service.AccountServiceImpl;
import com.effectivemobile.taskmanagement.utils.Role;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfiguration {
  private final AccountServiceImpl accountService;

  private final AuthTokenFilter authTokenFilter;

  public SecurityConfiguration(AccountServiceImpl accountService, AuthTokenFilter authTokenFilter) {
    this.accountService = accountService;
    this.authTokenFilter = authTokenFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(request -> {
          var corsConfiguration = new CorsConfiguration();
          corsConfiguration.setAllowedOriginPatterns(List.of("*"));
          corsConfiguration.setAllowedHeaders(List.of("*"));
          corsConfiguration.setAllowCredentials(true);
          return corsConfiguration;
        }))
        .authorizeHttpRequests(request -> request
            .requestMatchers("/swagger-ui/**", "/swagger-ui.html",
                "/swagger-resources/*", "/v3/api-docs/**").permitAll()
            .requestMatchers("/auth/sign-in", "/auth/sign-up").permitAll()
            .requestMatchers("/auth/refresh").authenticated()
            .requestMatchers(HttpMethod.GET, "/tasks").authenticated()
            .requestMatchers(HttpMethod.PUT, "/tasks/*").authenticated()
            .requestMatchers(antMatcher("/tasks/performer/**"), antMatcher("/tasks/author/**"))
            .hasAuthority(Role.ROLE_ADMIN.getRole())
            .requestMatchers(HttpMethod.POST, "/tasks")
            .hasAuthority(Role.ROLE_ADMIN.getRole())
            .requestMatchers(HttpMethod.DELETE, "/tasks/**")
            .hasAuthority(Role.ROLE_ADMIN.getRole())
            .requestMatchers(HttpMethod.GET, "/tasks/*/comments").authenticated()
            .requestMatchers(HttpMethod.POST, "/tasks/*/comments").authenticated()
            .requestMatchers(HttpMethod.PUT, "/tasks/comments/**").authenticated()
            .requestMatchers(HttpMethod.DELETE, "/tasks/comments/**")
            .hasAuthority(Role.ROLE_ADMIN.getRole()))
            .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
            .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    var authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(accountService.userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(authProvider);
  }
}
