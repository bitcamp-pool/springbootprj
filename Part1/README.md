# springbootprj
코드로 배우는 스프링 부트 웹 프로젝트
<br><br>

## Part 1 스프링 부트 도전하기

  - Intellij를 이용하는 경우 스프링 부트 프로젝트 설정하는 법
  - MariaDB 설치와 설정
  - Spring Data JPA를 이용해서 데이터베이스 처리 실습
  - 스프링 MVC와 Thymeleaf 소개
  
  <br><hr><br>

  ### 01 프로젝트를 위한 준비
  > 1.1 개발도구의 준비
  > *  [Intellij 다운로드](https://www.jetbrains.com/ko-kr/idea/download/)
  > *  [스프링 부트 Project 생성](https://start.spring.io/)
  >  - Name: Part1
  >  - Location: C:\DevPool\springbootprj\Part1
  >  - Language: Java
  >  - Type: Gradle
  >  - Group: com.bitcamp
  >  - Artfact: ex01
  >  - Package name: com.bitcamp.ex01
  >  - JDK: 11 Oracle OpenJDK version 11.0.15
  >  - Java: 11
  >  - Package: War
  >  - Spring Boot: 2.6.7
  >  - Dependencies: Spring Boot DevTools/Lombok/Spring Web
 
  > 1.2 Spring initializer를 이용한 프로젝트 생성
  > * Intellij Community 버전
  > * [https://start.spring.io](https://start.spring.io/)
  > * 필요한 항목을 선택한 후, [Generate]버튼 클릭, 파일 다운로드
  > * File->Settings->Plugins 'Lombok'검색 Install, Intellij 재시작
  > * Lombok 기능 활성화(Enable)설정

  > 1.3 스프링 프로젝트 실행해보기
  > * src/main/java/com.bitcamp.ex01/Part1Application.java 실행
  > * Tomcat8080포트 충돌 : src/main/resources/application.properties 수정
  > * 테스트 코드의 실행
  > * 컨트롤러 실습 : src/main/com.bitcamp.ex01.controller/SampleController.java

  > 1.4 스프링 부트를 단독으로 실행 가능한 파일로 만들기
  > * Tomcat WAS서버에 배포하여 실행
  > * 스프링 부트에서는 단독으로 실행 가능(웹app를 jar파일 형태로 제작 사용)
  > * Intellij 화면 오른쪽 탭 Gradle항목 Tasks>build>bootJar 실행
  > * 프로젝트 내에 build>libs/ex01-0.0.1-SNAPSHOT.jar파일 생성
  > * 터미널 환경 java -jar ex01-0.0.1-SNAPSHOT.jar 실행해보기

  <br><hr><br>

  ### 02 Maria 데이터베이스와 SpringDataJPA
  - Maria DB의 설치와 스키마/계정 생성
  - 스프링 부트 프로젝트의 데이터베이스 설정
  - Spring Data JPA를 이용한 CRUD와 페이징 처리
  - JpaRepository 인터페이스를 활용하는 다양한 방법
  
  <br>

  > 2.1 MariaDB의 설치와 데이터베이스 생성
  > * MySQL 동일기능 제공/완전한 오픈소스/라이센스 무료
  > * [MariaDB](https://mariadb.org/) 다운로드
  > * install:root 계정 패스워드 UTF8 세팅
  > * HeidiSQL 실행 root 계정 연결 설정
  > * 신규 데이터베이스 및 사용자 계정 설정
  > * HS 재실행 세션 추가

  > 2.2 Spring DataJPA를 이용하는 프로젝트의 생성
  > * Artifact: ex02
  > * Dependencies: SQL->Spring Data JPA 추가
  > * 데이터베이스를 위한 스프링 부트 설정 : Auto Configuration
  > * [Maven 저장소](https://mvnrepository.com/) MariaDB JDBC 드라이버 검색
  > * MariaDB Java Client » 3.0.4 Gradle 설정 복사
  > * 프로젝트 내에 build.gradle 파일의 dependencies항목에 추가
  > * 라이브러리 변경 반영을 위해 화면 오른쪽 상단 아이콘(Gradle) 클릭
  > * [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) 참조
  > * src/main/resources/ application.properties 파일에 DB접속 정보 추가

  > 2.3 Spring DataJPA의 소개
  > * ORM(Object Reational Mapping): 데이터베이스와 객체 지향 프로그래밍 언어 간의 호환되지 않는 데이터를 변환하는 프로그래밍 기법이다
  > * JPA(Java Persistence API): ORM의 자바 스펙
  > * Hibernate : 해당 스펙을 구현한 프레임워크(TopLink, Eclipselink...)

  ...........................[그림] 브라우저에서 DB까지.........................................

<br>
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<img src="https://1.bp.blogspot.com/-rETQcIDxSk8/XYRNiAc886I/AAAAAAAAA-I/EQv8YL_7BmAlHe29teIvZKsjO7PdAzGowCLcBGAsYHQ/s640/layers.png" width="550"><br><br>

..............................................................................................
  > 2.4 엔티티 클래스와 JpaRepository
  > * Entity클래스 : JPA를 통해서 관리하게 되는 객체(Object)
  > * Repository : Entity 객체들을 처리(스프링내부에서 자동생성하고 실행)
  > * 엔티티 클래스 작성 : com.example.ex02.entity.Memo 클래스 생성
  > * tbl_memo : 자동으로 데이터베이스 설정과 테이블을 확인(에러발생)
  > * File>Setting>Editor>Inspections 항목 JPA> 'Unresolved database reference in annotations' 체크 해제
  > * @Entity : 해당 클래스가 엔티티를 위한 클래스이며, 해당 클래스의 인스턴스들이 JPA로 관리되는 엔티티
  객체라는 것을 의미한다. 또한 옵션에 따라 자동으로 테이블을 생성할 수도 있다.
  > * @Table : 데이터베이스상에서 엔티티 클래스를 어떠한 테이블로 생성할 것인지에 대한 정보를 담기 위한
  어노테이션이다. 
  > * @Id : @Entity가 붙은 클래스는 Primary Key(PK)에 해당하는 특정 필드를 @Id로 지정해야만 한다. 사용자가
  직접 입력하는 값을 사용하는 경우가 아니면 자동으로 생성되는 번호를 사용하기 위해서 @GeneratedValue라는
  어노테이션을 활용한다.
  > * strategy = GeneratedType.IDENTITY : PK를 자동으로 생성하고자 할 때 사용(키 생성 전략) 사용하는 DB가
  키 생성을 결정 MySQL이나 MariaDB의 경우 auto increment방식을 이용
  > * @Column : 추가적인 필드(컬럼)가 필요한 경우 활용하는 어노테이션

  >  [application.properties 설정]
  > * spring.jpa.hibernate.ddl-auto=update : 자동으로 DDL(create, alter, drop)을 생성할 것인지 결정하는 설정
  update시 테이블이 없으면 create, 있으면 alter
  > * spring.jpa.properties.hibernate.format_sql=true : 실제 JPA의 구현체인 Hibernate가 동작하면서 발생하는
  SQL을 포맷팅해서 출력
  > * spring.jpa.show-sql=true : JPA 처리 시에 발생하는 SQL을 보여줄 것인지 설정  

  > [JpaRepository 인터페이스]
  > * JpaRepository : Spring Data JPA의 구현체인 Hibernate를 이용하기 위한 API
  > * CRUD 작업이나 페이징, 정렬 등의 처리를 인터페이스의 메서드 호출 형태로 처리   


  > 2.5 페이징/정렬 처리하기<br>
  > 2.6 쿼리 메서드(Query Methods)기능과 @Query<br>

  <br><hr><br>

  ### 03 스프링 MVC와 Thymeleaf
  > 3.1 Thymeleaf를 사용하는 프로젝트 생성<br>
  > 3.2 Thymeleaf의 기본 사용법<br>
  > 3.3 Thymeleaf의 기본 객체와 LocalDateTime<br>
  > 3.4 Thymeleaf의 레이아웃<br>
 






