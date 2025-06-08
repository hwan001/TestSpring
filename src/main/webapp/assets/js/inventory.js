console.log("JS loaded"); // 제일 먼저 확인

// 입고 등록
document.querySelector("#stockForm").addEventListener("submit", (e) => {
	e.preventDefault();
	const formData = new FormData(e.target);
	const data = {};
	formData.forEach((value, key) => {
		data[key] = value;
	});

	fetch("/inventory/addStock", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(data),
	}).then(() => {
		alert("입고 완료");
		loadSummary();
		loadStockOptions();
	});
});

// 소비 기록
document.querySelector("#usageForm").addEventListener("submit", (e) => {
	e.preventDefault();
	const formData = new FormData(e.target);
	const data = {};
	formData.forEach((value, key) => {
		data[key] = value;
	});

fetch("/inventory/addUsage", {
	method: "POST",
	headers: {
		"Content-Type": "application/json",
	},
	body: JSON.stringify(data),
})
.then((res) => {
	if (!res.ok) {
		return res.text().then((msg) => { throw new Error(msg); });
	}
	return res;
})
.then(() => {
	alert("소비 기록 완료");
	loadSummary();
})
.catch((err) => {
	alert("소비에 실패했습니다: " + err.message);
});
});

// 현황 테이블 로드
function loadSummary() {
	console.log("loadSummary 호출됨");

	fetch("/inventory/summary")
		.then((res) => {
			console.log("응답 상태:", res.status);
			if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
			return res.json(); 
		})
		.then((data) => {
			console.log("받은 데이터:", data);
			const tbody = document.querySelector("#stockTable tbody");
			tbody.innerHTML = "";
			data.forEach((row) => {
				const tr = document.createElement("tr");
				tr.innerHTML = `
        		  <td>${row.arrivalDate}</td>
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
		})
		.catch((err) => {
			console.error("Summary load error:", err); 
		});
}

// 입고 항목 선택용 드롭다운 로드
function loadStockOptions() {
	fetch("/inventory/stocks")
		.then((res) => {
			if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
			return res.json();
		})
		.then((data) => {
			const select = document.querySelector("#stockSelect");
			select.innerHTML = "";
			data.forEach((stock) => {
				const opt = document.createElement("option");
				opt.value = stock.id;
				opt.textContent = `${stock.name} (${stock.arrivalDate})`;
				select.appendChild(opt);
			});
		})
		.catch((err) => {
			console.error("Stock load error:", err);
		});
}

// 초기 로드
loadSummary();
loadStockOptions();
