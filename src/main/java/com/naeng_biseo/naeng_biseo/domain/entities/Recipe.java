package com.naeng_biseo.naeng_biseo.domain.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recipeId;

    @Column(length = 50)
    private String title;

    @Lob
    private String instructions;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Temporal(TemporalType.DATE)
    private Date writeDate;

    @Column
    private Integer view = 0;

}
