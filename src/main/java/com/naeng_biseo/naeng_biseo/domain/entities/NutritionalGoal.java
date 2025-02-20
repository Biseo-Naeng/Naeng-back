package com.naeng_biseo.naeng_biseo.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "NutritionalGoals")
public class NutritionalGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer goalId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private Integer calories;
    private Integer carbohydrates;
    private Integer protein;
    private Integer fat;
    private Integer sugar;
    private Integer sodium;
}

