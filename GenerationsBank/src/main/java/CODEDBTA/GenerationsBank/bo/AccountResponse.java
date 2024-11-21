package CODEDBTA.GenerationsBank.bo;

import CODEDBTA.GenerationsBank.entity.StockEntity;

import java.util.List;

public class AccountResponse {
    List<StockEntity> stockList;

    public AccountResponse(List<StockEntity> stockList) {
        this.stockList = stockList;
    }

    public List<StockEntity> getStockList() {
        return stockList;
    }

    public void setStockList(List<StockEntity> stockList) {
        this.stockList = stockList;
    }
}
