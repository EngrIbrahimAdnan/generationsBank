package CODEDBTA.GenerationsBank.service;

import CODEDBTA.GenerationsBank.bo.AccountResponse;
import CODEDBTA.GenerationsBank.bo.CreateStockRequest;
import CODEDBTA.GenerationsBank.bo.UpdateStockResponse;

public interface StockService {

    UpdateStockResponse updateStock(CreateStockRequest request);

    void addItems(CreateStockRequest stock);
    AccountResponse getAll();
}