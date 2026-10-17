package org.example.web;

import org.example.dto.OnboardingRequest;
import org.example.service.OnboardingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/onboarding")
public class OnboardingController {

    private final OnboardingService onboardingService;

    public OnboardingController(
            OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @PostMapping
    public ResponseEntity<Void> completeOnboarding(
            @RequestBody OnboardingRequest request) {

        onboardingService.completeOnboarding(request);

        return ResponseEntity.ok().build();
    }
}