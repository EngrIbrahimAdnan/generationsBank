package CODEDBTA.GenerationsBank.controller;

import CODEDBTA.GenerationsBank.bo.CreateUserRequest;
import CODEDBTA.GenerationsBank.entity.VerificationToken;
import CODEDBTA.GenerationsBank.service.GuardianService;
import CODEDBTA.GenerationsBank.service.VerificationTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final GuardianService guardianService;

    private final VerificationTokenService tokenService;

    public UserController(GuardianService guardianService, VerificationTokenService tokenService) {
        this.guardianService = guardianService;
        this.tokenService = tokenService;
    }

    // API endpoint for creating user. Returns comprehensive response of request status and message
    @PostMapping("/createUser")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody CreateUserRequest request) {
        String requestStatus = guardianService.CreateUserAccount(request);

        //if all required fields are satisfied, request is true
        if (requestStatus == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "message", "Verify email address via the email sent to complete User creation."
            ));
        } else {// otherwise, the required missing field is highlighted to client
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Account creation failed.",
                    "details", requestStatus
            ));
        }
    }

    // verifies email belongs to User
    @GetMapping("/verify-email")
    public ResponseEntity<Map<String, String>> verifyEmail(@RequestParam String token) {
        String requestStatus = tokenService.validateTokenForUserCreation(token);

        if (requestStatus == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "message", "User Verified!"
            ));
        } else {// otherwise, the required missing field is highlighted to client
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", requestStatus
            ));
        }

    }

}