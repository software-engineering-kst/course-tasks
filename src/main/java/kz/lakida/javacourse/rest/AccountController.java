package kz.lakida.javacourse.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<AccountService> getAllAccounts() {
        return ResponseEntity.ok(new AccountResponse(accountService.findAll()));
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody Account account) {
        accountService.createAccount(account);
        return ResponseEntity.ok().build();
    }
}
