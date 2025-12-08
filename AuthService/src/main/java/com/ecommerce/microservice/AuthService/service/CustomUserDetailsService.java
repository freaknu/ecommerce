package com.ecommerce.microservice.AuthService.service;

import com.ecommerce.microservice.AuthService.enums.Roles;
import com.ecommerce.microservice.AuthService.model.AppUser;
import com.ecommerce.microservice.AuthService.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AppUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        Set<Roles> roles = user.getRoles();

        Set<SimpleGrantedAuthority> auths = roles.stream().map((r)-> {
            return new SimpleGrantedAuthority("ROLE_"+r.name());
        }).collect(Collectors.toSet());

        return User
                .builder()
                .username(user.getEmail())
                .authorities(auths)
                .password(user.getPassword())
                .build();
    }
}
