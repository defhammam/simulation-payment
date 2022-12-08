package id.co.bankmandiri.micropayment.repository;

import id.co.bankmandiri.micropayment.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Account, String>, JpaSpecificationExecutor<Account> {
    public Account findAccountByCustomerPhone(String phoneNumber);
}
