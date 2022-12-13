package id.co.bankmandiri.micropayment.utils;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse<T> {
    private String message;
    private T data;
}
