package com.letscode.moviesBattle.config.service.impl;

import com.letscode.moviesBattle.config.dto.UserDetailsDto;
import com.letscode.moviesBattle.domain.repository.UserRepository;
import com.letscode.moviesBattle.domain.repository.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserEntity userEntity = userRepository.findByUserName(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailsDto(userEntity);
    }
}
