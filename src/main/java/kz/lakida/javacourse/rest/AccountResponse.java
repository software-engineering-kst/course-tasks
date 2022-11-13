package kz.lakida.javacourse.rest;

import java.util.List;

public class AccountResponse {
    private List<Account> accounts;

    public AccountResponse() {
    }

    public AccountResponse(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setUsers(List<Account> accounts) {
        this.accounts = accounts;
    }
}
