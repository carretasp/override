package override;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue
    private Long accountId;

    private String name;
    private int balance;
    private int payment;
    private boolean override;
    private String rulesTriggered;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public boolean isOverride() {
        return override;
    }

    public void setOverride(boolean override) {
        this.override = override;
    }

    public String getRulesTriggered() {
        return rulesTriggered;
    }

    public void setRulesTriggered(String rulesTriggered) {
        this.rulesTriggered = rulesTriggered;
    }

    public Account(String name, int balance, int payment) {
        this.name = name;
        this.balance = balance;
        this.payment = payment;
    }

    Account() {}
}
