# 원하는 일을 하며 삶을 사랑하자! 대화 및 피드백 통계 서비스 134talk

- [134.works](https://134.works/) 에서 이용하실 수 있어요.

---

## 🍻 Intro

<p align="center">
  <img width="640" alt="image" src="https://github.com/134talk/backend_MSA/assets/107406265/2e53f26d-31d2-41e3-b4f1-5ce375d71c14">
</p>
<p align="center">
  <img src="https://img.shields.io/badge/react-v18.2.0-9cf?logo=react" alt="react" />
  <img src="https://img.shields.io/badge/spring_boot-v2.7.12-green?logo=springboot"  alt="spring-boot" />
  <img src="https://img.shields.io/badge/typescript-v4.9.5-blue?logo=typescript" alt="typescript"/>
  <img src="https://img.shields.io/badge/mysql-v8.0.32-blue?logo=mysql" alt="mysql"/>
</p>

100가지 인생의 주요한 질문들을 듣고, </br>
너와 나를 꺼내보는 낯선 대화를 나누는 **134talk** 대화 플랫폼입니다.

인생 질문에 답하면서 담고만 있었던 고민들을 정리하고 싶을 땐?  </br> 상대의 이야기로부터 삶의 위안과 영감을 얻고 싶을 땐?

바로, **134talk**
</br>
</br>

## 🚀 Demo
|                   소셜 로그인                   |                   대화방 생성                    |                         대화방 참여                          |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="https://github.com/134talk/backend_MSA/assets/107406265/0d66e01b-61f7-424a-abe8-d4c3b38c67f2.gif" alt="소셜_로그인" width=80%> | <img src="https://github.com/134talk/backend_MSA/assets/107406265/54580e4f-6445-4205-adc3-2495760eb9d8.gif" alt="대화방_생성" width=80%> | <img src="https://.gif" alt="일괄_평가" width=80%> |

|                   자기소개 마무리                   |                   키워드 선택                    |                         질문 순서 선택                          |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="" alt="유저_맞춤_술_추천" width=80%> | <img src="" alt="베스트_추천" width=80%> | <img src="" alt="일괄_평가" width=80%> |

|                   대화 중 이모티콘 보내기                   |                   대화 마감 알림                    |                         피드백 필수형 등록                          |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="" alt="유저_맞춤_술_추천" width=80%> | <img src="" alt="베스트_추천" width=80%> | <img src="h460-0950-4915-8cc0-a695057a822c.gif" alt="일괄_평가" width=80%> |

|                   피드백 선택형 등록                   |                   참가자 성향 리포트 상세조회                    |                         대화 기록 리포트 상세 조회                          |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="https://user-images.githubusercontent.com/=/=" alt="유저_맞춤_술_추천" width=80%> | <img src="https" alt="베스트_추천" width=80%> | <img src="https://user-images.githubusercontent" alt="일괄_평가" width=80%> |

|                   대화 후 참가자 상태 피드백 리포트                   |                  침원별 종합 리포트                    |                         마이 페이지                          |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="3.gif" alt="유저_맞춤_술_추천" width=80%> | <img src="httpf" alt="베스트_추천" width=80%> | <img src="https://user-images.githubuserconten" alt="일괄_평가" width=80%> |

## ⚡️ Skills



### Back-end

<p>
  <img src="https://user-images.githubusercontent.com/52682603/138834253-9bcd8b12-241f-41b2-85c4-d723a16bdb58.png" alt="spring_boot" width=12%>
  <img src="https://user-images.githubusercontent.com/52682603/138834267-c86e4b93-d826-4fd4-bcc8-1294f615a82d.png" alt="hybernate" width=12%>
  <img src="https://user-images.githubusercontent.com/52682603/138834280-73acd37b-97ef-4136-b58e-6138eb4fcc46.png" alt="query_dsl" width=12%>
  <img src="https://avatars.githubusercontent.com/u/1529926?s=200&v=4" alt="query_dsl" width=12%>
  <img src="https://avatars.githubusercontent.com/u/7815877?s=200&v=4" alt="query_dsl" width=12%>
</p>

- **Springboot** 로 자동화, 경량화된 웹 애플리케이션을 제작했어요.
- **Spring Data JPA(Hibernate)** 로 객체 지향 데이터 로직을 작성했어요.
- **QueryDSL** 로 컴파일 시점에 SQL 오류를 감지하고 가독성 높은 코드를 작성했어요.
- **Redis**를 구현한 **ElastiCache** 로 Refresh Token 같이 수명을 관리해야 하는 데이터를 처리하고 DB 대비 더 빠른 RTT로 성능을 개선했어요.
- NoSQL 기반의 **DynamoDB** 로 복잡하고 다양한 구조의 통계 데이터를 처리했어요.
- **Spring Cloud Gateway**, **Netflix Eureka**로 MSA 환경에서 서비스 간의 호출 , 인증 절차를 관리했어요.

---

## 🏰 Architecture
<img src="https://github.com/134talk/backend_MSA/assets/67637716/0459f0c3-932b-4832-bb86-6e9dcd73c6bb" alt="image" width=70%>  

#### Config
<p>
  <img src="https://github.com/134talk/backend_MSA/assets/67637716/e6405bae-abe3-4795-8496-8398602082f9" alt="image" width=15%>
  <img src="https://github.com/134talk/backend_MSA/assets/67637716/d63bb187-c160-4462-bdcc-95d30fb1fc86" alt="image" width=15%>
</p>

- **Spring Cloud Config** 를 사용해 중앙 설정 저장소로 관리했어요.
- **Github** private config repository로 사용했어요.

#### Server
<p>
  <img src="https://github.com/134talk/backend_MSA/assets/67637716/1d2ec605-a6a1-431b-9d28-750aef657f6c" alt="image" width=15%>
  <img src="https://github.com/134talk/backend_MSA/assets/67637716/83f9fd03-9603-450a-abd4-a56e25979588" alt="image" width=15%>
  <img src="https://github.com/134talk/backend_MSA/assets/67637716/334d54e8-725c-4531-b801-1abcc5334da2" alt="image" width=15%>
</p>

- **AWS Route53** 을 사용해 Domain Name Server를 구축했어요.
- **AWS S3** 를 사용해 프로필 사진 저장소로 사용했어요.
- **AWS EC2** 를 사용해 Micro Service Server를 구축했어요.

#### DB

<p>
  <img src="https://github.com/134talk/backend_MSA/assets/67637716/396448d6-784b-429e-9a99-0ac84939cf4f" alt="mysql" width=15%>
  <img src="https://github.com/134talk/backend_MSA/assets/67637716/8978f2a9-f93d-47a9-a769-439fa376d7f2" alt="dynamoDB" width=15%>
  <img src="https://github.com/134talk/backend_MSA/assets/67637716/7c1bb43e-efaf-4073-ac08-58025db84ecf" alt="elastiCache" width=15%>
</p>

- **AWS RDS(Mysql)** 를 Chat-Service와 User-Service에 사용했어요.
- **AWS DynamoDB** 를 통계 서버인 Statistics-Service에 사용했어요.
- **AWS ElastiCache** 를 In-Memory-Database로 활용하여 성능 최적화를 하였어요.

#### Call

<p>
  <img src="https://github.com/134talk/backend_MSA/assets/67637716/d6323813-db4e-4160-a5c6-0a5641e3edee" alt="kafka" width=15%>
  <img src="https://github.com/134talk/backend_MSA/assets/67637716/1b2a4b56-668a-45dc-88cf-7f23157f11dc" alt="openFeign" width=15%>
</p>

- **Kafka** Message Queue를 사용해 Non-Blocking으로 이벤트를 안전하게 전달했어요.
- **Open Feign**을 사용해 Remote REST API CALL을 했어요.


#### CI/CD

<p>
  <img src="https://user-images.githubusercontent.com/52682603/138834229-e8a9dcb0-bdb8-4aec-9a3e-be1f9ff44149.png" alt="github_actions" width=15%>
</p>

- **Github Actions** 로 지속적 배포를 진행해요. 


## 🎫 ERD
#### User Service
AWS RDS(MySql)  
<p>
  <img src="https://github.com/134talk/backend_MSA/assets/67637716/9bff1ad5-32df-41c3-8a34-95bbcf0e068a" alt="github_actions" width=70%>
</p>

#### Chat Service
AWS RDS(MySql)  
<p>
  <img src="https://github.com/134talk/backend_MSA/assets/67637716/f73b717b-f116-4568-9d2b-fd0f80a9a041" alt="github_actions" width=70%>
</p>

#### Statistics Service
AWS DynamoDB  
<p>
  <img src="https://github.com/134talk/backend_MSA/assets/67637716/b0585e86-96dc-406e-b255-4005aa8a127f" alt="github_actions" width=70%>
</p>




## 🌈 Members

|            [이담](https://github.com/damiiya)             |            [해솔](https://github.com/sol-pine)             |             [민석홍](https://github.com/shmin7777)             |             [김아리](https://github.com/lielocks)             |              [김용현](https://github.com/facewise)               |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="https://avatars.githubusercontent.com/u/101298873?v=4" width=200px alt="_"/> | <img src="https://avatars.githubusercontent.com/u/105091138?v=4" width=200px alt="_"/> | <img src="https://avatars.githubusercontent.com/u/67637716?v=4" width=200px alt="_"/> | <img src="https://avatars.githubusercontent.com/u/107406265?v=4" width=200px alt="_"> | <img src="https://avatars.githubusercontent.com/u/62998666?v=4" width=200px alt="_"> |
|                         프론트엔드                         |                         프론트엔드                         |                           백엔드                           |                           백엔드                           |                           백엔드                           |
