package id.co.bankmandiri.micropayment.service.implementation;

import id.co.bankmandiri.micropayment.constant.ResponseMessage;
import id.co.bankmandiri.micropayment.dto.AccountResponseDto;
import id.co.bankmandiri.micropayment.entity.Account;
import id.co.bankmandiri.micropayment.repository.AccountRepository;
import id.co.bankmandiri.micropayment.service.AccountService;
import id.co.bankmandiri.micropayment.utils.exception.AccountExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(this.validateDeletedStatus(account));
    }

    @Override
    public AccountResponseDto saveByPhone(String phone) {
        Account accountToSearch = accountRepository.findAccountByCustomerPhone(phone);
        if (accountToSearch != null) {
            throw new AccountExistsException(ResponseMessage.ACCOUNT_EXISTS_ERROR);
        }
        Account accountToSave = new Account();
        accountToSave.setCustomerPhone(phone);
        accountToSave.setBalance(100_000);
        accountRepository.save(this.validateDeletedStatus(accountToSave));
        return new AccountResponseDto(
                accountToSave.getCustomerPhone(),
                accountToSave.getBalance()
        );
    }

    @Override
    public List<Account> saveBulk(List<Account> accounts) {
        List<Account> accountList = new ArrayList<>();
        accounts.forEach(bank -> accountList.add(this.validateDeletedStatus(bank)));
        return accountRepository.saveAll(accountList);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getById(String id) {
        if (accountRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException();
        }
        return accountRepository.findById(id).get();
    }

    @Override
    public Account softRemoveById(String id) {
        Account accountToRemove = this.getById(id);
        accountToRemove.setIsDeleted(true);
        return accountRepository.save(accountToRemove);
    }

    private Account validateDeletedStatus(Account account) {
        if (account.getIsDeleted() == null) {
            account.setIsDeleted(false);
        }
        return account;
    }
}
