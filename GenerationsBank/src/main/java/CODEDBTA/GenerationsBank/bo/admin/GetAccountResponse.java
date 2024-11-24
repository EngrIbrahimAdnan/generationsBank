package CODEDBTA.GenerationsBank.bo.admin;

import CODEDBTA.GenerationsBank.entity.AccountEntity;

public class GetAccountResponse {

    private String message;
    private AccountEntity account;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}
