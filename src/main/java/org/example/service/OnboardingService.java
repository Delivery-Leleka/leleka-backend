package org.example.service;

import org.example.dto.OnboardingRequest;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class OnboardingService {

    private final UserRepository userRepository;

    public OnboardingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void completeOnboarding(OnboardingRequest request) {

        if (request.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required");
        }

        if (request.getName() == null ||
                request.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }

        userRepository.updateName(
                request.getUserId(),
                request.getName()
        );
    }
}