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
