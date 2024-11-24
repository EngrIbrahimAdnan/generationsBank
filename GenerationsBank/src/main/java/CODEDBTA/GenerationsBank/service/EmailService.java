package CODEDBTA.GenerationsBank.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.InetAddress;


@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String toEmail, String token, String username) throws MessagingException {
        String subject = "Verify Your Email Address - Generations Bank";
        String verificationLink = "http://localhost:8080/api/auth/verify-email?token=" + token;

        // Ensures error is handled if mailSender faces an issue sending an email
        try {
            // Ensures email address provided passes a number of basic tests ensuring it conforms to the email structure
            if (isValidEmail(toEmail)) {
                String emailContent = "<html>" +
                        "<body>" +
                        "<h2 style='color: #4CAF50;'>Welcome to Generations Bank!</h2>" +
                        "<p>Hi " + username + ",</p>" +
                        "<p>Thank you for registering with us. To complete your registration, please verify your email address by clicking the link below:</p>" +
                        "<a href='" + verificationLink + "' style='display: inline-block; background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Verify Email</a>" +
                        "<p>If you didn’t register, you can safely ignore this email.</p>" +
                        "<p>Thanks, <br> The Team</p>" +
                        "</body>" +
                        "</html>";

                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setTo(toEmail);
                helper.setSubject(subject);
                helper.setText(emailContent, true); // `true` indicates HTML content
                mailSender.send(message);
            } else {
                // if email address doesn't conform to an email address structure
                throw new MessagingException("Invalid email address provided.");
            }
        } catch (Exception e) {
            throw new MessagingException("Error sending email.");
        }
    }

    /**
     * Validate an email address.
     *
     * @param email The email address to validate.
     * @return True if the email is valid; false otherwise.
     */
    public boolean isValidEmail(String email) {
        return isValidSyntax(email) && isDomainReachable(email);
    }

    /**
     * Check if the email syntax is valid using regex.
     *
     * @param email The email address to check.
     * @return True if the syntax is valid; false otherwise.
     */
    private boolean isValidSyntax(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(emailRegex);
    }

    /**
     * Check if the domain of the email is reachable.
     *
     * @param email The email address to check.
     * @return True if the domain is reachable; false otherwise.
     */
    private boolean isDomainReachable(String email) {
        try {
            String domain = email.substring(email.indexOf("@") + 1);
            InetAddress.getByName(domain);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

