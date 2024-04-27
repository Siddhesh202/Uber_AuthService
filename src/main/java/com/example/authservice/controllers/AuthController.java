package com.example.authservice.controllers;

import com.example.authservice.dto.PassengerDto;
import com.example.authservice.dto.PassengerSignupRequestDto;
import com.example.authservice.models.Passenger;
import com.example.authservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto) {
        PassengerDto p = authService.signupPassenger(passengerSignupRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    @GetMapping("/signin/passenger")
    public ResponseEntity<?> signIn() {

        return ResponseEntity.status(HttpStatus.CREATED).body("SIGNED IN SUCCESSFULLY");
    }
}
