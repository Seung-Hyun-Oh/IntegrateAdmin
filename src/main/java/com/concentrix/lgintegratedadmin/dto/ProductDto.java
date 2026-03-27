package com.concentrix.lgintegratedadmin.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductDto {
    private String productCode;
    private String productName;
    private Long price;
    private Integer stockQuantity;
    private String category;
}
