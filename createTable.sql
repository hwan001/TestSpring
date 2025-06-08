CREATE TABLE food_stock (
    id INT AUTO_INCREMENT PRIMARY KEY,
    arrival_date DATE NOT NULL,
    name VARCHAR(100) NOT NULL,
    unit_weight DECIMAL(10,2) NOT NULL,   -- 낱개 중량
    quantity INT NOT NULL,                -- 개수
    total_price DECIMAL(10,2) NOT NULL,   -- 전체 가격
    unit_price AS (total_price / quantity) STORED  -- 개당 가격 (계산 필드)
);

CREATE TABLE food_usage (
    id INT AUTO_INCREMENT PRIMARY KEY,
    stock_id INT NOT NULL,
    used_quantity DECIMAL(10,2) NOT NULL, -- 소비 개수 (실수 허용)
    used_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (stock_id) REFERENCES food_stock(id) ON DELETE CASCADE
);

-- 자동 계산용 뷰
CREATE VIEW food_stock_summary AS
SELECT
    fs.id,
    fs.arrival_date,
    fs.name,
    fs.unit_weight,
    fs.quantity,
    fs.total_price,
    fs.unit_price,
    IFNULL(SUM(fu.used_quantity), 0) AS used_quantity,
    fs.quantity - IFNULL(SUM(fu.used_quantity), 0) AS remaining_quantity,
    fs.unit_price * IFNULL(SUM(fu.used_quantity), 0) AS used_cost,
    fs.unit_price * (fs.quantity - IFNULL(SUM(fu.used_quantity), 0)) AS remaining_cost
FROM
    food_stock fs
LEFT JOIN
    food_usage fu ON fs.id = fu.stock_id
GROUP BY
    fs.id;