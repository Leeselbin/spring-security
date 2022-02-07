# Use Intelilj and maria DB , defendency

- Spring security, Spring Web , Lombok , Jpa , Devtool

```
#build.gradle

maria db
	implementation group: 'org.mariadb.jdbc', name: 'mariadb-java-client', version: '2.7.3'
	implementation group: 'org.javassist', name: 'javassist', version: '3.15.0-GA'

JWT
    https://mvnrepository.com/artifact/com.auth0/java-jwt
	implementation group: 'com.auth0', name: 'java-jwt', version: '3.10.2'
```

```
#application.properties

##maria DB
spring.datasource.username= 마리아디비아이디
spring.datasource.password= 비밀번호
spring.datasource.url=jdbc:mariadb://localhost:3306/본인스키마
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

```

# spring-security

스프링 시큐리티 , gradle,springboot,

## 시큐리티 가 가지고있는 세션정보 등

## 시큐리티 세션에는 -> Authentication 객체만 들어갈수있다.

- Authentication 객체가 들고있는 필드 (담을수있는 필드)

- OAuth2User타입이랑 , UserDetails 타입이 -> Authenticatio 객체에 담을수 있다.

- 회원가입을 하면 User 오브젝트가 필요하다

- 결론 : PrincipalDetails 에다가 User오브젝트를 품어놧다. 거기다 UserDetails와 OAuth2User를 implements한다.
- 그냥 PrincipalDetails 에다가 다때려박는다 ^^

### SecurityConfig 시큐리티 기본설정

-구글 로그인이 완료된 뒤의 후처리가 필요하다. 1.코드받기(인증) 2.엑세스토큰(권한) 3.사용자프로필 정보를 가져온다
4-1. 그정보를 토대로 회원가입을 자동으로진행시키기도함
4-2(이메일,전화번호,이름,아이디) 쇼핑몰의 경우 (집주소)같은게 필요함
추가적인 회원가입이 되어야한다.

## PrincipalOauth2UserService 로직

- 구글로그인버튼 클릭 -> 구글로그인창 -> 로그인을 완료 -> code를 리턴(Oauth-client라이브러리) -> AccessToken요청
- OAuth2UserRequest userRequest -> userRequest정보 -> loadUser함수 호출 -> 구글로부터 회원 프로필을 받아준다.

---

# JWT (Json Web Token)

- 왜사용되고 어디에 쓰는지 중점으로 알아두자
- 세션의 고질적인 문제 해결

# 통신: OSI 7계층

예를들면 스타크래프트

- 응용계층 = 어떤 데이타를 보내는배경

- 표현 계층 = 데이타가 암호화, 압축

- 세션 계층 = 인증 체크(상대방 컴퓨터 켜져있는지, 접근가능한지 등등)

- 트랜스포트 계층 = TCP/UDP할지 결정

- 네트워크 계층 = 중간 라우터를 통한 라우팅을 포함하여 패킷 포워딩을 담당

- 데이터링크 계층 = 신호를 전달하는 물리계층을 이용하여 네트워크 상의주변 장치들간 데이터전송

- 물리계층 = 실제로 데이터 전송된다.

#### TCP

- 웹에서 쓴다
- 신뢰성 있는 통신, 속도 느리다

#### UDP

- 사람이 이해할수 있는일 , 전화할때 쓴다.
- 신뢰성 X , ACK신경안쓰고 막보내도된다, 속도 빠르다

02/04 ...

---

## JWT (Json Web Token)

- 구성은 Header.Payload.signiture 로구성되어있고 각각 Base64로 암호화되어있다.
- Base64로 디코딩이 가능. 암호화의 목적이아닌 서명의 목적이있다.
- 데이터가 유효한지 무효한지에 무결성을 확인 목적.
- HMAC SHA256
- 서버들이 여러개가 있을때 세션을 사용하지 않고 토큰검증만 하면되서 클라이언트 입장에서 막진입해도 상관이 없다.
- 서버들이 secret키만 알고있으면 된다.
