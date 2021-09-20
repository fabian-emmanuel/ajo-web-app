package com.codewithfibbee.ajowebapp.model;

import lombok.Data;

import java.util.Date;

@Data
public class MemberContributionCycle {
    private User user;
    private ContributionCycle contributionCycle;
    private int slot;
    private Date dateJoined;

}
