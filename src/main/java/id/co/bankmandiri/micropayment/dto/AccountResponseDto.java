package id.co.bankmandiri.micropayment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponseDto {
    private String phone;
    private Integer balance;
}
