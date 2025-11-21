package com.rewards_service.infrastructure.config;

import com.rewards_service.application.service.UserService;
import com.rewards_service.domain.model.UserAccount;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // Skip API key authentication for public endpoints
        if (isPublicEndpoint(requestURI, request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader("X-API-Key");
        if (apiKey == null) {
            // Try alternative header names
            apiKey = request.getHeader("x-api-key");
        }
        if (apiKey == null) {
            apiKey = request.getHeader("X-Api-Key");
        }

        if (apiKey != null && !apiKey.trim().isEmpty()) {
            String trimmedApiKey = apiKey.trim();
            Optional<UserAccount> userOpt = userService.findByApiKey(trimmedApiKey);
            if (userOpt.isPresent()) {
                UserAccount user = userOpt.get();
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Log when API key not found
                System.out.println("API key not found: " + trimmedApiKey);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String requestURI, HttpServletRequest request) {
        return requestURI.equals("/") ||
               requestURI.startsWith("/swagger-ui/") ||
               requestURI.startsWith("/v3/api-docs") ||
               requestURI.equals("/swagger-ui.html") ||
               requestURI.startsWith("/webjars/") ||
               requestURI.startsWith("/swagger-resources/") ||
               requestURI.startsWith("/actuator/") ||
               (requestURI.equals("/api/users") && "POST".equalsIgnoreCase(request.getMethod()));
    }
}