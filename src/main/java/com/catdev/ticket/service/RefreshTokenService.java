package com.catdev.ticket.service;


import com.catdev.ticket.entity.RefreshTokenEntity;
import com.catdev.ticket.entity.UserEntity;

import javax.transaction.Transactional;
import java.util.Optional;

public interface RefreshTokenService {
    Optional<RefreshTokenEntity> findByToken(String token);

    RefreshTokenEntity createRefreshToken(UserEntity userEntity);

    RefreshTokenEntity verifyExpiration(RefreshTokenEntity token);

    @Transactional
    Long deleteByUserId(Long userId);
}
