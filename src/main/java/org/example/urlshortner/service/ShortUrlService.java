package org.example.urlshortner.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.urlshortner.dto.CreateShortUrlRequest;
import org.example.urlshortner.entity.ShortUrl;
import org.example.urlshortner.entity.User;
import org.example.urlshortner.repository.ShortUrlRepo;
import org.example.urlshortner.repository.UserRepo;
import org.example.urlshortner.util.NanoIdGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShortUrlService {

    private final ShortUrlRepo shortUrlRepo;
    private final UserRepo userRepo;
    private final NanoIdGenerator nanoIdGenerator;
    private final RedisTemplate<String, String> redisTemplate;

    public ShortUrl createShortUrl(CreateShortUrlRequest request, User user) {

        System.out.println("Checkpoint : createShortUrl Service");

        String shortCode = "";
        int maxAttempts = 5;
        int attempts = 0;
        while (attempts < maxAttempts) {
            shortCode = nanoIdGenerator.generateNanoId();
            attempts++;
            boolean isEmpty = shortUrlRepo.findByShortCode(shortCode).isEmpty();
            if(isEmpty)
                break;
        }

        if(attempts > maxAttempts){
            throw new RuntimeException("unable to generate shortCode. pls try again");
        }

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(request.originalUrl());
        shortUrl.setDescription(request.description());
        shortUrl.setClickCount(0L);
        shortUrl.setShortCode(shortCode);
        shortUrl.setUser(user);

        return  shortUrlRepo.save(shortUrl);

    }

    @Transactional
    public String getOriginalUrl(String shortCode){
        Optional<ShortUrl> shortUrlOptional = shortUrlRepo.findByShortCode(shortCode);
        if(shortUrlOptional.isEmpty()){
            throw new RuntimeException("Url does not exist");
        }

        String redisKey = "shortUrl:" + shortCode;
        String cachedValue = redisTemplate.opsForValue().get(redisKey);

        if(cachedValue != null){
            System.out.println("Cache Hit");
            return cachedValue;
        }
        else
            System.out.println("Cache Miss");

        ShortUrl shortUrl = shortUrlOptional.get();
        shortUrl.setClickCount(shortUrl.getClickCount()+1);

        redisTemplate.opsForValue().set(redisKey,shortUrl.getOriginalUrl());

        return  shortUrlRepo.save(shortUrl).getOriginalUrl();
    }


    public Page<ShortUrl> getUserUrls(UUID userId, Pageable pageable
    ){
        System.out.println("Checkpoint : getUserUrls Service");
        return shortUrlRepo.findByUser_Id(userId, pageable);
    }






}
