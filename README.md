# FC 프로젝트(2023-04-01 ~ 2023-05-06)
freecontact라는 주제의 사이트로써 개발자와 기업간 매칭 사이트


## 사이트 기능
> ### auth
1. 회원가입
    - 기업회원과 일반회원 분리
    - ajax를 통해 중복된 아이디 검열
    - 이메일 인증을 통해 인증된 회원만 로그인 가능
   
2. 로그인
    - 기업회원과 일반회원 분리

3. 마이페이지
    - 회원의 개인정보 변경
  
> ### epRecruit(구인)
1. 구직 게시글 CRUD
        - 기업이 원하는 기술이나 능력을 등록하고 기업정보를 등록
2. 이메일(이력시 받기)
        - SMTP를 구직 회원이 편리하게 이력서를 이메일로 전송 할 수 있는 서비스
3. summernote 라이브러리 사용
        - multipart를 이용하여 사진, 파일 등등 편리하고 좋은 ui를 확보
4. 페이징 처리 

> ### Admin
1. 1:1 관리 게시글 CRUD
2. 공지 사항 게시글 CRUD
3. 일반/기업회원 게시글 관리 CRUD
4. 채팅 문의 받기

---
## 나의 역할

> ### jobHunting(구직)
1. 구직 게시글 CRUD
    - 자신의 기술이나 분야 등 현재 상태와 정보를 등록
2. 이메일(제안서 받기)
    - SMTP를 기업회원이 보다 편리하게 이메일을 전송 할 수 있는 서비스
3. summernote 라이브러리 사용
    - multipart를 이용하여 사진, 파일 등등 편리하고 좋은 ui를 확보
4. 페이징 처리 



> ### chat
1. STOMP를 사용하여 양방향 소통이 가능하게 함.
    - stomp cdn를 이용하여 websocket연결
    - 회원의 고유id 값을 이용하여 방을 생성함(RestFul 방식의 chat채널 생성)
        - (ex chat/chatRoom/roomname/id)_number)
    - admin 로그인시 채팅방 존재, 일반회원은 채팅방 없이 admin에게 채팅 메세지 전송 가능

> ### SMTP
1. 구글 SMTP를 사용하여 이메일 기능을 사용
    - 회원가입 시 이메일 인증
        - 회원가입 한 사용자 정보를 키:벨류로 저장하여 이메일로 전송 -> 다른 사용자는 사용불가
        - 느린 처리속도를 async 비동기를 이용하요 빠른 화면 처리로 해결 
    - 구인/구직 이메일 전송
        - 느린 처리속도를 async 비동기를 이용하요 빠른 화면 처리로 해결 

   
