package org.example.urlshortner.repository;

import org.example.urlshortner.entity.ShortUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShortUrlRepo extends JpaRepository<ShortUrl,Long> {
      Optional<ShortUrl> findByShortCode(String shortCode);

      Page<ShortUrl> findByUser_Id(UUID userId, Pageable pageable);


}
