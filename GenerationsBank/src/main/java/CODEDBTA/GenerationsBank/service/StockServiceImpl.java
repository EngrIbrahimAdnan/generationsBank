package CODEDBTA.GenerationsBank.service;

import CODEDBTA.GenerationsBank.bo.AccountResponse;
import CODEDBTA.GenerationsBank.bo.CreateStockRequest;
import CODEDBTA.GenerationsBank.bo.UpdateStockResponse;
import CODEDBTA.GenerationsBank.entity.StockEntity;
import CODEDBTA.GenerationsBank.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements CODEDBTA.GenerationsBank.service.StockService {
    private final StockRepository repository;

    public StockServiceImpl(StockRepository repository) {
        this.repository = repository;
    }

    @Override
    public UpdateStockResponse updateStock(CreateStockRequest request) {
        System.out.println("Inside update inventory for order " + request);
        UpdateStockResponse orderStatus = new UpdateStockResponse();
        try {
            Iterable<StockEntity> inventories = repository.findByItem(request.getItem());

            boolean exists = inventories.iterator().hasNext();

            if (!exists) {
                System.out.println("Stock not exist so reverting the order");
                throw new Exception("Stock not available");
            }

            inventories.forEach(i -> {
                i.setQuantity(i.getQuantity() - request.getQuantity());
                repository.save(i);

                orderStatus.setItem(i.getItem());
                orderStatus.setOrderId(i.getId());
                orderStatus.setRemainingQty(i.getQuantity());
                orderStatus.setStatus("Success");
            });

        } catch (Exception e) {
        }

        return orderStatus;

    }

    @Override
    public void addItems(CreateStockRequest stock) {
        Iterable<StockEntity> items = repository.findByItem(stock.getItem());

        if (items.iterator().hasNext()) {
            items.forEach(i -> {
                i.setQuantity(stock.getQuantity() + i.getQuantity());
                repository.save(i);
            });
        } else {
            StockEntity i = new StockEntity();
            i.setItem(stock.getItem());
            i.setQuantity(stock.getQuantity());
            repository.save(i);
        }
    }

    @Override
    public AccountResponse getAll() {
        AccountResponse accountResponse = new AccountResponse(repository.findAll());
        return accountResponse;

    }

}
