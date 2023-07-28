# React.js, 스프링 부트, AWS로 배우는 웹 개발 101 2/e, 김다정
## 2장 백엔드 개발
### JPA Entity Id를 설정할 때 system-uuid 설정
- @Id<br>
  @GeneratedValue(generator = "system-uuid")<br>
  @GenericGenerator(name="system-uuid", strategy = "uuid")

## 3장 프론트엔드 개발
전체적으로 잘 모르겠지만 흐름에 몸을 맡겼다.
### 프론트엔드 개발 환경 설정
- node, npm 은 nvm으로 사용
  - node 버전 맞춰서 12.16.1 사용 하려했으나 React 에서 거부 -> node 14버전 사용
  - React는 버전 맞춰서 17 사용

### 프론트엔드 서비스 개발
- 어느 블로그에서 말하기를 최근 React에서 class 스타일은 deprecated, function 방식을 채택했다고 한다.
  - 뭘 알아야 바꾸든지 하지 default로 function 방식으로 생성된 것을 class 방식으로 조금 바꿨더니 정상적으로 동작했다.
- props와 state
  - props 는 인자를 전달할 때 사용하고
  - state 자기가 요소를 가지고 있을 때 사용하는 것으로 보인다.
- UI를 꾸밀 때 material-ui 이용
- 어떤 동작을 할 때 일어날 수 있는 이벤트를 고려하여 해당 이벤트핸들러를 만들고, 파일 간 연결시키고, 등록해준다.
  - 변경이 일어나면 this.setState() 메서드로 state 변경 사항을 React가 알 수 있도록 한다. React는 이후 렌더링 다시 하는 듯.

### 서비스 통합
- CORS
  - 프론트쪽에서 백엔드 서버에 api 요청을 보내면 나오는 에러
  - 스프링 2.5 버전으로 작성된 책에서는 WebMvcConfigurer를 implements해서  WebMvcConfig를 적용
    - 나는 스프링 3.1 버전이라 spring boot 3 CORS 검색하여 spring 홈페이지에서 나오는 @Bean 등록방법 사용. 정상적 동작.
- fetch() 함수를 이용해서 간단하게 api 요청할 수 있다.
  - 다시 그걸 사용하는 call(api, url, request) / return json 함수를 만드니 더욱 간단했다.
  - 자바스크립트는 프론트엔드 개발자들이 아름답게 쓰는 듯 하다.
