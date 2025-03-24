package com.naeng_biseo.naeng_biseo.repository;

import com.naeng_biseo.naeng_biseo.domain.entities.Bookmark;
import com.naeng_biseo.naeng_biseo.domain.entities.BookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId> {
    List<Bookmark> findByIdUserId(Integer userId);
}
