package com.example.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.example.domain.FoodUsage;
import com.example.dto.FoodStockDto;
import com.example.dto.StockSummaryDto;

public interface FoodMapper {
    int insertFoodStock(FoodStockDto dto);
    
    void createFoodStockTable(); 
    void createFoodUsageTable();
    void createSummaryView();

    void insertUsage(FoodUsage usage);
    List<StockSummaryDto> getStockSummary();
    List<FoodStockDto> getAllStocks();
    
    BigDecimal getRemainingQuantity(int stockId);
}
