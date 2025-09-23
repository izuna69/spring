# 계획서

- MVC패턴에 대해
- 컨트롤러에서의 매핑에 대해
- Views에 역할
- 배치파일이 있는 이유
- 데이터 베이스의 연결
- localhost:\*\*\*\*/bbses 수정해보기

## 1. MVC 패턴에 대해

- **Model** : 데이터, 로직 (예: 게시글 목록, DB 연결 코드)
- **View** : 사용자 화면 (JSP, HTML)
- **Controller** : 사용자의 요청을 받고, Model과 View를 연결해줌

## 2. 컨트롤러에서의 매핑

- `@Controller` → 컨트롤러 클래스임을 표시
- `@RequestMapping("/hello")` → 사용자가 `/hello` 주소로 요청하면 이 메서드 실행
-

## 3. Views의 역할

- 사용자에게 보여줄 **화면(View)** 파일들을 모아두는 공간
- Spring MVC 기본 구조: `/WEB-INF/views/`
- 예:
  - `bbs/list.jsp`
  - `bbs/showhelloworld.jsp`

## 4. 배포패키지 .war에 대하여

- 현제 프로젝트를 mvn clean install 하는 이유와 배포

## 5. 데이터 베이스 연결

- 데이터베이스가 유저네임,비번,쿼리 연결

## 6. /bbses 빌드하여 배포

- 1~5 활용

### 각 브랜치

- ...
