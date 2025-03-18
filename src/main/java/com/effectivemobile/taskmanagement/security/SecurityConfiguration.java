package com.effectivemobile.taskmanagement.security;

import com.effectivemobile.taskmanagement.service.AccountServiceImpl;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
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
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
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
                .requestMatchers("/**").permitAll())
//            .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
//            .requestMatchers(HttpMethod.GET, "/profiles/**").authenticated()
//            .requestMatchers(HttpMethod.PUT, "/profiles/**").authenticated()
//            .requestMatchers(HttpMethod.DELETE, "/profiles/**").authenticated())
        .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    var authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(accountService.userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }
}
