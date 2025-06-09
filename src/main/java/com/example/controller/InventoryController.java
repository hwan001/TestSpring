package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.dto.FoodStockDto;
import com.example.dto.FoodUsageDto;
import com.example.dto.StockSummaryDto;
import com.example.service.InventoryService;


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
    public String showInventoryPage(Model model) {
        model.addAttribute("pageTitle", "재고 목록");
        model.addAttribute("bodyView", "/WEB-INF/views/inventory/inventory.jsp");
        return "common/layout";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String uploadFoodImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if (file.isEmpty()) return "파일 없음";

        // 저장 경로 설정 (서버의 실제 경로로 변환)
        String uploadDir = request.getServletContext().getRealPath("/uploads");
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
            dir.setReadable(true, false);
            dir.setWritable(true, false);
        }
    
        // 파일 저장
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        File dest = new File(dir, fileName);
        dest.setReadable(true, false);
        dest.setWritable(true, false);
        file.transferTo(dest);

        // 업로드된 이미지 경로 반환
        return "/uploads/" + fileName;
    }
}