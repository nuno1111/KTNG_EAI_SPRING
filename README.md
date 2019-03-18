# KTNG_EAI_SPRING
KTNG EAI 연계 공통 모듈

    [HISTORY]
    - 2019-02-21 : additionalInfo추가 / 버전 1.01 패치
    - 2019-02-22 : JAVA1.6 에서 가능 / 버전 1.02 패치
    - 2019-03-18 : Header Encoding 제거 / 버전 1.03 패치

## EaiSpring : 전자정부프레임워크(Spring)를 사용하는 경우 샘플

- REPOSITORY : KTNG Nexus 사용

#### POM.XML 셋팅 : 

    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-web</artifactId>
        <version>${spring.maven.artifact.version}</version>
    </dependency>
	
	<dependency>
	    <groupId>com.fasterxml.jackson.core</groupId>
	    <artifactId>jackson-databind</artifactId>
	    <version>2.5.1</version>
	</dependency>
	
	<dependency>
	    <groupId>com.ktng</groupId>
	    <artifactId>eai-spring</artifactId>
	    <version>1.03</version>  
	</dependency>

## EaiBasic : 일반 Java 기본 API 사용한 샘플

- json-20160212.jar IMPORT 필요


# DBLINK 제거 및 개선안

MDM 시스템이 MISDB / KTG 계정 DBLINK걸어서 데이터를 가져오거나 입력한다고 가정하여 설명

### CASE 0) DB LINK 된 테이블 데이터 입력/수정

    INSERT INTO TABLE_A@DL_MISDB_KTG VALUES (...)
    
##### 적용방안 : MDM 시스템에서 REST 입력/수정 개발

### CASE 1) 단순조회 

    SELECT * FROM TABLE_A@DL_MISDB_KTG

##### 적용방안 : MDM 시스템에서 REST 조회 개발

### CASE 2) DL 테이블끼리 단순 JOIN 조회

    SELECT * FROM 
    TABLE_A@DL_MISDB_KTG A, 
    TABLE_B@DL_MISDB_KTG B 
    WHERE A.KEY = B.KEY

##### 적용방안 : MDM 시스템에서 REST 개발

### CASE 3) LOCAL 테이블과 DL LINK 테이블이 JOIN 등 복잡한 쿼리 / 시스템 내 다수의 쿼리에서 DB Link된 테이블을 이용     

    SELECT * FROM 
    TABLE_A A, 
    TABLE_B@DL_MISDB_KTG B 
    WHERE A.KEY = B.KEY

##### 적용방안 : MDM 시스템에서 Interface Table을 생성하고, MISDB에서 DB Link된 테이블의 REST 입력/수정 개발

### 예외사항) NEAR REAL TIME DB TO DB
##### 적용방안 : REST가 기술적으로 불가능한 시스템 ( 패키지 / Old Language / Old Platform )
