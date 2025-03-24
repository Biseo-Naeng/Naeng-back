package com.naeng_biseo.naeng_biseo.controller;

import com.naeng_biseo.naeng_biseo.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<String> addBookmark(@RequestParam Integer userId, @RequestParam Integer recipeId) {
        bookmarkService.addBookmark(userId, recipeId);
        return ResponseEntity.ok("북마크가 추가되었습니다.");
    }
  
    @DeleteMapping
    public ResponseEntity<String> removeBookmark(@RequestParam Integer userId, @RequestParam Integer recipeId) {
        bookmarkService.removeBookmark(userId, recipeId);
        return ResponseEntity.ok("북마크가 제거되었습니다.");
    }

    // 특정 사용자 북마크 목록 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<?>> getBookmarks(@PathVariable Integer userId) {
        return ResponseEntity.ok(bookmarkService.getBookmarksForUser(userId));
    }
}
