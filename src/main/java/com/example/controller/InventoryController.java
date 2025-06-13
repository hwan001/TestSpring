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

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.dto.FoodStockDto;
import com.example.dto.FoodUsageDto;
import com.example.dto.StockSummaryDto;
import com.example.service.InventoryService;
import com.example.service.MinioService;

import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;
import com.example.config.YamlPropertySourceFactory;


@Controller
@RequestMapping("/inventory")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class InventoryController {
    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private MinioService minioService;

    @Value("${file.minio.publicUrl}")
    private String publicMinioUrl;
    
    @Value("${file.minio.bucketName}")
    private String bucket;

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
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("파일 없음");
            }
    
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            
            minioService.uploadFile(
                fileName,
                file.getInputStream(),
                file.getSize(),
                file.getContentType()
            );

            String publicUrl = publicMinioUrl + "/" + bucket + "/" + fileName;
            return ResponseEntity.ok(publicUrl);
        } catch (Exception e) {
            e.printStackTrace();  // 서버 로그에 전체 에러 출력
            return ResponseEntity.status(500).body("파일 업로드 실패: " + e.getMessage());
        }
    }
    


}