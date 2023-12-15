## Dowajoyak 의 Backend 코드 리팩토링 버전입니다.
#### 오리지널 : https://github.com/codestates-seb/seb43_main_009

---

### Commu API
- 기존 인증 로직없이 진행되던 API 에서 JWT Token 을 이용한 인증과정 추가
    - 기존에 JWT token 을 운용하지만 API 에 인증 로직이 없었던 문제가 있었음.
    - : JWT token 의 활용성을 높이고 인증 로직이라는 공통의 관심사를 분리하여 AOP 의 개념을 적용하였음.

- 기존의 로직에서 수행되는 일을 분산시켜 구성되게끔 메서드 재구성
    - (MVC 패턴을 기반) Controller 에서 필요하지 않은 일을 하고 있었고 비지니스 로직이 포함되어 있었음
    - : Spring MVC 패턴에 맞게 각 클래스에서 해야되는 일을 하게끔 하였고, 서로의 존재를 모른 채 DI 받아 사용하게끔 하였음.

- 공통된 양식의 Response Dto 에 담아 전달하게끔 구성
    - API 마다 JSON 의 형태가 달라서 parse 하기 귀찮은 문제가 있었음.
    - : Response 라는 공통의 관심사를 같은 구조를 가질 수 있도록 공통의 Response 클래스를 만들어 AOP 의 개념을 적용하였음.
  

### Comment API
- JWT Token 을 이용한 사용자 인증 추가
    - 기존에 JWT token 을 운용하지만 API 에 인증 로직이 없었던 문제가 있었음.
    - : JWT token 의 활용성을 높이고 인증 로직이라는 공통의 관심사를 분리하여 AOP 의 개념을 적용하였음.
  

### User API
- 기존의 로직에서 수행되는 일을 분산시켜 구성되게끔 메서드 재구성
    - (MVC 패턴을 기반) Controller 에서 필요하지 않은 일을 하고 있었고 비지니스 로직이 포함되어 있었음
    - : Spring MVC 패턴에 맞게 각 클래스에서 해야되는 일을 하게끔 하였고, 서로의 존재를 모른 채 DI 받아 사용하게끔 하였음.
  

- 공통된 양식의 Response Dto 에 담아 전달하게끔 구성
    - API 마다 JSON 의 형태가 달라서 parse 하기 귀찮은 문제가 있었음.
    - : Response 라는 공통의 관심사를 같은 구조를 가질 수 있도록 공통의 Response 클래스를 만들어 AOP 의 개념을 적용하였음.
  

### Survey API
- Survey 관련 로직을 수행하는 Service 추가 후 적용
    - Survey 관련 API 를 수행하는 비지니스 로직이 분리되지 않고 하나의 클래스에서 수행되어 MVC 패턴을 위반하는 문제가 있었음.
    - : 비지니스 로직을 담당하는 service 클래스를 만들어 controller 에 DI 시킴으로써 MVC 패턴을 적용함.

- JWT Token 을 이용한 사용자 인증 추가
    - 기존에 JWT token 을 운용하지만 API 에 인증 로직이 없었던 문제가 있었음.
    - : JWT token 의 활용성을 높이고 인증 로직이라는 공통의 관심사를 분리하여 AOP 의 개념을 적용하였음.
  

### S3 API
- S3 관련 로직을 수행하는 Service 추가 후 적용
  - 기존 S3 비지니스 로직을 config 클래스에서 수행하는 문제점이 있었음.
  -  : config 클래스에선 configuration 만, controller 클래스에선 view 의 역할만 할 수 있도록 비지니스 로직을 수행하는 service 클래스를 정의하여 controller 클래스에 DI 시켜 MVC 패턴의 개념을 적용하였음.

- JWT Token 을 이용한 사용자 인증 추가
  - 기존에 JWT token 을 운용하지만 API 에 인증 로직이 없었던 문제가 있었음.
  - : JWT token 의 활용성을 높이고 인증 로직이라는 공통의 관심사를 분리하여 AOP 의 개념을 적용하였음.

### JWT Refresh API
- Token refresh 관련 로직을 수행하는 Service 추가 후 적용
  - 기존에 controller 에서 비지니스 로직이 수행되던 문제가 있었음.
  - : 리팩토링 하며 만든 사용자 인증 메서드 등을 재활용하여 사용하는 로직으로 재정의 후 service 클래스에 정의하여 controller 클래스에 DI 시켜 MVC 패턴의 개념을 적용하였음. 

### Search API
- 비지니스 로직의 분리
  - Controller 에서 필요하지 않은 비지니스 로직이 포함되는 문제가 있었음.
  - : 비지니스 로직은 service 클래스에서 수행할 수 있도록 코드를 재구성 하여 MVC 패턴의 개념을 적용하였음.

- 외부 API 호출하여 response 받아오는 메서드 분리
  - Search service 에서 필요하지 않은 일을 한 번에 수행하는 문제가 있었음.
  - : 외부 API 와 통신하는 클래스를 새로 만들어 DI 시켜 코드를 재활용 할 수 있게 AOP 의 개념을 적용하였음.

- JSON response 에서 Dto 클래스로 변환해주는 클래스 생성 후 DI
  - Service 클래스에서 필요하지 않은 mapping 작업을 수행하는 문제가 있었음.
  - : Mapper 클래스에 메서드를 정의시켜 mapper 클래스에서 할 일로 분리시켜 MVC 패턴과 AOP 의 개념을 적용하였음. 

- JWT Token 을 이용한 사용자 인증 추가
  - 기존에 JWT token 을 운용하지만 API 에 인증 로직이 없었던 문제가 있었음.
  - : JWT token 의 활용성을 높이고 인증 로직이라는 공통의 관심사를 분리하여 AOP 의 개념을 적용하였음.