package kz.lakida.javacourse.rest;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createAccount(Account account) {
        if (account.getBalance().doubleValue() < 0) throw new RuntimeException("Amount is invalid");
        if (account.getBalance() == null ) account.setBalance(BigDecimal.valueOf(0.00));
        accountRepository.save(account);

    }


    public List<Account> findAll() {
        var list = new ArrayList<Account>();
        for (Account account : accountRepository.findAll()) {
            list.add(account);
        }
        return list;
    }
}
