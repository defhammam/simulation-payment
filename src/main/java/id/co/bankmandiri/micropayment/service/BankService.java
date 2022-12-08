package id.co.bankmandiri.micropayment.service;

import id.co.bankmandiri.micropayment.entity.Bank;

import java.util.List;

public interface BankService {
    Bank save(Bank bank);
    List<Bank> saveBulk(List<Bank> banks);
    List<Bank> getAll();
    Bank getById(String id);
    Bank removeById(String id);
}
