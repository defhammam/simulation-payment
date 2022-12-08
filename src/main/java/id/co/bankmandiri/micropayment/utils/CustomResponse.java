package id.co.bankmandiri.micropayment.utils;

import lombok.*;

@Data
public class CustomResponse<T> {
    private String message;
    private T data;
}
