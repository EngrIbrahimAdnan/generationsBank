package CODEDBTA.GenerationsBank.controller;

import CODEDBTA.GenerationsBank.bo.CreateStockRequest;
import CODEDBTA.GenerationsBank.bo.UpdateStockResponse;
import CODEDBTA.GenerationsBank.bo.AccountResponse;
import CODEDBTA.GenerationsBank.service.StockService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/addItems")
    public void addItems(@RequestBody CreateStockRequest stock) {
        stockService.addItems(stock);
    }

    @PostMapping("/updateStock")
    public UpdateStockResponse updateStock(@RequestBody CreateStockRequest request) {
        return stockService.updateStock(request);
    }

    @GetMapping("/getStock")
    public AccountResponse getStock() {
        return stockService.getAll();
    }
}