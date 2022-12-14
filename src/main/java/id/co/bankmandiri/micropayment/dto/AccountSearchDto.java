package id.co.bankmandiri.micropayment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSearchDto {
    private Integer balanceUpperRange;
    private Integer balanceLowerRange;
}
