package org.example.web;

import org.example.service.UserService;
import org.example.model.User;
import org.example.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService) {
        this.userService = userService;
        this.jwtUtil = new JwtUtil();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        User registered = userService.register(user);

        if (registered != null) {
            return ResponseEntity.ok(
                    "User registered successfully with login: " + user.getLogin()
            );
        }

        return ResponseEntity.badRequest()
                .body("Registration failed (maybe login exists)");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        User authenticated = userService.authenticateUser(
                user.getLogin(),
                user.getPassword()
        );

        if (authenticated != null) {

            String token = jwtUtil.generateToken(
                    authenticated.getLogin(),
                    authenticated.getName()
            );

            return ResponseEntity.ok(
                    new LoginResponse(
                            token,
                            authenticated.getId()
                    )
            );
        }

        return ResponseEntity.status(401)
                .body("Invalid credentials");
    }
    public static class LoginResponse {

        private final String token;
        private final Long userId;

        public LoginResponse(String token, Long userId) {
            this.token = token;
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public Long getUserId() {
            return userId;
        }
    }
}