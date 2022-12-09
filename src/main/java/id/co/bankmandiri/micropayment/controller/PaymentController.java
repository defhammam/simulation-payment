package id.co.bankmandiri.micropayment.controller;

import id.co.bankmandiri.micropayment.constant.DefaultParameter;
import id.co.bankmandiri.micropayment.constant.Noun;
import id.co.bankmandiri.micropayment.constant.ResponseMessage;
import id.co.bankmandiri.micropayment.constant.UrlPath;
import id.co.bankmandiri.micropayment.dto.PaymentRequestDto;
import id.co.bankmandiri.micropayment.dto.DebitResponseDto;
import id.co.bankmandiri.micropayment.dto.PaymentSearchDto;
import id.co.bankmandiri.micropayment.entity.Payment;
import id.co.bankmandiri.micropayment.service.PaymentService;
import id.co.bankmandiri.micropayment.utils.CustomResponse;
import id.co.bankmandiri.micropayment.utils.PageResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlPath.TRX_PAYMENT)
public class PaymentController {
    PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{idOfPayment}")
    public ResponseEntity<CustomResponse<DebitResponseDto>> getPaymentById(@PathVariable String idOfPayment) {
        CustomResponse<DebitResponseDto> customResponse = new CustomResponse<>();
        customResponse.setData(paymentService.getById(idOfPayment));
        customResponse.setMessage(String.format(
                ResponseMessage.GET_SINGLE_SUCCESS, Noun.PAYMENT, idOfPayment
        ));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(customResponse);
    }

    @GetMapping("/page")
    public PageResponseWrapper<Payment> getPayments(
            @RequestParam(name="index", defaultValue=DefaultParameter.PAGE_INDEX) Integer pageIndex,
            @RequestParam(name="size", defaultValue=DefaultParameter.PAGE_SIZE) Integer pageSize,
            @RequestParam(name="from", defaultValue="") Long startDate,
            @RequestParam(name="until", defaultValue="") Long endDate
    ) {
        Pageable pageRequest = PageRequest.of(pageIndex, pageSize);
        PaymentSearchDto paymentSearchDto = new PaymentSearchDto();
        paymentSearchDto.setBeginDate(startDate);
        paymentSearchDto.setEndDate(endDate);

        return new PageResponseWrapper<>(paymentService.getPaymentsPerPage(pageRequest, paymentSearchDto));
    }

    @PostMapping("/debit")
    public ResponseEntity<CustomResponse<DebitResponseDto>> addDebit(
            @RequestBody PaymentRequestDto paymentRequestDto
            ) {
        CustomResponse<DebitResponseDto> customResponse = new CustomResponse<>();
        customResponse.setData(paymentService.debit(
                paymentRequestDto.getPhoneNumber(),
                paymentRequestDto.getAmountPaid()
        ));
        customResponse.setMessage(String.format(
                ResponseMessage.PAYMENT_CREATED, Noun.DEBIT
        ));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(customResponse);
    }

    @PostMapping("/top-up")
    public Integer topUpByPhoneNumber(@RequestParam String phone, @RequestParam Integer amount) {
        return paymentService.topUp(phone, amount);
    }
}
