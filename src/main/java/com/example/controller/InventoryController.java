package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.dto.FoodStockDto;
import com.example.dto.FoodUsageDto;
import com.example.dto.StockSummaryDto;
import com.example.service.InventoryService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/addStock")
    @ResponseBody
    public ResponseEntity<?> registerFood(@RequestBody FoodStockDto food) {
        logger.info("registerFood : food = {}", food);
        inventoryService.registerFood(food);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addUsage")
    @ResponseBody
    public ResponseEntity<?> recordFoodUsage(@RequestBody FoodUsageDto usage) {
        logger.info("addUsage : usage  = {}", usage);
        inventoryService.addUsage(usage);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/summary", produces = "application/json")
    @ResponseBody
    public List<StockSummaryDto> getSummary() {
        return inventoryService.getStockSummary();
    }

    @GetMapping(value = "/stocks", produces = "application/json")
    @ResponseBody
    public List<FoodStockDto> getAllFoods() {
        return inventoryService.getAllStocks(); // 드롭다운용
    }

    @GetMapping("")
    public String showInventoryPage() {
        return "inventory"; // Maps to /WEB-INF/views/inventory.jsp
    }
}