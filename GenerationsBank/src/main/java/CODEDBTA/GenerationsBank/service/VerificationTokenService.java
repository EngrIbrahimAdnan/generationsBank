package CODEDBTA.GenerationsBank.service;

import CODEDBTA.GenerationsBank.entity.UserEntity;
import CODEDBTA.GenerationsBank.entity.VerificationToken;
import CODEDBTA.GenerationsBank.repository.UserRepository;
import CODEDBTA.GenerationsBank.repository.VerificationTokenRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenService {

    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;


    public VerificationTokenService(VerificationTokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;

    }

    public String generateToken(String email) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setEmail(email);
        verificationToken.setExpirationTime(LocalDateTime.now().plusSeconds(30)); //plusHours(1)
        tokenRepository.save(verificationToken);
        return token;
    }

    public String validateTokenForUserCreation(String token) {

        // Retrieve the token without filtering by expiration time
        // This is done so that user can be deleted (via tokenEntity) after expiration to optimize repository space
        try {
            // try fetching token from the parameter passed
            Optional<VerificationToken> tokenEntity = tokenRepository.findByToken(token);
            // Check if the token is present and hasn't valid (not expired)
            if (tokenEntity.isPresent() && tokenEntity.get().getExpirationTime().isAfter(LocalDateTime.now())) {

                String userByEmail = tokenEntity.get().getEmail();
                UserEntity userEntity = userRepository.findByEmail(userByEmail);

                // if user is found, set verification status and save it back into repository
                if (userEntity != null) {
                    userEntity.setVerified(true);  // Mark the user as verified
                    userRepository.save(userEntity);

                    // null translates to successful here
                    return null;
                } else {
                    // If no User is found.
                    return "User not found.";
                }
            } else {
                // if token is expired, the user (found via token entity) is deleted if he exists
                String userByEmail = tokenEntity.get().getEmail();
                UserEntity userEntity = userRepository.findByEmail(userByEmail);

                // To delete unverified user after expiration, ensure user exists and has not been verified
                if (userEntity != null && !userEntity.getVerified()) {
                    userRepository.delete(userEntity);  // Delete the user if exists
                }
                return "Token expired";
            }

        } catch (Exception e) {
            return "Token doesn't exist!";
        }
    }
}

