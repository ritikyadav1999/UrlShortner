package org.example.urlshortner.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.urlshortner.dto.CreateShortUrlRequest;
import org.example.urlshortner.entity.ShortUrl;
import org.example.urlshortner.security.CustomUserDetail;
import org.example.urlshortner.service.ShortUrlService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/urls")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    @PostMapping("/short")
    public ShortUrl createShortUrl(
            @Valid @RequestBody CreateShortUrlRequest request,
            @AuthenticationPrincipal CustomUserDetail userDetails
            ) {
        System.out.println("checkpoint : CreateShortUrl Controller");
        return shortUrlService.createShortUrl(request,userDetails.getUser());
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> getShortUrl(@PathVariable String shortCode) {
        String originalUrl = shortUrlService.getOriginalUrl(shortCode);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

    @GetMapping()
    public Page<ShortUrl> getUserUrls(@AuthenticationPrincipal CustomUserDetail userDetail,
                                      @PageableDefault(size=10,sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable) {
        return shortUrlService.getUserUrls(userDetail.getUser().getId(), pageable);
    }


}
