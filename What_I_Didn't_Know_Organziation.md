# 점프 투 스프링부트3 모르는 점 및 용어정리

## 위키독스 링크: https://wikidocs.net/160023


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
 
  * CasecadeType.REMOVE : 부모 삭제 시 자식도 삭제

  * CasecadeType.ALL : 부모 변경이 자식에게 모두 전파
 
  * orphanRemoval=true : 부모가 자식을 리스트에서 제거(자식과의 관계를 제거)하면 자식이 삭제됨
 

* repository : 개발자가 app 소스 코드에 대한 변경을 수행/관리하는데 쓰이는 중앙화 디지털 저장소

* @Autowired : Spring이 관리하는 Bean을 찾아 자동으로 주입해주는 역할.

* 순환 참조 문제 : A클래스가 B클래스의 Bean을 주입받고, B클래스가 A클래스의 Bean을 주입받는 문제

* Optional<T> :  null 체크를 보다 안전하고 명확하게 처리하기 위한 제네릭. 값이 존재할수도, 안할수도 있다는 사실을 암시함. JPA와 함께 자주 쓰임.

  * indById() 같은 PK 기반 조회는 기본적으로 Optional<T>을 반환.

* this.Repository.save(q) 에서 save(q)는 q에 담긴 PK값을 통해 레포지토리에서 검색하여 일치하는 필드가 존재하면, 해당 필드 업데이트. 일치하는게 없다면, 해당 필드를 새로 업로드함.

* Transaction : Db관리 시스템에서 상호작용의 단위. 더 이상 쪼개질 수 없는 최소의 연산.

* Lazy Loading (지연 로딩) : 객체를 처음 조회할 때 연관된 엔티티를 바로 가져오지 않고, 나중에 필요할 때 가져오는 방식.

* (테스트 코드에서) @Transactional을 쓰지 않았을때, A 엔티티를 조회(A엔티티에 대한 트랜잭션 시작) 후 연관된 B 엔티티를 뒤늦게 조회하려 할때 A엔티티에 대한 트랜잭션이 종료되면서 그대로 종료됨. 때문에 @Transactional을 이용하여 A엔티티 트랜잭션이 계속 유지될 수 있도록 함.

* (Spring에서) 도메인 : SW로 해결하고자하는 문제 영역

* 템플릿 엔진(ex. Thymeleaf) : html 문법을 유지하면서 템플릿 속성(자바 코드)을 주입하여 동적 웹app 구현.
  * jsp와의 차이점 :
    * jsp - 레거시 도구, 가독성이 낮고, 브라우저에서 열기 어려움. html+자바 코드.
    * 템플릿 엔진 - 최신 스프링, 스프링 부트와 잘 맞음. 가독성이 높고, 브라우저에서 열기 쉬움. html+템플릿 속성.
  * 템플릿 엔진 사용 시 @ResponseBody가 필요없는 이유 : 해당 어노테이션은 리턴값을 http 응답 body로 보냄. 하지만 템플릿 엔진을 사용했을때 리턴값(String)은 템플릿 파일의 이름을 의미함. HTML을 템플릿으로부터 만들어 응답을 보냄.


* html에서 <th> = table head, <th: . . . > = thymeleaf의 속성


* SpringBoot에서 String 타입 메서드의 리턴타입이 어떤 응답(액션)을 취하는지 어떻게 정해질까?
  * SpringBoot의 ViewResolver라는 컴포넌트를 통해 Spring 리턴값을 보고 redirect 및 forward 등을 수행


* DTO 클래스와 @Service 클래스 차이
  * DTO는 엔티티 클래스와 직접적으로 닿아있고 해당 엔티티의 DB를 Getter, Setter 를 통해 관리 및 불러오기. @Service는 직접적인 서비스 로직(CRUD)를 수행.
  * DTO에서 불러온 데이터를 통해 @Service 클래스에서 액션을 수행함.


* Thymeleaf 에서의 @{ . . . } : URL을 동적으로 작성

* | . . . | : 문자열 안에 ${ } 표현식을 동적으로 사용케 함 (선택)
  *${ . . . } : 변수에 접근하는 표현식


* HttpStatus.NOT_FOUND : 404 에러코드를 담음


* SerialVersion / SerialVersionUID : 해당 클래스의 버전을 명시. java.io.Serialization 을 import 하여 사용. 해당 클래스 직렬화 과정에서 serialversionUID의 버전이 포함되고, 역직렬화 과정에서 동일한 버전의 클래스를 불러옴.


* 직렬화 (Serialization) : 복잡한 데이터 구조의 클래스의 객체를 바이트 형태로 변환하여 네트워크 전송, 파일 저장에 사용되는 기술. (역직렬화는 반대)
  * 자바 컴파일과 다른점: 
    * 컴파일은 .java 코드를 바이트 코드 파일(.class)로 변환하여 JVM이 실행할 수 있도록 함. 
    * 직렬화는 메모리에 존재하는 객체를 바이트 스트림(네트워크, 파일)으로 변환하여 저장.


* <form th:action=" ">태그 : form 태그가 어디로 데이터를 받을지 지정하는 주소(url)
  * <form method=" "> 태그: 해당 form태그가 get방식일지, post방식일지 결정.
  * 매핑 어노테이션 을 통해 해당 action의 값(url)과 동일한 url을 제시할 경우, 두 메서드끼리 매핑되어 데이터를 클라이언트가 받아 쓸 수 있게됨.


* Model 객체는 언제 쓰이는가? : Spring에서 컨트롤러 메서드가 템플릿(HTML)에 데이터를 넘겨줘야할 때 이용


* Bootstrap : 웹사이트를 쉽게 만들 수 있게 도와주는 CSS, JS 프레임워크. class를 태그마다 지정만 하면됨.
   * docs : https://getbootstrap.kr/docs/5.3/getting-started/introduction/


* <th:each=". . . , loop : ${. . .}"> 에서의 loop: 현재 반복 상태에 대한 횟수 및 순서를 보여주는 Thymeleaf 템플릿 기능


* 표준 HTML 작성 방식을 따르지 않으면 브라우저는 Quirks Mode를 이용해 해석함.
  * 하지만, Quirks Mode는 브라우저마다 다르게 작동/해석하고, JS 코드가 꼬일 수 있으며, 접근성이 떨어질 수 있어 표준 html 작성은 필수임.

