package com.concentrix.lgintegratedadmin.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Product {
    private Long id;
    private String productCode;
    private String productName;
    private Long price;
    private Integer stockQuantity;
    private String category;
}
