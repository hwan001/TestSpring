<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>Inventory</title>
		<script src="/assets/js/inventory.js" defer></script>
    <link rel="stylesheet" href="/assets/css/inventory.css">
	</head>
	<body>
		<!-- 식품 입고 등록 폼 -->
		<form id="stockForm">
			도착일: <input type="date" name="arrivalDate" required /> 이름:
			<input type="text" name="name" required /> 중량:
			<input type="number" name="unitWeight" step="0.01" required /> 개수:
			<input type="number" name="quantity" required /> 전체 가격:
			<input type="number" name="totalPrice" step="0.01" required />
			<button type="submit">등록</button>
		</form>

		<!-- 소비 기록 폼 -->
		<form id="usageForm">
			식품 선택:
			<select name="stockId" id="stockSelect"></select>
			소비량: <input type="number" name="usedQuantity" step="0.01" required />
			<button type="submit">소비</button>
		</form>

		<!-- 재고 현황 테이블 -->
		<table id="stockTable">
			<thead>
				<tr>
          			<th>입고일</th>
					<th>이름</th>
					<th>전체수량</th>
          			<th>전체가치</th>
					<th>소비량</th>
					<th>남은수량</th>
					<th>사용된 비용</th>
					<th>남은 가치</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</body>
</html>
