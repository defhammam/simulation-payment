package id.co.bankmandiri.micropayment.service.implementation;

import id.co.bankmandiri.micropayment.entity.Account;
import id.co.bankmandiri.micropayment.repository.BankRepository;
import id.co.bankmandiri.micropayment.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BankServiceImpl implements BankService {
    BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public Account save(Account account) {
        return bankRepository.save(this.validateDeletedStatus(account));
    }

    @Override
    public List<Account> saveBulk(List<Account> accounts) {
        List<Account> accountList = new ArrayList<>();
        accounts.forEach(bank -> accountList.add(this.validateDeletedStatus(bank)));
        return bankRepository.saveAll(accountList);
    }

    @Override
    public List<Account> getAll() {
        return bankRepository.findAll();
    }

    @Override
    public Account getById(String id) {
        if (bankRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException();
        }
        return bankRepository.findById(id).get();
    }

    @Override
    public Account softRemoveById(String id) {
        Account accountToRemove = this.getById(id);
        accountToRemove.setIsDeleted(true);
        return accountToRemove;
    }

    @Override
    public Integer debit(String phoneOfCustomer, Integer amountToReduce) {
        Account foundAccount = bankRepository.findAccountByCustomerPhone(phoneOfCustomer);
        foundAccount.setBalance(foundAccount.getBalance() - amountToReduce);
        return bankRepository.save(foundAccount).getBalance();
    }

    private Account validateDeletedStatus(Account account) {
        if (account.getIsDeleted() == null) {
            account.setIsDeleted(false);
        }
        return account;
    }
}
