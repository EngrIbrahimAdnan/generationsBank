package CODEDBTA.GenerationsBank.controller;

import CODEDBTA.GenerationsBank.entity.TransactionEntity;
import CODEDBTA.GenerationsBank.service.DependentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dependent")
public class DependentController {

    private final DependentService dependentService;

    public DependentController(DependentService dependentService) {
        this.dependentService = dependentService;
    }
    @GetMapping("/transactions/{id}")
    public ResponseEntity<List<TransactionEntity>> getTransactionsById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(dependentService.viewTransactions(id));
    }
}
