package com.naeng_biseo.naeng_biseo.domain.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkId implements Serializable {
    private Integer recipeId;
    private Integer userId;
}
