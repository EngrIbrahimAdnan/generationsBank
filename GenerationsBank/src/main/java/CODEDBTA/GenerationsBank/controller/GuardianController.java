package CODEDBTA.GenerationsBank.controller;

import CODEDBTA.GenerationsBank.bo.guardian.TransferRequest;
import CODEDBTA.GenerationsBank.bo.guardian.DependentRequest;
import CODEDBTA.GenerationsBank.entity.TransactionEntity;
import CODEDBTA.GenerationsBank.entity.UserEntity;
import CODEDBTA.GenerationsBank.service.GuardianServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/guardian")
public class GuardianController {

    private final GuardianServiceImpl guardianService;

    public GuardianController(GuardianServiceImpl guardianService) {
        this.guardianService = guardianService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        try {
            guardianService.transfer(request.getSenderAccountId(), request.getReceiverAccountId(), request.getAmount());
            return new ResponseEntity<>("Transfer successful", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/addDependent")
    public ResponseEntity<String> addDependent(@RequestBody DependentRequest request) {
        try {
            guardianService.addDependent(Long.parseLong(request.getGuardianId()), Long.parseLong(request.getDependentId()));
            return ResponseEntity.ok("Dependent successfully added to the guardian.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/dependents")
    public ResponseEntity<List<UserEntity>> viewDependents(@PathVariable Long id) {
        return ResponseEntity.ok(guardianService.viewDependents(id));
    }

    @GetMapping("/viewTransactions/{guardianId}")
    public ResponseEntity<List<TransactionEntity>> viewTransactions(
            @PathVariable Long guardianId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String category) {
        List<TransactionEntity> transactions = guardianService.viewTransactions(guardianId, startDate, endDate, category);
        return ResponseEntity.ok(transactions);
    }

    @PutMapping("/setSpendingLimit")
    public ResponseEntity<String> setSpendingLimit(@RequestParam Long dependentAccountId, @RequestParam double limit) {
        guardianService.setSpendingLimit(dependentAccountId, limit);
        return ResponseEntity.ok("Spending limit set successfully.");
    }

    @PutMapping("/approveTransaction")
    public ResponseEntity<String> approveTransaction(@RequestParam Long transactionId, @RequestParam boolean approve) {
        guardianService.approveTransaction(transactionId, approve);
        return ResponseEntity.ok(approve ? "Transaction approved." : "Transaction rejected. For more information, contact your guardian.");
    }

}
