package com.naeng_biseo.naeng_biseo.domain.entities;

import com.naeng_biseo.naeng_biseo.domain.entities.compositedId.BookMarkId;
import jakarta.persistence.*;

@Entity
@Table(name = "BookMark")
public class BookMark {

    @EmbeddedId
    private BookMarkId id;

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("recipeId")
    @JoinColumn(name = "recipeId")
    private Recipe recipe;

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId")
    private User user;

}

