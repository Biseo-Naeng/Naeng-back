package com.naeng_biseo.naeng_biseo.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "WaterIntake")
public class WaterIntake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer waterIntakeId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private Integer targetAmount;
    private Integer consumedAmount;

}
