package org.example.urlshortner.util;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.springframework.stereotype.Component;

@Component
public class NanoIdGenerator {

    private static final int DEFAULT_LENGTH = 7;

    public String generateNanoId() {
        return NanoIdUtils.randomNanoId(
                NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
                NanoIdUtils.DEFAULT_ALPHABET,
                DEFAULT_LENGTH
        );
    }
}
