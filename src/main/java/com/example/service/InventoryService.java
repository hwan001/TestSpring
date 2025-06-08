package com.example.service;

// import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import com.example.mapper.FoodMapper;
import com.example.dto.FoodStockDto;
import com.example.domain.FoodUsage;
import com.example.dto.FoodUsageDto;
import com.example.dto.StockSummaryDto;

import java.util.List;
import java.math.BigDecimal;

@Service
public class InventoryService {

	@Autowired
	private FoodMapper foodMapper;

	@PostConstruct
	public void init() {
		try {
			foodMapper.createFoodStockTable();
			foodMapper.createFoodUsageTable();
			foodMapper.createSummaryView();
		} catch (Exception e) {
			throw new RuntimeException("초기화 중 테이블 또는 뷰 생성 실패", e);
		}
	}

	public void registerFood(FoodStockDto dto) {
		try {
			foodMapper.insertFoodStock(dto);
		} catch (Exception e) {
			throw new RuntimeException("식품 등록 실패", e);
		}
	}

	public void addUsage(FoodUsageDto dto) {
		BigDecimal remainingQuantity = foodMapper.getRemainingQuantity(dto.getStockId());
		BigDecimal usedQuantity = dto.getUsedQuantity();

		if (usedQuantity.compareTo(remainingQuantity) > 0) {
			throw new IllegalArgumentException("사용량이 남은 수량보다 많습니다.");
		}

		FoodUsage usage = new FoodUsage();
		usage.setStockId(dto.getStockId());
		usage.setUsedQuantity(dto.getUsedQuantity());
		foodMapper.insertUsage(usage);
	}

	public List<StockSummaryDto> getStockSummary() {
		return foodMapper.getStockSummary();
	}

	public List<FoodStockDto> getAllStocks() {
		return foodMapper.getAllStocks();
	}
}