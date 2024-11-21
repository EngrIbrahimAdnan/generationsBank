package CODEDBTA.GenerationsBank.bo;

public class UpdateStockResponse {

    private String item;

    private Long remainingQty;

    private String status;

    private Long orderId;
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getRemainingQty() {
        return remainingQty;
    }

    public void setRemainingQty(Long remainingQty) {
        this.remainingQty = remainingQty;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}