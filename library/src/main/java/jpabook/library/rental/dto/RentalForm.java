package jpabook.library.rental.dto;

import lombok.Data;

import java.util.List;

@Data
public class RentalForm {
    private Long userId;
    private List<Long> bookIds; // 여러 권 대출 가능

}