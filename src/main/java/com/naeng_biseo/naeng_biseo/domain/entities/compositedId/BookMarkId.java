package com.naeng_biseo.naeng_biseo.domain.entities.compositedId;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookMarkId implements Serializable {

    private Integer recipeId;
    private Integer userId;

    public BookMarkId() {}

    public BookMarkId(Integer recipeId, Integer userId) {
        this.recipeId = recipeId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookMarkId)) return false;
        BookMarkId that = (BookMarkId) o;
        return Objects.equals(recipeId, that.recipeId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, userId);
    }
}
