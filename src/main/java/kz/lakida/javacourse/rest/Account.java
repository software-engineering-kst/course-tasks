package kz.lakida.javacourse.rest;

import org.apache.catalina.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "account")
public class Account {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    private BigDecimal amount;

    public Account(){}

    public Account(UUID id, User owner, BigDecimal amount) {
        this.id = id;
        this.owner = owner;
        this.amount = amount;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(owner, account.owner) && Objects.equals(amount, account.amount);
    }



    @Override
    public int hashCode() {
        return Objects.hash(id, owner, amount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", owner=" + owner +
                ", amount=" + amount +
                '}';
    }
}
