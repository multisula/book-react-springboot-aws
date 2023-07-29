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
  - Security 에서는 버전 다르면 호환 안되는 부분이 많아서 인터넷 검색해봄
    - 그런데 그냥 책에 있는대로 해도 됐었는듯
  - 스프링 2.5 버전으로 작성된 책에서는 WebMvcConfigurer를 implements해서  WebMvcConfig를 적용
    - 나는 스프링 3.1 버전이라 spring boot 3 CORS 검색하여 spring 홈페이지에서 나오는 @Bean 등록방법 사용. 정상적 동작.
- fetch() 함수를 이용해서 간단하게 api 요청할 수 있다.
  - 다시 그걸 사용하는 call(api, url, request) / return json 함수를 만드니 더욱 간단했다.
  - 자바스크립트는 프론트엔드 개발자들이 아름답게 쓰는 듯 하다.
## 4장 인증 백엔드 통합
#### JWT 토큰 설정에서 문제발생
- io.jsonwebtoken.lang.UnknownClassException:
Unable to load class named [io.jsonwebtoken.impl.DefaultJwtBuilder] from the thread context, current, or system/application ClassLoaders.
All heuristics have been exhausted.
Class could not be found.
Have you remembered to include the jjwt-impl.jar in your runtime classpath?
  - 해결: 기존에 jjwt 가 최신화되면서 jjwt-api랑 jjwt-impl 로 쪼개졌는데 api만 가져다 써서 에러발생<br>
    라이브러리 끌어다쓰니까 해결됨 그러나 다음 문제 발생

- io.jsonwebtoken.security.WeakKeyException:
The signing key's size is 96 bits which is not secure enough for the HS512 algorithm.
The JWT JWA Specification (RFC 7518, Section 3.2) states that keys used with HS512 MUST have a size >= 512 bits (the key size must be greater than or equal to the hash output size).
Consider using the io.jsonwebtoken.security.Keys class's 'secretKeyFor(SignatureAlgorithm.HS512)' method to create a key guaranteed to be secure enough for HS512.
See https://tools.ietf.org/html/rfc7518#section-3.2 for more information.
  - 해결: SECRET 키가 너무 짧아서 뜬 오류 length가 512이상 돼야 한다. 그러나 또다른 문제

- io.jsonwebtoken.impl.lang.UnavailableImplementationException:
Unable to find an implementation for interface io.jsonwebtoken.io.Serializer using java.util.ServiceLoader.
Ensure you include a backing implementation .jar in the classpath, for example jjwt-impl.jar, or your own .jar for custom implementations.
  - 해결: https://github.com/jwtk/jjwt/issues/573 참고<br>
    // https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson<br>
    runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-jackson', version: '0.11.5'<br>
    라이브러리 추가함. 에러가 jjwt-impl.jar 로 misleading 하고있지만 실제로는 Serialize 가 안되서 발생한 것으로 보임

