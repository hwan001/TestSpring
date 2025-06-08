package com.example.dto;

import lombok.Data;

@Data
public class StockSummaryDto {
    private int id;
    private String name;
    private String arrivalDate;
    private double unitWeight;
    private int quantity;
    private double totalPrice;
    private double usedQuantity;
    private double remainingQuantity;
    private double usedCost;
    private double remainingCost;
}
