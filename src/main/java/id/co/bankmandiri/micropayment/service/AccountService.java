package id.co.bankmandiri.micropayment.service;

import id.co.bankmandiri.micropayment.dto.AccountResponseDto;
import id.co.bankmandiri.micropayment.entity.Account;

import java.util.List;

public interface AccountService {
    Account save(Account account);
    AccountResponseDto saveByPhone(String phone);
    List<Account> saveBulk(List<Account> accounts);
    List<Account> getAll();
    Account getById(String id);
    Account softRemoveById(String id);
}
