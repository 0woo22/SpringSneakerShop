package com.github.springsneaker.web.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderRequest {
    private Integer modelId;
    private Integer userId;
    private String shippingAddress;
    private Integer sneakerSize;
    private Integer orderQuantity;
}
