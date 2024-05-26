package com.marketplace.services;

import com.marketplace.models.User;
import com.marketplace.dto.UserDTO;
import com.marketplace.models.UserRequest;
import com.marketplace.models.UserResponse;
import com.marketplace.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse updateUser(UserRequest userRequest) {
        userRepository.updateUser(userRequest.getId(), userRequest.getFirstname(), userRequest.getLastname(), userRequest.getCountry());
        return new UserResponse("El usuario se registró satisfactoriamente");
    }

    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return UserDTO.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .country(user.getCountry())
                    .build();
        }
        return null;
    }
}

