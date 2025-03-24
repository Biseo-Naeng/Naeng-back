package com.naeng_biseo.naeng_biseo.service;

import com.naeng_biseo.naeng_biseo.domain.entities.Bookmark;
import com.naeng_biseo.naeng_biseo.domain.entities.BookmarkId;
import com.naeng_biseo.naeng_biseo.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final RecipeService recipeService; // 상세 조회를 위해

    public void addBookmark(Integer userId, Integer recipeId) {
        BookmarkId id = new BookmarkId(recipeId, userId);
        if(bookmarkRepository.existsById(id)) {
            throw new RuntimeException("이미 북마크 되어있습니다.");
        }
        Bookmark bookmark = new Bookmark(id);
        bookmarkRepository.save(bookmark);
    }

    public void removeBookmark(Integer userId, Integer recipeId) {
        BookmarkId id = new BookmarkId(recipeId, userId);
        if(!bookmarkRepository.existsById(id)) {
            throw new RuntimeException("북마크가 존재하지 않습니다.");
        }
        bookmarkRepository.deleteById(id);
    }

    public List<?> getBookmarksForUser(Integer userId) {
        List<Bookmark> bookmarks = bookmarkRepository.findByIdUserId(userId);
        // 각 북마크에 대해 상세 레시피 정보를 조회하여 반환
        return bookmarks.stream()
                .map(b -> recipeService.getRecipeDetail(b.getId().getRecipeId()))
                .collect(Collectors.toList());
    }
}
