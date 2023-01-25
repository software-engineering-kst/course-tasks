package kz.lakida.javacourse.rest;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "account")
public class Account {

    @Id
    private UUID id;

    private User owner;

    private BigDecimal amount;

    public Account(){}

    public Account(UUID id,User owner,BigDecimal amount){
        this.id = id;
        this.owner = owner;
        this.amount = amount;
    }
}
