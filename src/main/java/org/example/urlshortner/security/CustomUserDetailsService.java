package org.example.urlshortner.security;

import lombok.RequiredArgsConstructor;
import org.example.urlshortner.entity.User;
import org.example.urlshortner.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByEmail(email);
        if(user.isEmpty()){
            throw new UsernameNotFoundException(email);
        }

        return new CustomUserDetail(user.get());


    }
}
