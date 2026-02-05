package com.rewards_service.infrastructure.config

import io.github.bucket4j.Bucket
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class RateLimitInterceptor(private val bucket: Bucket?) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // When no Bucket is provided (e.g., local profile), skip rate limiting
        if (bucket == null) return true

        val probe = bucket.tryConsumeAndReturnRemaining(1)
        return if (probe.isConsumed) {
            response.addHeader("X-Rate-Limit-Remaining", probe.remainingTokens.toString())
            true
        } else {
            response.status = HttpStatus.TOO_MANY_REQUESTS.value()
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", (probe.nanosToWaitForRefill / 1_000_000_000).toString())
            false
        }
    }
}