// 입고 등록
document.querySelector("#stockForm").addEventListener("submit", async (e) => {
	e.preventDefault();
	const formData = new FormData(e.target);
	const file = formData.get('file');
	
	let imageUrl = '';
	if (file && file.size > 0) {
		// 파일 업로드
		const uploadFormData = new FormData();
		uploadFormData.append('file', file);
		
		try {
			const uploadResponse = await fetch("/inventory/upload", {
				method: "POST",
				body: uploadFormData
			});
			
			if (!uploadResponse.ok) {
				throw new Error('이미지 업로드 실패');
			}
			
			imageUrl = await uploadResponse.text();
			if (imageUrl === "파일 없음") {
				throw new Error('이미지가 없습니다');
			}
		} catch (err) {
			alert("이미지 업로드 실패: " + err.message);
			return;
		}
	}

	// 재고 정보 등록
	const data = {};
	formData.forEach((value, key) => {
		if (key !== 'file') {  // 파일은 제외
			data[key] = value;
		}
	});
	data.imageUrl = imageUrl;  // 업로드된 이미지 URL 추가

	try {
		const response = await fetch("/inventory/addStock", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify(data),
		});
		
		if (!response.ok) {
			const errorText = await response.text();
			throw new Error(errorText || '재고 등록 실패');
		}
		
		alert("입고 완료");
		e.target.reset(); // 폼 초기화
		await loadSummary();
		await loadStockOptions();
	} catch (err) {
		alert("재고 등록 실패: " + err.message);
	}
});

// 소비 기록
document.querySelector("#usageForm").addEventListener("submit", async (e) => {
	e.preventDefault();
	const formData = new FormData(e.target);
	const data = {};
	formData.forEach((value, key) => {
		data[key] = value;
	});

	try {
		const response = await fetch("/inventory/addUsage", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify(data),
		});

		if (!response.ok) {
			const errorText = await response.text();
			throw new Error(errorText || '소비 기록 실패');
		}

		alert("소비 기록 완료");
		e.target.reset(); // 폼 초기화
		await loadSummary();
	} catch (err) {
		alert("소비에 실패했습니다: " + err.message);
	}
});

// 현황 테이블 로드
async function loadSummary() {
	try {
		const response = await fetch("/inventory/summary");
		if (!response.ok) {
			throw new Error(`HTTP error! status: ${response.status}`);
		}
		
		const data = await response.json();
		const tbody = document.querySelector("#stockTable tbody");
		tbody.innerHTML = "";
		
		data.forEach((row) => {
			const tr = document.createElement("tr");
			tr.innerHTML = `
				<td>${row.arrivalDate}</td>
				<td><img src="${row.imageUrl || ''}" alt="식재료 이미지" width="100" onerror="this.style.display='none'"></td> 
				<td>${row.name}</td> 
				<td>${row.quantity}</td>
				<td>${row.totalPrice}</td>
				<td>${row.usedQuantity}</td>
				<td>${row.remainingQuantity}</td>
				<td>${row.usedCost}</td>
				<td>${row.remainingCost}</td>
			`;
			tbody.appendChild(tr);
		});
	} catch (err) {
		console.error("Summary load error:", err);
		alert("재고 현황을 불러오는데 실패했습니다.");
	}
}

// 입고 항목 선택용 드롭다운 로드
async function loadStockOptions() {
	try {
		const response = await fetch("/inventory/stocks");
		if (!response.ok) {
			throw new Error(`HTTP error! status: ${response.status}`);
		}
		
		const data = await response.json();
		const select = document.querySelector("#stockSelect");
		select.innerHTML = "";
		
		data.forEach((stock) => {
			const opt = document.createElement("option");
			opt.value = stock.id;
			opt.textContent = `${stock.name} (${stock.arrivalDate})`;
			select.appendChild(opt);
		});
	} catch (err) {
		console.error("Stock load error:", err);
		alert("재고 목록을 불러오는데 실패했습니다.");
	}
}

// 초기 로드
loadSummary();
loadStockOptions();
