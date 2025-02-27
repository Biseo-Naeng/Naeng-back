package com.naeng_biseo.naeng_biseo.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "recipeId")
    private Recipe recipe;

    @Column(length = 32)
    private String imageName;

    @Column(length = 256)
    private String imagePath;

}

