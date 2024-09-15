# ABOUT ROMANCE (공유일기)
## 1. 프로젝트 개요
스쿠톤 행사의 개발테마인 “성공회대학교", "여름", "낭만” 키워드 중 "낭만" 이라는 키워드에 팀원들이 가장 큰 흥미를 느꼈고 낭만은 사람들이 함께 하는 시간에서 오는 것이 아닌가 생각해서 낭만을 공유하는 공유일기를 기획하게 되었습니다. 공유일기는 교환일기처럼 커플 혹은 단짝친구 사이의 추억을 기록하는 것 뿐만 아니라 여러 친구들과 함께 낭만을 나눌 수 있는 서비스 입니다.

## 2. 기술 스택
### Environment
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
### Development
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">

## 3. 프로젝트 구조
```
📦example
 ┗ 📂sharediary
 ┃ ┣ 📂comment
 ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┗ 📜CommentController.java
 ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┗ 📜Comment.java
 ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┣ 📜CommentRequestDto.java
 ┃ ┃ ┃ ┗ 📜CommentResponseDto.java
 ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┗ 📜CommentRepository.java
 ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┗ 📜CommentService.java
 ┃ ┣ 📂diary
 ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┗ 📜DiaryController.java
 ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┗ 📜Diary.java
 ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┣ 📜DiaryDateRequestDto.java
 ┃ ┃ ┃ ┣ 📜DiaryRequestDto.java
 ┃ ┃ ┃ ┣ 📜DiaryResponseDto.java
 ┃ ┃ ┃ ┗ 📜PagedResponse.java
 ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┗ 📜DiaryRepository.java
 ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┗ 📜DiaryService.java
 ┃ ┣ 📂friend
 ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┗ 📜FriendController.java
 ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┣ 📜Friend.java
 ┃ ┃ ┃ ┗ 📜FriendStatus.java
 ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┣ 📂reqeust
 ┃ ┃ ┃ ┃ ┗ 📜FriendRequestDto.java
 ┃ ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┃ ┗ 📜FriendResponseDto.java
 ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┗ 📜FriendRepository.java
 ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┗ 📜FriendService.java
 ┃ ┣ 📂global
 ┃ ┃ ┗ 📂config
 ┃ ┃ ┃ ┗ 📂swaggerConfig
 ┃ ┃ ┃ ┃ ┗ 📜SwaggerConfig.java
 ┃ ┣ 📂heart
 ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┗ 📜HeartController.java
 ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┗ 📜Heart.java
 ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┣ 📂request
 ┃ ┃ ┃ ┃ ┗ 📜HeartRequestDto.java
 ┃ ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┃ ┗ 📜HeartResponseDto.java
 ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┗ 📜HeartRepository.java
 ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┗ 📜HeartService.java
 ┃ ┣ 📂member
 ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┗ 📜WebMvcConfiguration.java
 ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┗ 📜MemberController.java
 ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┗ 📜Member.java
 ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┣ 📂request
 ┃ ┃ ┃ ┃ ┣ 📜LoginMemberResponseDto.java
 ┃ ┃ ┃ ┃ ┣ 📜MemberRequestDto.java
 ┃ ┃ ┃ ┃ ┗ 📜TokenRequestDto.java
 ┃ ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┃ ┣ 📜MemberResponseDto.java
 ┃ ┃ ┃ ┃ ┗ 📜TokenResponseDto.java
 ┃ ┃ ┣ 📂infrastructure
 ┃ ┃ ┃ ┣ 📜AuthorizationHandlerInterceptor.java
 ┃ ┃ ┃ ┣ 📜LoginMemberArgumentResolver.java
 ┃ ┃ ┃ ┗ 📜TokenProvider.java
 ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┗ 📜MemberRepository.java
 ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┣ 📜CookieService.java
 ┃ ┃ ┃ ┗ 📜MemberService.java
 ┃ ┣ 📂time
 ┃ ┃ ┗ 📜BaseTimeEntity.java
 ┃ ┗ 📜ShareDiaryApplication.java
```

 ## 4. 기능 구현
 ![image](https://github.com/user-attachments/assets/39fcec6a-947b-4a09-8564-065cd43feef7)
![image](https://github.com/user-attachments/assets/2ac22f60-178d-43bb-a262-be42f812a2e8)
![image](https://github.com/user-attachments/assets/a117b754-3b90-4a63-b5e0-5ee60755fd38)

