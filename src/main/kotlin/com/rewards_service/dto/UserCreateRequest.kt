package com.rewards_service.dto

import com.rewards_service.domain.model.UserTier
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

class UserCreateRequest {

    @NotBlank
    var externalUserId: String? = null

    @NotBlank
    var displayName: String? = null

    @Email
    @NotBlank
    var email: String? = null

    @NotNull
    var tier: UserTier? = null

    constructor()

    constructor(externalUserId: String?, displayName: String?, email: String?, tier: UserTier?) {
        this.externalUserId = externalUserId
        this.displayName = displayName
        this.email = email
        this.tier = tier
    }
}
