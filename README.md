# invetory

### 용도
집에 있는 식재료 잔량과 가치를 관리해보자

### 명령어
```
bash build.sh 

# bash 없으면 아래 직접 실행
mvn clean package;docker compose -f docker-compose.dev.yml up -d --build --force-recreate

# 제거하기
docker compose -f docker-compose.dev.yml down
docker volume rm householdledger_mariadb-data

# 로그 
docker logs -f spring-tomcat

# db 접근 후 테이블, 데이터 직접 보기
docker exec -it mariadb mariadb -u HJH -pHJH
use householdledger;
show tables;
select * from food_stock;


# 톰캣 인코딩 확인
docker exec -it spring-tomcat bash
java -XshowSettings:properties -version 2>&1 | grep file.encoding


```

### config
- MultipartConfig.java -> 파일 업로드 config
- TransactionConfig.java 
- WebConfig.java
- MyBatisConfig.java
- SessionConfig.java


