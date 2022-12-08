package id.co.bankmandiri.micropayment.service;

import id.co.bankmandiri.micropayment.dto.PaymentResponseDto;
import id.co.bankmandiri.micropayment.dto.PaymentSearchDto;
import id.co.bankmandiri.micropayment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    Integer debit(String phoneOfCustomer, Integer amountToReduce);
    Integer topUp(String phoneOfCustomer, Integer amountToAdd);
    Page<Payment> getPaymentsPerPage(Pageable pageable, PaymentSearchDto paymentSearchDto);
    PaymentResponseDto getById(String id);
}
