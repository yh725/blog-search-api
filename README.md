# 블로그 검색 서비스

------------
## 모듈 설명
### blog-api-core
공통 Entity, Repository

### blog-api-server
API 제공 서버

### blog-batch-server
스케줄러를 통한 인기 검색어 Top10 처리 서버(1분마다 갱신)

### blog-h2-server
TCP 접근이 가능한 인메모리 h2 기동 서버

------------
## 파일 링크
GitHub :
```
https://github.com/yh725/blog-search-api/tree/main/jar
```
blog-h2-server.jar :
```
https://drive.google.com/file/d/1be2VlyFktsOFXcHNRR_c2R1YWJJpL82x/view?usp=share_link
```
blog-api-server.jar :
```
https://drive.google.com/file/d/1OkieeYlkweU6SQMTnNw0jpOVI0kGywYj/view?usp=share_link
```
blog-batch-server.jar :
```
https://drive.google.com/file/d/1hc7UUbwaxNY1zfVuyNzWFs7xgxMrEi1X/view?usp=share_link
```
## 기동 순서 
blog-h2-server.jar(제일 먼저 실행) >
blog-api-server.jar, blog-batch-server.jar
```
java -jar blog-h2-server.jar
java -jar blog-api-server-1.0-SNAPSHOT.jar
java -jar blog-batch-server-1.0-SNAPSHOT.jar
```
------------
### 아래 URL로 API 명세서 확인 및 테스트 가능합니다.(swagger)

```
http://localhost:9080/swagger-ui/index.html#
```
### 데이터 베이스 확인 (h2)
```
http://localhost:9085/h2-console
jdbc:h2:tcp://localhost:9086/mem:db
```
## 블로그 검색 API
### Request
```
GET http://localhost:9080/blog/search/api

ex) http://localhost:9080/blog/search/api?query=검색&sort=recency&page=1&size=10

query : 검색어 (필수값)
sort : 정렬 방식 (accuracy:정확도순, recency: 최신순) : default = accuracy
page : 결과 페이지 (1 ~ 50) : default = 1
size : 한 페이지 문서 수 (1 ~ 50) : default = 10
```
### Response
```
{
    "data" : {},
    "message" : "",
    "currentPage" : 0,
    "size" : 0,
    "totalPages" : 0
}
```
------------
## 인기 검색어 조회 API

### Request
```
GET http://localhost:9080/blog/search/api/top

ex) http://localhost:9080/blog/search/api/top?size=10

size : 인기 검색어 목록 수 (1 ~ 10) : default = 10
```
### Response
```
{
    "data" : {},
    "message" : "",
}
```
------------
## 사용 라이브러리
```
validation : 파라미터 검증을 위해 사용
springdoc : swagger API 명세서를 제공하기 위해 사용
httpclient : RestTemplate 설정에 사용
modelmapper : 객체 Mapping을 위해 사용
```
