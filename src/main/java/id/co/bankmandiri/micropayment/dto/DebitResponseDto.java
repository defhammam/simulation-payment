package id.co.bankmandiri.micropayment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DebitResponseDto {
    private String idPayment;
    private Long paymentDate;
    private Integer amountPaid;
    private String phoneNumber;
}
