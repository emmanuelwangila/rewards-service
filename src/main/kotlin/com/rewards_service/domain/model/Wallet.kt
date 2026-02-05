package com.rewards_service.domain.model

import jakarta.persistence.*

@Entity
class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToOne
    var userAccount: UserAccount? = null

    var points: Long = 0L

    var balance: Double = 0.0

    constructor()

    constructor(userAccount: UserAccount) {
        this.userAccount = userAccount
        this.points = 0L
        this.balance = 0.0
    }
}
