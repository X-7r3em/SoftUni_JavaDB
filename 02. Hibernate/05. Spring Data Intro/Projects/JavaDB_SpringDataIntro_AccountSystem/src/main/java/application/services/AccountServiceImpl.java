package application.services;

import application.models.Account;
import application.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService{
    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal amount, Long id) {
        if (amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Can not transfer negative sum");
        }

        Account account = this.accountRepository.findById(id).orElse(null);

        if (account == null){
            throw new IllegalArgumentException("No such user!");
        }

        if (account.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Not enough money!");
        }

        account.setBalance(account.getBalance().subtract(amount));
        this.accountRepository.save(account);
    }

    @Override
    public void transferMoney(BigDecimal amount, Long id) {
        if (amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Can not transfer negative sum");
        }

        Account account = this.accountRepository.findById(id).orElse(null);

        if (account == null){
            throw new IllegalArgumentException("No such user!");
        }

        if (account.getBalance().add(amount).compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Not enough money!");
        }

        account.setBalance(account.getBalance().add(amount));
        this.accountRepository.save(account);
    }
}
