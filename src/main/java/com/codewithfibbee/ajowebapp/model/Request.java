package com.codewithfibbee.ajowebapp.model;

import com.codewithfibbee.ajowebapp.enums.RequestMessage;
import com.codewithfibbee.ajowebapp.enums.RequestStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Request implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Date dateOfRequest;

    @ManyToOne
    @JoinColumn(name = "contribution_cycle_id", nullable = false)
    private ContributionCycle contributionCycle;

    @Column(nullable = false)
    private RequestStatus requestStatus;

    @Column(nullable = false)
    private RequestMessage requestMessage;

}
