package kz.lakida.javacourse.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AccountService accountService;

    public UserController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<UsersResponse> getAllUsers() {
        return ResponseEntity.ok(new UsersResponse(userService.findAll()));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.createUser(user);
        var account = new Account(UUID.randomUUID(), user, BigDecimal.ZERO);
        accountService.createAccount(account);
        return ResponseEntity.ok().build();
    }
}
