package kz.lakida.javacourse.rest;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    private BigDecimal balance;

    public Account(){}

    public Account(UUID id, User owner, BigDecimal balance) {
        this.id = id;
        this.owner = owner;
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(owner, account.owner) && Objects.equals(balance, account.balance);
    }



    @Override
    public int hashCode() {
        return Objects.hash(id, owner, balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", owner=" + owner +
                ", amount=" + balance +
                '}';
    }
}
