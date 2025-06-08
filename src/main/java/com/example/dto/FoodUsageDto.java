package com.example.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FoodUsageDto {
    private int stockId;
    private BigDecimal usedQuantity;
}
