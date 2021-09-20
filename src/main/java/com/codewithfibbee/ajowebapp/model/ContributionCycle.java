package com.codewithfibbee.ajowebapp.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
public class ContributionCycle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double monthlyContributionAmount;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private Date paymentStartDate;

    @Column(nullable = false)
    private Date paymentEndDate;

    @OneToMany(mappedBy = "contributionCycle", cascade = ALL)
    private List<Contribution> contributions;

    @OneToMany(mappedBy = "contributionCycle", cascade = ALL)
    private List<Request> requests;

}
