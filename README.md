## Dowajoyak 의 리팩토링 버전입니다.
#### 오리지널 : https://github.com/codestates-seb/seb43_main_009

---

### Commu API
- 기존 인증 로직없이 진행되던 API 에서 JWT Token 을 이용한 인증과정 추가
- 기존의 비지니스 로직에서 수행되는 일이 최소한으로 구성되게끔 메서드 재구성
- 공통된 양식의 Response Dto 에 담아 전달하게끔 구성

### Comment API
- JWT Token 을 이용한 사용자 인증 추가

### User API
- 기존의 비지니스 로직에서 수행되는 일이 최소한으로 구성되게끔 메서드 재구성
- 공통된 양식의 Response Dto 에 담아 전달하게끔 구성

### Survey API
- Survey 관련 로직을 수행하는 Service 추가 후 적용
- JWT Token 을 이용한 사용자 인증 추가

### S3 API
- 안정성을 위해 비지니스 로직을 최소한의 메서드 단위로 쪼개 service 클래스에 새로 만들고 DI
- JWT Token 을 이용한 사용자 인증 추가

### JWT Refresh API
- 기존에 token 인증 과정에서 누락됐던 사용자 인증 과정 추가

### Search API
- 외부 API 호출하여 response 받아오는 메서드 분리
- JSON response 에서 Dto 클래스로 변환해주는 클래스 생성 후 DI
- JWT Token 을 이용한 사용자 인증 추가