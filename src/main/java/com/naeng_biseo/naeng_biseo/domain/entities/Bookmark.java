package com.naeng_biseo.naeng_biseo.domain.entities;

import jakarta.persistence.*
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BookMark")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {
    @EmbeddedId
    private BookmarkId id;
}
