package id.co.bankmandiri.micropayment.service.implementation;

import id.co.bankmandiri.micropayment.dto.PaymentResponseDto;
import id.co.bankmandiri.micropayment.dto.PaymentSearchDto;
import id.co.bankmandiri.micropayment.entity.Account;
import id.co.bankmandiri.micropayment.entity.Payment;
import id.co.bankmandiri.micropayment.repository.AccountRepository;
import id.co.bankmandiri.micropayment.repository.PaymentRepository;
import id.co.bankmandiri.micropayment.service.PaymentService;
import id.co.bankmandiri.micropayment.specification.PaymentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
public class PaymentServiceImpl implements PaymentService {
    PaymentRepository paymentRepository;
    AccountRepository accountRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, AccountRepository accountRepository) {
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Page<Payment> getPaymentsPerPage(Pageable pageable, PaymentSearchDto paymentSearchDto) {
        Specification<Payment> paymentSpecification = PaymentSpecification.getSpecification(paymentSearchDto);
        return paymentRepository.findAll(paymentSpecification, pageable);
    }

    @Override
    public PaymentResponseDto getById(String id) {
        if (paymentRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException();
        }
        Payment foundPayment = paymentRepository.findById(id).get();
        return new PaymentResponseDto(
                foundPayment.getId(),
                foundPayment.getPaymentDate(),
                foundPayment.getAmountPaid(),
                foundPayment.getAccount().getCustomerPhone()
        );
    }

    @Override
    @Transactional
    public PaymentResponseDto debit(String phoneOfCustomer, Integer amountToReduce) {
        Payment payment = new Payment();
        Account foundAccount = accountRepository.findAccountByCustomerPhone(phoneOfCustomer);
        payment.setPaymentDate(System.currentTimeMillis());
        foundAccount.setBalance(foundAccount.getBalance() - amountToReduce);
        payment.setAmountPaid(amountToReduce);
        payment.setAccount(foundAccount);
        Payment paymentWithId = paymentRepository.save(payment);
        return new PaymentResponseDto(
                paymentWithId.getId(),
                paymentWithId.getPaymentDate(),
                amountToReduce,
                foundAccount.getCustomerPhone()
        );
    }

    @Override
    @Transactional
    public Integer topUp(String phoneOfCustomer, Integer amountToAdd) {
        Account foundAccount = accountRepository.findAccountByCustomerPhone(phoneOfCustomer);
        foundAccount.setBalance(foundAccount.getBalance() + amountToAdd);
        return accountRepository.save(foundAccount).getBalance();
    }
}
