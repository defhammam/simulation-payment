package id.co.bankmandiri.micropayment.service;

import id.co.bankmandiri.micropayment.dto.AccountResponseDto;
import id.co.bankmandiri.micropayment.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    Account save(Account account);
    AccountResponseDto saveByPhone(String phone);
    List<Account> saveBulk(List<Account> accounts);
    Page<Account> getAllPerPage(Pageable pageable);
    Account getById(String id);
    Account softRemoveById(String id);
}
