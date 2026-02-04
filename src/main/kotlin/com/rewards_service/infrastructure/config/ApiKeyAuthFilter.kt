package com.rewards_service.infrastructure.config

import com.rewards_service.application.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class ApiKeyAuthFilter(private val userService: UserService) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestURI = request.requestURI

        if (isPublicEndpoint(requestURI, request)) {
            filterChain.doFilter(request, response)
            return
        }

        var apiKey: String? = request.getHeader("X-API-Key")
        if (apiKey == null) {
            apiKey = request.getHeader("x-api-key")
        }
        if (apiKey == null) {
            apiKey = request.getHeader("X-Api-Key")
        }

        if (apiKey != null && !apiKey.trim { it <= ' ' }.isEmpty()) {
            val trimmedApiKey = apiKey.trim { it <= ' ' }
            val userOpt = userService.findByApiKey(trimmedApiKey)
            if (userOpt.isPresent) {
                val user = userOpt.get()
                val authentication = UsernamePasswordAuthenticationToken(user, null, null)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            } else {
                println("API key not found: $trimmedApiKey")
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun isPublicEndpoint(requestURI: String, request: HttpServletRequest): Boolean {
        return requestURI == "/" ||
                requestURI.startsWith("/swagger-ui/") ||
                requestURI.startsWith("/v3/api-docs") ||
                requestURI == "/swagger-ui.html" ||
                requestURI.startsWith("/webjars/") ||
                requestURI.startsWith("/swagger-resources/") ||
                requestURI.startsWith("/actuator/") ||
                (requestURI == "/api/users" && "POST".equals(request.method, ignoreCase = true))
    }
}
