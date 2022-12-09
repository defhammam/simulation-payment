package id.co.bankmandiri.micropayment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentRequestDto {
    private Integer amountPaid;
    private String phoneNumber;
}
