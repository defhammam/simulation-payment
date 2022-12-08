package id.co.bankmandiri.micropayment.controller;

import id.co.bankmandiri.micropayment.constant.DefaultParameter;
import id.co.bankmandiri.micropayment.dto.PaymentSearchDto;
import id.co.bankmandiri.micropayment.entity.Payment;
import id.co.bankmandiri.micropayment.service.PaymentService;
import id.co.bankmandiri.micropayment.utils.PageResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/page")
    public PageResponseWrapper<Payment> getPayments(
            @RequestParam(name="index", defaultValue=DefaultParameter.PAGE_INDEX) Integer pageIndex,
            @RequestParam(name="size", defaultValue=DefaultParameter.PAGE_SIZE) Integer pageSize,
            @RequestParam(name="from", required=false) Long startDate,
            @RequestParam(name="until", required=false) Long endDate
    ) {
        Pageable pageRequest = PageRequest.of(pageIndex, pageSize);
        PaymentSearchDto paymentSearchDto = new PaymentSearchDto();
        paymentSearchDto.setBeginDate(startDate);
        paymentSearchDto.setEndDate(endDate);

        return new PageResponseWrapper<>(paymentService.getPaymentsPerPage(pageRequest, paymentSearchDto));
    }

    @PostMapping("/debit")
    public Integer addDebit(@RequestParam String phone, @RequestParam Integer amount) {
        return paymentService.debit(phone, amount);
    }

    @PostMapping("/top-up")
    public Integer topUpByPhoneNumber(@RequestParam String phone, @RequestParam Integer amount) {
        return paymentService.topUp(phone, amount);
    }
}
