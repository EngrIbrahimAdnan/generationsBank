package CODEDBTA.GenerationsBank.controller;

import CODEDBTA.GenerationsBank.bo.admin.*;
import CODEDBTA.GenerationsBank.entity.AccountEntity;
import CODEDBTA.GenerationsBank.entity.TransactionEntity;
import CODEDBTA.GenerationsBank.entity.UserEntity;
import CODEDBTA.GenerationsBank.enums.Roles;
import CODEDBTA.GenerationsBank.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


//no admin authentication is implemented. anyone can access these methods
@RestController
@PreAuthorize("hasAuthority('ADMIN')")
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

    @GetMapping("/{id}")
    public ResponseEntity<GetUserByIdResponse> getUserById(@PathVariable(name = "id") Long id){
        UserEntity user = adminService.getUserById(id);

        if (user == null){
            GetUserByIdResponse response = new GetUserByIdResponse();
            response.setMessage("User with Id " + id + " not found.");
            return ResponseEntity.badRequest().body(response);
        }
        else {
            GetUserByIdResponse response = new GetUserByIdResponse();
            response.setUser(user);
            response.setMessage("User with Id " + id + " found.");
            return ResponseEntity.ok().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> DeleteById(@PathVariable(name = "id") Long id){
            UserEntity user = adminService.getUserById(id);

            if (user == null){
                DeleteResponse response = new DeleteResponse();
                response.setMessage("User with id " + id + " does not exist.");
                return ResponseEntity.badRequest().body(response);
            }
            else {
                DeleteResponse response = new DeleteResponse();
                adminService.deleteUserById(id);
                response.setMessage("User with id " + id + " deleted successfully.");
                return ResponseEntity.badRequest().body(response);
            }

    }

    @GetMapping("/accounts")
    public ResponseEntity<Map<String, List<? extends Object>>> getAllAccounts(){
        List<AccountEntity> accounts = adminService.getAllAccounts();

        // if users are not empty, return a comprehensive response with request status and list of accounts
        if (!accounts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", List.of("success"),  // We return a single element list for "status"
                    "accounts", accounts                     // Include the list of accounts in the response
            ));
        }
        else {// if no users exist in database, return status and empty users list
            return ResponseEntity.badRequest().body(Map.of(
                    "status", List.of("No accounts found"), // Return status message in a list
                    "accounts", new ArrayList<>()            // Empty list of accounts
            ));
        }
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<GetAccountResponse> getAccountById(@PathVariable(name = "id") Long id){
        AccountEntity account = adminService.getAccountById(id);

        if (account == null){
            GetAccountResponse response = new GetAccountResponse();
            response.setMessage("Account with Id " + id + " not found.");
            return ResponseEntity.badRequest().body(response);
        }
        else {
            GetAccountResponse response = new GetAccountResponse();
            response.setAccount(account);
            response.setMessage("Accounts with Id " + id + " found.");
            return ResponseEntity.ok().body(response);
        }
    }

    @DeleteMapping("/account/{id}")
    public ResponseEntity<DeleteResponse> deleteAccount(@PathVariable(name = "id") Long id){
        AccountEntity account = adminService.getAccountById(id);

        if (account == null){
            DeleteResponse response = new DeleteResponse();
            response.setMessage("Account with id " + id + " does not exist.");
            return ResponseEntity.badRequest().body(response);
        }
        else {
            DeleteResponse response = new DeleteResponse();
            adminService.deleteAccountById(id);
            response.setMessage("Account with id " + id + " deleted successfully.");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<Map<String, List<? extends Object>>> getAllTransactions(){
        List<TransactionEntity> transactions = adminService.getAllTransactions();

        // if users are not empty, return a comprehensive response with request status and list of transactions
        if (!transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", List.of("success"),  // We return a single element list for "status"
                    "transactions", transactions                     // Include the list of transactions in the response
            ));
        }
        else {// if no users exist in database, return status and empty users list
            return ResponseEntity.badRequest().body(Map.of(
                    "status", List.of("No transactions found"), // Return status message in a list
                    "transactions", new ArrayList<>()            // Empty list of transactions
            ));
        }
    }

    @GetMapping("/transaction/{id}")
    public ResponseEntity<GetTransactionResponse> getTransactionById(@PathVariable(name = "id") Long id){
        TransactionEntity transaction = adminService.getTransactionById(id);

        if (transaction == null){
            GetTransactionResponse response = new GetTransactionResponse();
            response.setMessage("Transaction with Id " + id + " not found.");
            return ResponseEntity.badRequest().body(response);
        }
        else {
            GetTransactionResponse response = new GetTransactionResponse();
            response.setTransaction(transaction);
            response.setMessage("Transaction with Id " + id + " found.");
            return ResponseEntity.ok().body(response);
        }
    }

    @DeleteMapping("/transaction/{id}")
    public ResponseEntity<DeleteResponse> deleteTransaction(@PathVariable(name = "id") Long id){
        TransactionEntity transaction = adminService.getTransactionById(id);

        if (transaction == null){
            DeleteResponse response = new DeleteResponse();
            response.setMessage("Transaction with id " + id + " does not exist.");
            return ResponseEntity.badRequest().body(response);
        }
        else {
            DeleteResponse response = new DeleteResponse();
            adminService.deleteTransactionById(id);
            response.setMessage("Transaction with id " + id + " deleted successfully.");
            return ResponseEntity.badRequest().body(response);
        }

    }

    @PutMapping("/user/role")
    public ResponseEntity<AssignRoleResponse> assignRole(@RequestBody AssignRoleRequest request){
        UserEntity user = adminService.getUserById(request.getUserId());

        if (user == null){
            AssignRoleResponse response = new AssignRoleResponse();
            response.setMessage("User with id " + request.getUserId() + " does not exist.");
            return ResponseEntity.badRequest().body(response);
        }
        else {
            AssignRoleResponse response = new AssignRoleResponse();
            response.setMessage("Assigned role " + request.getRole() + " to User with ID " + request.getUserId() + " .");
            response.setUser(user);
            return ResponseEntity.ok().body(response);
        }
    }
}