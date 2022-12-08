package id.co.bankmandiri.micropayment.service.implementation;

import id.co.bankmandiri.micropayment.entity.Bank;
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
    public Bank save(Bank bank) {
        return bankRepository.save(this.validateDeletedStatus(bank));
    }

    @Override
    public List<Bank> saveBulk(List<Bank> banks) {
        List<Bank> bankList = new ArrayList<>();
        banks.forEach(bank -> bankList.add(this.validateDeletedStatus(bank)));
        return bankRepository.saveAll(bankList);
    }

    @Override
    public List<Bank> getAll() {
        return bankRepository.findAll();
    }

    @Override
    public Bank getById(String id) {
        if (bankRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException();
        }
        return bankRepository.findById(id).get();
    }

    @Override
    public Bank removeById(String id) {
        Bank bankToRemove = this.getById(id);
        bankToRemove.setIsDeleted(true);
        return bankToRemove;
    }

    private Bank validateDeletedStatus(Bank bank) {
        if (bank.getIsDeleted() == null) {
            bank.setIsDeleted(false);
        }
        return bank;
    }
}
