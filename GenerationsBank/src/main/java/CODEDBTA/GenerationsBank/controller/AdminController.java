package CODEDBTA.GenerationsBank.controller;

import CODEDBTA.GenerationsBank.entity.UserEntity;
import CODEDBTA.GenerationsBank.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


//no admin authentication is implemented. anyone can access these methods
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Get all users
    // <Map<String, List<? extends Object>> is used to return a string for status and List of users in responseEntity
    @GetMapping("/getAll")
    public ResponseEntity<Map<String, List<? extends Object>>> getAllUsers() {

        List<UserEntity> allUsers = adminService.getAllUsers();

        // if users are not empty, return a comprehensive response with request status and list of users
        if (!allUsers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", List.of("success"),  // We return a single element list for "status"
                    "users", allUsers                     // Include the list of users in the response
            ));
        }
        else {// if no users exist in database, return status and empty users list
            return ResponseEntity.badRequest().body(Map.of(
                    "status", List.of("No users found"), // Return status message in a list
                    "users", new ArrayList<>()            // Empty list of users
            ));
        }
    }
}