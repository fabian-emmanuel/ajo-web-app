package com.codewithfibbee.ajowebapp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Contribution implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="users_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "contribution_cycle_id", nullable = false)
    private ContributionCycle contributionCycle;

    @Column(nullable = false)
    private double amountPaid;

    @Column(nullable = false)
    private Date datePaid;
}
