package com.example.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FoodStockDto {
    private int id;
    private String name;
    private double unitWeight;
    private int quantity;
    private double totalPrice;
    private String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate arrivalDate;
}
