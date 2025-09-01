# 점프 투 스프링부트3 모르는 점 및 용어정리

## 위키독스 링크: [https://wikidocs.net/160023](https://wikidocs.net/160023)
## AWS Lightsail: [https://lightsail.aws.amazon.com/](https://lightsail.aws.amazon.com/)
### 라이브러리 및 인터페이스 참고 사이트(docs) 
> Bootstrap : [https://getbootstrap.com/docs/5.3/getting-started/introduction/](https://getbootstrap.com/docs/5.3/getting-started/introduction/)
> 
> Thymeleaf : [https://www.thymeleaf.org/documentation.html](https://www.thymeleaf.org/documentation.html)
> 
> Specification : [https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)

* STS (Spring Tool Suite) : 이클립스 기반 IDE. 스프링 프레임워크 개발/관리 도구 제공.


* Tomcat: 자바 기반 WAS

  * http요청을 받아 서블릿과 jsp 실행
 
  * 서블릿 (Servlet): 동적 웹페이지 개발에 사용되는 자바 기반 웹app 프로그래밍 기술.
  
  * jsp (JavaServer Pages): html코드에 java 코드를 넣어 동적 웹페이지를 생성하는 웹app 도구. jsp 실행시, 서블릿으로 변환돼 서버 동작.


* 페이징 기능 : 홈페이지를 쪽수 단위로 나누는 기능(1쪽,2쪽,..)


* REST : 클라이언트가 자원(URL)을 기반으로 API 설계 방식(CRUD 등)을 요청하는 구조


* SSL : 웹사이트와 브라우저 간의 데이터 암호화하는 기술


* Gradle : 개발부터 배포까지 자동화를 통해 빠르게 사용자들이 이용할 수 있게 (CI/CD) 하기 위한 Groovy 기반 오픈소스 빌드 도구
  * Groovy : 자바 기반의 OOP 언어. 기존 언어보다 동적이고 유연하며 컴파일 없이 실행가능한 스크립팅 언어


* DTO (Data Transfer Object) : 프로세스 간 데이터 교환을 위해 사용하는 객체


* JPA (Java Persistence API) : 자바 진영에서 ORM 기술 표준으로 사용하는 인터페이스 모음. 자바 app에서 RDB를 사용하는 방식을 정의한 인터페이스.


* ORM (Object Relational Mapping) : 객체와 RDB의 데이터를 자동으로 매핑해주는 것


* bean : 스프링 컨테이너가 관리하는 인스턴스화된 자바 객체


* 컨테이너 : 독립적인 프로세스 실행 환경을 제공하는 경량 가상화 기술


* DDL (Data Definition Language) : 데이터 정의어 DB를 정의하는 언어. DB 테이블을 CRUD하는 언어.


* spring.jpa.hibernate.ddl-auto 설정을 운영환경에서 none 이나 validate 인 이유 

  * 데이터 손실 방지와 안정성 확보 때문에
 
  * update로 인해 db가 자동 변경되면 데이터 손실 가능성이 큼. 떄문에 수동으로 직접 관리.


* cascade : 부모 엔티티의 특정 작업(CRUD)이 자식 엔티티에게도 전파되도록 하는 설정.

  * JPA에서 @OneToMany, @ManyToOne, @OneToOne, @ManyToMany 관계 설정시 사용
    * 다음 어노테이션같은 양방향 관계 설정 시, MappedBy 필수! : 관계가 있는 두 객체가 서로 상대 객체의 데이터를 수정하려 하면 그것은 무결성 저해!! 때문에 한쪽 객체에게만 수정 권한을 부여해야함.
      * MappedBy에 정의되지 않은 객체가 수정 권한을 부여받게 됨
 
  * CascadeType.REMOVE : 부모 삭제 시 자식도 삭제

  * CascadeType.ALL : 부모 변경이 자식에게 모두 전파
 
  * orphanRemoval=true : 부모가 자식을 리스트에서 제거(자식과의 관계를 제거)하면 자식이 삭제됨
 

* repository : 개발자가 app 소스 코드에 대한 변경을 수행/관리하는데 쓰이는 중앙화 디지털 저장소

* @Autowired : Spring이 관리하는 Bean을 찾아 자동으로 주입해주는 역할.

* 순환 참조 문제 : A클래스가 B클래스의 Bean을 주입받고, B클래스가 A클래스의 Bean을 주입받는 문제

* Optional<T> :  null 체크를 보다 안전하고 명확하게 처리하기 위한 제네릭. 값이 존재할수도, 안할수도 있다는 사실을 암시함. JPA와 함께 자주 쓰임.

  * findById() 같은 PK 기반 조회는 기본적으로 Optional<T>을 반환.

* this.Repository.save(q) 에서 save(q)는 q에 담긴 PK값을 통해 레포지토리에서 검색하여 일치하는 필드가 존재하면, 해당 필드 업데이트. 일치하는게 없다면, 해당 필드를 새로 업로드함.

* Transaction : Db관리 시스템에서 상호작용의 단위. 더 이상 쪼개질 수 없는 최소의 연산.

* Lazy Loading (지연 로딩) : 객체를 처음 조회할 때 연관된 엔티티를 바로 가져오지 않고, 나중에 필요할 때 가져오는 방식.

* (테스트 코드에서) @Transactional을 쓰지 않았을때, A 엔티티를 조회(A엔티티에 대한 트랜잭션 시작) 후 연관된 B 엔티티를 뒤늦게 조회하려 할때 A엔티티에 대한 트랜잭션이 종료되면서 그대로 종료됨. 때문에 @Transactional을 이용하여 A엔티티 트랜잭션이 계속 유지될 수 있도록 함.

* (Spring에서) 도메인 : SW로 해결하고자하는 문제 영역

* 템플릿 엔진(ex. Thymeleaf) : html 문법을 유지하면서 템플릿 속성(자바 코드)을 주입하여 동적 웹app 구현.
  * jsp와의 차이점 :
    * jsp - 레거시 도구, 가독성이 낮고, 브라우저에서 열기 어려움. html+자바 코드.
    * 템플릿 엔진 - 최신 스프링, 스프링 부트와 잘 맞음. 가독성이 높고, 브라우저에서 열기 쉬움. html+템플릿 속성.
  * 템플릿 엔진 사용 시 @ResponseBody가 필요없는 이유 : 해당 어노테이션은 리턴값을 http response body로 보냄. 하지만 템플릿 엔진을 사용했을때 리턴값(String)은 템플릿 파일의 이름을 의미함. HTML을 템플릿으로부터 만들어 응답을 보냄.
    * String을 리턴하는 메서드가 존재할 경우, @ResponseBody는 해당 리턴값의 String을 그대로 클라이언트에게 전송. @Controller를 통해 (View를 전달하려는 목적이라면)해당 리턴값의 String과 동일한 이름을 가진 템플릿 파일(.html)을 탐색하여 전송


* html에서 <th> = table head, <th: . . . > = thymeleaf의 속성


* SpringBoot에서 String 타입 메서드의 리턴타입이 어떤 응답(액션)을 취하는지 어떻게 정해질까?
  * SpringBoot의 ViewResolver라는 컴포넌트를 통해 Spring 리턴값을 보고 redirect 및 forward 등을 수행


* DTO 클래스와 @Service 클래스 차이
  * DTO는 엔티티 클래스와 직접적으로 닿아있고 해당 엔티티의 DB를 Getter, Setter 를 통해 관리 및 불러오기. @Service는 직접적인 서비스 로직(CRUD)를 수행.
  * DTO에서 불러온 데이터를 통해 @Service 클래스에서 액션을 수행함.


* | . . . | : 문자열 안에 { } 표현식을 동적으로 사용케 함 (선택사항)
  * || 가 없이 그냥 쓰면 글자 그대로 출력하지만, 있다면 리터럴 대체문자로써 ||내부에 편리하게 작성 가능.
  
* Thymeleaf 에서의 값 표현 구문 5가지
  * @{ . . . } : 링크 표현식(URL을 동적으로 작성)
  * ${ . . . } : 변수에 접근하는 표현식
  * *{ . . . } : 선택변수에 접근하는 표현식
  * #{ . . . } : 메세지 표현식
  * ~{ . . . } : fragment 표현식
  >> https://bnzn2426.tistory.com/140
 
   
* HttpStatus.NOT_FOUND : 404 에러코드를 담음


* SerialVersion / SerialVersionUID : 해당 클래스의 버전을 명시. java.io.Serialization 을 import 하여 사용. 해당 클래스 직렬화 과정에서 serialversionUID의 버전이 포함되고, 역직렬화 과정에서 동일한 버전의 클래스를 불러옴.


* 직렬화 (Serialization) : 복잡한 데이터 구조의 클래스의 객체를 바이트 형태로 변환하여 네트워크 전송, 파일 저장에 사용되는 기술. (역직렬화는 반대)
  * 자바 컴파일과 다른점: 
    * 컴파일은 .java 코드를 바이트 코드 파일(.class)로 변환하여 JVM이 실행할 수 있도록 함. 
    * 직렬화는 메모리에 존재하는 객체를 바이트 스트림(네트워크, 파일)으로 변환하여 저장.


* <form th:action=" "> 태그 : form 태그가 어디로 데이터를 받을지 지정하는 주소(url)
 
  * <form method=" "> 태그 : 해당 form태그가 get방식일지, post방식일지 결정.
  
  * 매핑 어노테이션 을 통해 해당 action의 값(url)과 동일한 url을 제시할 경우, 두 메서드끼리 매핑되어 데이터를 클라이언트가 받아 쓸 수 있게됨.


* Model 객체는 언제 쓰이는가? : Spring에서 컨트롤러 메서드가 템플릿(HTML)에 데이터를 넘겨줘야할 때 이용


* Bootstrap : 웹사이트를 쉽게 만들 수 있게 도와주는 CSS, JS 프레임워크. class를 태그마다 지정만 하면됨.
>> docs : https://getbootstrap.kr/docs/5.3/getting-started/introduction/


* <th:each=". . . , loop : ${. . .}"> 에서의 loop: 현재 반복 상태에 대한 횟수 및 순서를 보여주는 Thymeleaf 템플릿 기능


* 표준 HTML 작성 방식을 따르지 않으면 브라우저는 Quirks Mode를 이용해 해석함.
  * 하지만, Quirks Mode는 브라우저마다 다르게 작동/해석하고, JS 코드가 꼬일 수 있으며, 접근성이 떨어질 수 있어 표준 html 작성은 필수임.
 

* Spring Boot Validation 라이브러리를 통해 사용자의 입력값 검증 가능
  * https://jakarta.ee/specifications/bean-validation/3.0/apidocs/

 
* Thymeleaf에서 #(샵)은 Thymeleaf 표준 객체(유틸리티 객체)에 접근할때 쓰는 문법. (유틸리티 객체 ex. Java에서 Math.random())
>> Validation 에러 메시지 docs - https://www.thymeleaf.org/doc/tutorials/3.1/thymeleafspring.html#validation-and-error-messages


* Spring MVC에서 컨트롤러 메서드의 매개변수가 @RequestParam, @PathVariable, @ModelAttribute, 혹은 그냥 POJO 일 경우, 매개변수에만 해당 바인딩할 객체를 적어놓아도 자동으로 Spring에서 바인딩을 진행.
  * 바인딩 이후, 동시에 Model에 등록돼 View로 넘어감. 즉, Model model 을 통해 View로 넘기는 행위를 자동으로 해줌.
  * POJO (Plain Old Java Object) : 객체 지향적인 원리에 충실하며, 특정 환경과 기술에 종속되지 않고 필요에 따라 재활용 가능한 방식으로 설계된 순수한 오브젝트

* Spring에서 클라이언트에게 받은 요청을 자바 객체에 바인딩해주는 어노테이션
  * @RequestParam : 1개의 HTTP 요청 파라미터를 받기 위해 사용됨. RequestParam을 설정한 매개변수는 요청 메시지를 받을때 무조건 할당받아야함.
    * HTTP 요청 파라미터에 해당 매개변수를 일일히 설정 안해도 이용가능하게 하는 법 : required를 false로 만들어 필수 할당을 해소해주거나, defaultValue 설정을 통해 기본값을 지정해주기
  * @PathVariable : 경로 변수를 표시하기 위해 메서드의 매개변수에 사용. 'Get(또는 Post)Mapping( ...{경로 변수} )' 를 이용할때 URL에 있는 경로 변수 값을 추출하여 매개변수에 할당.
  * @ModelAttribute : 폼 형태의 HTTP Body 및 요청 파라미터를 자바객체(DTO, 폼 클래스)에 삽입(바인딩)해줌. 요청파라미터 이름과 변수 이름이 동일하면 어노테이션 생략 가능.
    * 어노테이션 괄호 내의 String : Model에서 쓰일 해당 객체의 이름. (ex. @ModelAttribute("commentForm") CommentForm commentForm == model.addAttribute("commentForm", commentForm) )


* GET 방식에서 특정 파라미터 값을 가져오려면 ? 와 & 를 이용
  * ? : 첫번째 파라미터인 경우
  * ^ : 첫번째 이후의 파라미터인 경우


* th:classappend="조건식 ? 클래스_값" 은 조건식이 true일 때 해당 클래스 값을 class 속성에 추가함.


* Spring을 통해 페이지에 보이는 정렬 순서를 조정하려면 Sort객체 이용


* AntPathRequestMatcher( . . . ) : 사용자가 요청한 요청 정보를 확인하여 요청한 URL이 매개변수에 지정한 URL과 일치하는지 확인함.


* Spring 메서드에서의 매개변수(URL) '/**' : 경로의 모든 하위 경로들을 매핑
  * '/*' : 경로의 바로 하위 경로를 매핑

 
* CSRF공격 : 사용자의 인증 정보를 도용하여, 사용자가 의도하지 않은 요청을 서버에 보내는 공격 방식.
  * Spring Security 에서의 CSRF 방지 시스템
    1. 브라우저가 처음으로 웹 페이지 요청
    2. 서버간의 세션 생성 + 서버에서 CSRF 토큰 생성
    3. 세션에 고유한 CSRF 토큰 정보 저장
    4. 추후 브라우저에서 서버에 요청 시, 유효성 검사 실행
    5. 서버는 전달받은 CSRF 토큰과 세션에 있는 토큰을 비교
       *  일치하면, 통과
       *  불일치하면, 403 Forbidden

      
* 클릭재킹 : <iframe> 으로 감싸 숨겨놓은 URL을 클릭하도록 유도하는 공격 방식. 사용자는 의도하지 않은 서비스를 이용하게 만들어냄.
  * /h2-console 은 iframe으로 구성돼있어 Spring Security에서 차단함. 때문에 별도로 조정 필요.


* Column 어노테이션에 columnDefinition="TEXT" 을 지정하지 않으면 JPA가 자동으로 매핑
  * columndefinition : JPA에서 엔티티의 필드를 테이블 컬럼으로 매핑 할 때, DB에 생성될 컬럼 구체적인 DDL 정의를 직접 지  


* unique=true를 설정한 속성은 h2-console에 서두에 "UK_"가 붙음
  * UK_ = Unique Key 


* Spring Security는 BCryptPasswordEncoder 클래스를 통해 비밀번호 및 개인정보 등의 중요 정보를 암호화
  * BCrypt 해시 함수를 통해 특정 정보를 암호화하고 검증
  * PasswordEncoder 는 BCryptPasswordEncoder 클래스의 인터페이스
    * Spring Security를 이용할 때 쓰는 PasswordEncoder matches 란?
      >> String형태의 Password를 입력하면 인코딩 되어 DB에 해시처리 됨. 따라서, 내가 찾고자 하는 password와 DB에 존재하는 password가 일치하는지 확인할때, 단순히 equals를 통해 비교하면 불일치하다고 표시될 수 있음. 이때 matches메서드를 사용하면 해시된 password의 원본형태와 직접 비교 가능해짐. 

* bindingResult : Spring에서 이용되는 검증 메서드. bindingResult의 타겟(객체)에서 발생하는 오류들을 저장함.
  >> bindingResult 매개변수 선언부분의 옆에 위치한 @ModelAttribute 객체를 타겟팅하여 작동됨! 즉, @ModelAttribute 선언은 필수!
 
  * bindingResult.rejectValue(필드명, 오류 코드, 오류 메시지) : Spring MVC 폼 검증 메서드. 
  
    * 필드명 : 오류가 발생한 객체의 필드
      
    * 오류 코드 (중요★): Spring message resolver가 찾을 수 있는 오류 코드 문자열.
      >> 해당 Arguments의 값은 'messages.'파일같은 국제화 메시지 파일에서 해당 값을 찾는 일종의 Key 역할을 함.
      * message resolver : 클라이언트가 보낸 요청 데이터를 컨트롤러 메서드의 파라미터로 바인딩하는 객체
        
    * 오류 메시지 : 기본적으로 사용자에게 보여줄 직접 작성한 오류 메시지
      >> 해당 Arguments의 값은 오류 코드에서 매칭되는 코드가 없다면 출력됨.


* href = "#" : 클릭 이벤트 발생 시 페이지 전환이 이루어지지 않고 아무런 일도 안일어남. 즉, 의미없는 링크를 의미함.


* param.error : thymeleaf에서 요청 파라미터 중 error가 포함되어 있는 값을 가져올때 쓰는 표현 (ex. 로그인 실패)
  * Spring Security에서는 로그인 요청 실패 시, 매개변수로 error를 무조건 전달
 

* enum에서는 '요소이름(. . .)' 의 형태로 상수를 작성 가능함. 해당 괄호 안에 있는 값과 요소이름은 같은 역할을 함.
  * enum 내부의 요소들은 기본적으로 불변하기에 final이 default 


* Spring Security에서는 UserDetails, UserDetailsService 인터페이스를 제공. 유저의 정보(id,비번,권한)를 관리하도록 함.
  * UserDetailsService는 UserDetails를 반환
  * UserDetails는 GrantedAuthorities를 반환


* ROLE 과 GrantedAuthority의 의 차이점: 일반적으로 둘은 동의어로 쓰임. GrantedAuthority의 권한 그 자체를 말함. ROLE은 특별한 정의는 없지만 GrantedAuthority의 대응되는 enum에 입력한 권한 요소들의 네이밍 컨밴션 서두에 "ROLE_"의 형태로 쓰임. 그리고 서두에 이런 ROLE_ 이 붙은 경우 GrantedAuthority의 취급됨.


* SimpleGrantedAuthority : GrantedAuthority의 기본 구현체(클래스)로 String을 리턴함.


* 이미 생성된 JPA 엔티티 내부의 필드이름을 수정해도 수정이전의 컬럼정보는 h2 console에 계속 담겨있음.
  * 해결법 : " ALTER TABLE '엔티티 이름' DROP COLUMN '삭제하고픈 속성 이름' " 을 h2 console에 입력하여 더이상 쓰지않는 이전의 컬럼을 삭제 


* @EnableMethodSecurity : Spring Security에서 해당 어노테이션을 설정한 메서드 및 클래의 접근 제어
  * 함께 이용되는 다른 어노테이션
    * @PreAuthorize : 메서드 호출 이전에 접근을 제한할지 말지
    * @PostAuthorize : 메서드 호출 이후에 접근을 제한할지 말지


* AuthenticationManager : Spring Security의 사용자 인증과 권한 부여 프로세스를 처리. 내가 구현한 (혹은 Bean 처리한) 사용자 인증 메서드(PasswordEncoder)와 권한 부여 메서드(UserDetailsService 구현체)가 자동으로 사용됨.


* href=" A : . . ." 의 형태에서 ':'의 뒤에 있는 A는 가상 URL. 해당 경로를 실행하지 않고 A에 해당하는 프로그래밍 언어 코드로 해석하게 유도함.


* form 태그 내부의 모든 입력필드(textarea,input,select 등)들은 name속성을 기준으로 묶어 자동으로 POST 요청을 보냄.
  * HTTP POST요청을 현재 페이지 혹은 action에 지정한 url로 전송.


* 'javascprit:void(0)' : void(0) 아무것도 하지 말라는 의미의 내장 함수.
  * a태그 하이퍼링크에 본래는 URL을 입력해 넣고 페이지를 이동한다. 하지만, 페이지 이동을 하지 않고 (이동할 URL 또한 존재하지 않음) 오직 자바스크립트 코드만 실행하고 싶을 때 다음처럼 작성할 수 있음.


* html부분에서 isAuthenticated() (즉, 로그인한 사람만)를 부여받은 권한자만 특정 html코드(수정/삭제 버튼)을 보이게 했는데, Controller에서 또 PreAuthorize를 통해 2단 검증하는 이유
  * 꼭 브라우저를 통해 접근하리라는 법이 없기 때문에 프론트엔드에서도, 백엔드에서도 모두 검증이 필요함.


* js에서의 fetch(): fetch 내부에 있는 url로의 페이지 이동을 지시함.
  * .then{ } : fetch로 페이지 이동 이후에 실행할 명령어를 적음.
  * .catch() { } : 자바의 catch와 동일

 
* AJAX (Asynchronous JavaScript and XML) : 비동기적으로 서버와 데이터를 주고 받아 웹 페이지의 전체를 새로고치지 않고 웹 페이지의 일부만 업데이되는 웹 개발 기술


* 대개 build.gradle 파일에는 implements한 라이브러리들의 버전을 접속하지 않으면, SpringBoot에서 호환성이 가장 좋은 버전들을 선별해 자동으로 실행함.
  * SpringBoot가 내부적으로 관리하는 라이브러리가 아니라면 직접 상세 버전까지 입력해줘야함!!

 
* Component (컴포넌트) : props라는 input값을 받고 UI같은 View상태에 따른 화면을 출력하는 모듈


* escape 처리 : 웹 개발에서 특정 문자가 html문서나 다른 마크업 언어로 해석되지 않게 변환하는 과정. 사용자가 입력한 텍스트를 안전하게 출력할 수 있게함.


* SQL에서 '%' : 와일드카드 문자. LIKE문과 함께 쓰임.


* 다대다(ManyToMany) 혹은 1대다(OneToMany)같이 다수의 컬럼에 대해 정렬 할때, 대개 JOIN + GROUP BY 를 통해 직접 Query문을 작성함.
  * AnswerService의 getList메서드에서는 Set<SiteUser>를 바로 Sort.Order 시키고 있음.
    >> 2025-06-21 답변 페이징 처리에 버그를 동반하여 삭제함.
    
    >> AnswerRepository에 Query문 직접 작성
   
    * Sort.Order은 단일 컬럼으로 작동되게 설계돼있음.
      * hibernate상에서 Collection을 그대로 Sort.Order하게 되면 size크기를 기준으로 정렬하는 side effect가 발생함. 때문에 순서 정렬이 되는것. 
    * 해당 코드줄은 사실 불안정함!! H2의 hibernate같은 특수 RDB가 유연하게 JOIN 처리하여 정렬된것처럼 표시됨. 즉, 내부적으로는 어떤 구조로 정렬돼있을지 보장이 안됨!


* RedirectAttributes : 지정한 URL을 redirect로 서버에 전송하면 곧이어 새로운 페이지가 요청됨. 이때 Redirect되는 과정에서 이전에 수정한 모델 데이터가 유지되지 않아 그대로 소멸한채 요청됨. 때문에 RedirectAttributes를 통해 모델 데이터를 복사해 유지할 수 있게함.


* JPA는 영속성 컨텍스트 때문에 엔티티 간의 연결을 확인하여 자동으로 관리해주는 기능이 있음.
  * ex. A엔티티에서 B에 대해 N:1관계 설정. B엔티티에서 A에 대해 1:N관계 설정. JPA는 해당 관계를 보고 트렌잭션을 동기화하여 A객체에서 B객체에 관한 CRUD 진행 시, B객체에도 똑같이 적용해줌.
  * ***주의!*** JPA 고유의 기능이므로 일반 DB의 경우. A객체에서 B객체에 대한 작업을 진행했다면, B객체에서도 A객체에 대한 작업을 동일하게 ***직접*** 명시해줘야함!!!
  * 영속성 컨텍스트 : JPA 같은 고수준 ORM 프레임워크에서 사용되는 Java/JPA 내부 개념. 서버와 DB 사이의 엔티티를 저장하는 환경으로 엔티티(객체)를 저장하거나 조회하면 엔티티 매니저는 영속성 컨텍스트에 엔티티를 보관 및 관리한다.


 * @PrePersist : 엔티티가 DB에 저장되기 직전에 호출. 즉, 영속성 컨텍스트에 처음으로 저장될 때 수행해야하는 작업.


 * @PreUpdate : 엔티티가 DB에 업데이트되기 직전에 호출. 즉, 엔티티에 갱신된 데이터를 변경하기 전에 수행해야하는 작업.


 * OAuth (Open Authorization) : 사용자(Resource Owner)의 ID/PW가 아닌 accessToken을 활용하여 나의 서비스(Client)가 연동하고자 하는 서비스(Resource Server)와 연결될 수 있게 해줌
   * accessToken : Client가 Resource Server(구글, 카카오 등)에 담겨있는 Resource Owner의 정보(Resource)를 활용하고 할때, Resource Server에 담긴 정보권한을 ID/PW 대신에 부분적으로(보안상의 이유 때문에) 접근할 수 있게 함. 일종의 Resource Owner를 보호하기 위한 특별 비밀번호.
   * OAuth의 승인 리다이렉트 URI 설정
     *SpringBoot 2 버전에서의 Security는 기본적으로 {도메인이름}/login/oauth2/code/{소셜서비스코드} 의 양식으로 리다이렉트 URL을 지원함.
       * 다음과 같은 양식으로 작성 시, 별도의 리다이렉트를 요청하는 Controller를 생성 안해도 됨.
   * OAuth2UserService 전체흐름
     1. 사용자가 소셜 로그인 요청(OAuth2) 시, Request Server에 사용자 인증을 함.
     2. 인증에 성공하면, Client에게 authorization code를 전달.
     3. Client는 authorization code를 다시 Request Server에게 전달.
     4. Request Server가 OAuth2UserRequest (accessToken) 과 ClientRegistration (어떤 소셜인지) 에 대한 정보를 제공.
     5. 모든 필요한 인자를 받았으므로 Spring Security는 OAuth2UserService의 구현체 호출 통해 loadUser를 실행


 * Spring에서는 application-{profile}.properties 라는 양식을 통해 개발환경을 직접 구현 가능토록 함.
   >> https://lejewk.github.io/springboot-gradle-spring-profiles-active/
   * profile에 원하는 입력값을 넣기
   * if) profile에 아무값도 안넣었다면? default 값으로 분류되어 application.properties로 인식됨. 


 * 순환 참조 : 2개 이상의 Bean들이 서로를 참조하면서 객체 생성 시, 무한 루프에 빠지게 되는 현상
   * 해결책 >> 객체간의 의존성을 줄이고 올바른 DI를 선택하자 


 * Customizer.withDefaults() : 람다 함수 내부적으로 아무것도 하지 않을 것이기에 기본 설정으로 구성해달라는 뜻.
   * 대개 Spring Security 등에서 활용되는 메서드


 * .properties 파일 : Key=Value 형식으로 작성한 JAVA 환경 설정 및 구성 값 정의 파일.
   * .yml 과 기능적인 면은 동일! properties보다 가독성 측면에서 우위!
  

 * @Builder : builder 패턴을 자동으로 구현해주는 어노테이션. 필드가 많은 객체일수록 생성자 대신 명확하고 유연한 객체 생성 가능.


 * MarkUp 마크업 : 문서가 화면에 표시되는 형식을 나타내거나 데이터의 논리적 구조를 명시하기 위한 컴퓨터 언어
 * MarkDown 마크다운 : 웹 콘텐츠를 작성하기 위해 쉽게 사용할 수 있는 경량 마크업 언어로, 텍스트 문서를 다양한 HTML 형식으로 변환토록 설계된 언어
   * 마크다운 에디터 : 마크다운 형식의 텍스트를 작성하고 편집할 수 있도록 하는 도구

  
 * 가정용 인터넷, 모바일 통신의 경우 '유동 IP' 사용.
   * 인터넷을 재접속하거나, 기기를 재부팅하는 경우에 IPv4가 변동됨
   * 장점: 보안 위험 감소, 설정하기 쉬움
 * 기업 서버, 클라우드 서버 등의 경우 '고정 IP' 사용.
   * IP가 고정되므로 항상 동일한 IP주소를 가짐.
   * 장점: 외부에서 접근하기 용이, DNS 설정 용이

  
 * SSH (Secure Shell) : 안전한 원격 접속을 위한 프로토콜. 서버 관리 및 개발 작업에 특화.
   * 보안 - 암호화 기술을 통해 데이터를 보호
   * 원격 접속 - 네트워크를 통해 다른 컴퓨터에 접속하여 명령을 실행하고 파일을 관리
   * 인증 - 사용자 인증과 서버 인증을 통해 보안 강화
   * 다중화 - 하나의 연결에서 여러 논리 채널을 생성하여 효율적인 전송 가능

  
 * FTP (File Transfer Protocol) : 파일 전송 프로토콜. (Port=21) 21번 포트와 20번 포트를 통해 서버와 클라이언트가 서로 명령어와 파일을 패킷으로 주고받을 수 있게함.
   >> 여기서의 클라이언트 : FTP를 사용하는 프로그램 및 원격 시스템 
   * 장점: 동시에 여러 파일을 전송가능, 연결이 끊긴 후에도 전송 재개 가능, 전송 일정 예약 가능 
   * 단점: 명령어 및 파일을 평문으로 보내기 때문에 보안에 취약함

      
 * SFTP (SSH File Transfer Protocol) : FTP에 SSH를 적용하여 보안을 강화한 SSH 확장 프로토콜. (Port=22)
   * 장점: 패킷을 암호화하여 전송하기 때문에 보안에 강력
   * 단점: 압축 여부와 상관없이 무조건 한번에 4GB 이하의 파일만 전송 가능

  
 * 서버 인스턴스를 통해 웹 페이지를 열 때, (ModaXterm 등) 무한로딩에 걸린채 제대로 열리지 않는다면?
   * 유력한 원인 : 메모리 부족 현상 -> 스왑 기능(Memory Swap)을 통해 해결하자! 
     * 스왑 기능이란 서버가 갖고 있던 RAM의 메모리가 부족할 때, 서버의 보조기억장치를 활용해 추가적인 메모리로 사용할 수 있게 해주는 기능!
     * 다만, 서버를 재부팅 할때마다 스왑 설정을 다시 해주어야함
       >> sudo fallocate -l 1G /swapfile

       >> sudo chmod 600 /swapfile

       >> sudo mkswap /swapfile

       >> sudo swapon /swapfile

       >> free -h
       
       >> 1기가만큼을 추가로 할당하겠다는 의미
       

 * 처음에 서버에 jar 파일을 배포할때, 404가 뜬다면 category db가 비어있어 DataNotFoundException이 발생했기 때문이었다.
   * Problem 쉽게 찾는 법) Properties 파일에 디버그를 위해 다음과 같은 코드를 추가 작성. ssh dash에 모든 로그내역이 뜨면서 어디에 문제가 있는지 상세하게 확인가능
     >> logging.level.org.springframework.web=DEBUG
     
     >> logging.level.org.thymeleaf=DEBUG
     
   * 해결법) 서버를 처음 접속하자마자 category db를 보고 필수적인 데이터가 없다면 바로 그 자리에서 category를 새로 생성하는 DataInit.java 를 생성


 * @Configuration : Spring 컨테이너에서 설정 클래스임을 명시함.
   * application.properties 를 대신하여 Java에서 자체적으로 Bean 설정을 작성 가능! (대신 완전 대체 가능하다는 뜻은 아님. 상호 보완적)
  
 * nano 편집기 : 리눅스 기반 OS에서 이용가능한 간단한 터미널 텍스트 편집기
 
 * properties파일들에 spring.profiles.include로 같은 것을 참조할 시, spring에선 오류로 간주! (잠재적 위험)
   * properties들이 중복적으로 include를 이용할때 차후 무한 루프가 발생할 가능성이 존재함. 따라서, 원천적으로 차단시킴.
     >> 예시로, 영화관에서 외부 음식 반입 금지 문구 써있음. 하지만 내 가방엔 생수 한통만 있음. 실질적으론 당장 아무 문제 없지만 모두 통일된 규칙에 따라야함.    

 * 80번 포트: HTTP 포트로 서버를 해당 포트로 열 시, HTTP에서 HTTPS로 리디렉션과 SSL 인증서 자동 발급 및 갱신 역할을 수행함.
   * 암호화 되지않은 http 도메인을 80번 포트가 받으면 https 도메인 (443번 포트)로 리디렉션
     * 이러한 리디렉션을 위해선 일일히 로직을 설정해주어야함
       >> NginX , SpringBoot 를 통해 관리 가능
   * Let's Encrypt 라는 기관에게 SSL 인증서를 받음으로써 https 도메인 사용 가능

 * proxy_set_header : Nginx가 서버(내 PC)에게 보내는 요청을 중개해줌으로서 클라이언트의 요구를 받을 수 있게 해줌. 하지만, 서버는 Nginx의 요청을 받는 것이기에 Niginx의 서버 주소만 알고 있고 클라이언트의 정보는 모름. 때문에 proxy_set_header를 이용하면, Nginx에게 요청한 클라이언트의 정보를 함께 서버가 알아낼 수 있게됨
 
 * lombok의 @Slf4j 어노테이션: SpringBoot에 log객체를 이용가능하게 해줌. log객체를 통해 특정 error 및 debug 등의 로그를 로그 레벨로 출력 가능
   * 특정 단계의 로그 레벨을 지정하면 그 단계 이상의 레벨 정보들을 같이 출력함. (ex. 3레벨 지정 시, 3 4 5 레벨을 출력) 
   * 각 단계별 로그 레벨 순: log.trace, log.debug, log.info, log.warn, log.error, log.fatal
     1. trace : debug 보다 상세하게 기록할 경우 사용
     2. debug : 디버깅 목적으로 사용
     3. info : 주요 이벤트나 상태 등의 일반 정보를 출력할 목적으로 사용
     4. warn : 문제가 발생할 가능성 있는 상태 및 상황(비교적 작은 문제)에 관해 경고 정보를 출력
     5. error : 심각한 문제나 예외 상황(비교적 큰 문제)에 대한 오류 정보를 출력
     6. fatal : 프로그램 기능의 일부가 실패 및 오류가 발생하는 아주 심각한 문제에 관한 정보를 출력  
