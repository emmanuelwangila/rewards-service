package com.rewards_service.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private UserAccount userAccount; // <-- change here

    private long points;

    private double balance; // money credited from redemptions

    // Convenience constructor
    public Wallet(UserAccount userAccount) {
        this.userAccount = userAccount;
        this.points = 0L;
        this.balance = 0.0;
    }
}
