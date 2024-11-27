package CODEDBTA.GenerationsBank.controller;

import CODEDBTA.GenerationsBank.bo.CreateUserRequest;

import CODEDBTA.GenerationsBank.bo.LoginRequest;
import CODEDBTA.GenerationsBank.bo.LoginResponse;
import CODEDBTA.GenerationsBank.entity.UserEntity;
import CODEDBTA.GenerationsBank.entity.VerificationToken;
import CODEDBTA.GenerationsBank.service.AuthenticationService;

import CODEDBTA.GenerationsBank.service.GuardianService;
import CODEDBTA.GenerationsBank.service.JwtService;
import CODEDBTA.GenerationsBank.service.VerificationTokenService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Paragraph;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;


@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final GuardianService guardianService;

    private final VerificationTokenService tokenService;

    private final JwtService jwtService; //Abdulrahman: Required for JWT Token creation and validation

    private final AuthenticationService authenticationService; //Abdulrahman: Required for login to be Validated

    public UserController(GuardianService guardianService, VerificationTokenService tokenService, JwtService jwtService, AuthenticationService authenticationService) {
        this.guardianService = guardianService;
        this.tokenService = tokenService;
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
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

    // "generateQRCode" and "generatePDF" are only examples to understand how to generate work for the time being
    // this will be later moved to
    @GetMapping("/generate-qr")
    public ResponseEntity<byte[]> generateQRCode() {

        // Use the correct HTTPS URL for your API
        // The ip here is test while being in coded. For testing purposes, change it to the one corrosponding to your current ip
        // run "ipconfig /all" (windows) and copy the ip for "IPv4 Address" and replace with "192.168.8.92"
        String url = "http://192.168.8.92:8080/api/generate-pdf";

        try {
            // Create a QRCodeWriter instance
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            // Generate a QR code as a matrix from the URL
            var bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 300, 300);

            // Create a ByteArrayOutputStream to hold the PNG data
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

            // Write the QR code matrix to the output stream in PNG format
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

            // Convert the output stream to a byte array (PNG image data)
            byte[] pngData = pngOutputStream.toByteArray();

            // Return the PNG image as a response with appropriate headers
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"qrcode.png\"") // Inline display, not a download
                    .contentType(MediaType.IMAGE_PNG) // Set content type to PNG
                    .body(pngData); // Attach the image data as the response body

        } catch (Exception e) {
            // Handle any exceptions by returning a Bad Request response
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePDF() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // Create a new PDF document
            Document document = new Document();

            // Associate the document with a PdfWriter to write to the ByteArrayOutputStream
            PdfWriter.getInstance(document, baos);

            // Open the document to start adding content
            document.open();

            // Add content to the PDF
            document.add(new Paragraph("Bank Statement")); // Title of the document
            document.add(new Paragraph("Account Holder: John Doe")); // Account holder's name
            document.add(new Paragraph("Account Number: 1234567890")); // Account number
            document.add(new Paragraph("Balance: $5,000.00")); // Current balance
            document.add(new Paragraph("Recent Transactions:")); // Transactions section
            document.add(new Paragraph("1. $500.00 - Walmart")); // Transaction 1
            document.add(new Paragraph("2. $100.00 - Starbucks")); // Transaction 2
            document.add(new Paragraph("3. $250.00 - Rent")); // Transaction 3

            // Close the document after adding content
            document.close();

            // Convert the contents of the ByteArrayOutputStream to a byte array (PDF data)
            byte[] pdfBytes = baos.toByteArray();

            // Return the PDF as a downloadable file with appropriate headers
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bank_statement.pdf") // Prompt download with file name
                    .header(HttpHeaders.CONTENT_TYPE, "application/pdf") // Specify content type as PDF
                    .body(pdfBytes); // Attach the PDF data as the response body

        } catch (Exception e) {
            // Handle exceptions by returning a 500 Internal Server Error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest request) {
        UserEntity authenticatedUser = authenticationService.authenticate(request);

        String jwtToken = jwtService.generateTokenWithUserId(authenticatedUser, authenticatedUser.getId());

        LoginResponse loginResponse = new LoginResponse();
                loginResponse.setToken(jwtToken);
                loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}