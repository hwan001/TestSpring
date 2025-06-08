package com.example.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FoodUsage {
    private int id;
    private int stockId;
    private BigDecimal usedQuantity;
}
