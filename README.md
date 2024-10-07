# 🔖 매장 테이블 예약 프로젝트
  > 매장을 방문할 때 미리 예약을 진행하는 프로젝트
<br>
<br>

# 🛠️ 사용 기술
<table>
  <thead>
    <th>Name</th>
    <th>Version</th>
  </thead>
  <tbody>
    <tr>
      <td>Spring Boot</td>
      <td>3.3.4</td>
    </tr>
    <tr>
      <td>Gradle</td>
      <td>8.10.2</td>
    </tr>
    <tr>
      <td>Java</td>
      <td>17</td>
    </tr>
    <tr>
      <td>MySql</td>
      <td>9.0.1</td>
    </tr>
    <tr>
      <td>lombok</td>
      <td>1.18.34</td>
    </tr>  
    <tr>
      <td>Spring Data JPA</td>
      <td>3.3.4</td>
    </tr>  
    <tr>
      <td>Spring Security</td>
      <td>3.3.4</td>
    </tr>  
    <tr>
      <td>jjwt</td>
      <td>0.9.1</td>
    </tr>
  </tbody>
</table>

<br>
<br>

# 🗓️ 기능
#### 사용자
  - 사용자 등록(저장) : Http Request 요청으로 사용자 정보 및 역할(권한)을 받아 <br>
              사용자 중복체크하고 비밀번호 암호화해 DB에 저장 <br>
  - 사용자 로그인 : Http Request 요청으로 사용자이름과 비밀번호를 받아 해당 사용자가 DB 에 <br>
                존재하는지 확인 후 비밀번호가 같은지 체크한 후에 jwt 토큰을 발급해 응답 <br>
  - 사용자 수정 : Http Request 요청으로 사용자 정보를 수정할 객체를 받아 해당 사용자가 DB 에 <br>
                존재하는지 확인 후 사용자 정보 수정 <br>
  - 사용자 삭제 : Http Request 요청으로 사용자의 DB 상의 id 를 받아 해당 사용자가 DB 에 <br>
                존재하는지 확인 후 사용자 정보 삭제 <br>
    
#### 매장
  - 매장 정보 등록(저장) : Http Request 요청으로 매장 정보를 받아 <br>
                      해당 매장이 존재하지 않는지 확인 후 DB 에 저장 <br>
  - 전체 매장 목록 조회 : 전체 매장 목록을 가다나순, 평점순, 거리순으로 정렬해 조회 <br>
  - 특정 매장 정보 조회 : Http Request 요청으로 매장의 DB 상의 id 를 받아 <br>
                    특정 매장 정보가 존재하는지 확인 후 특정 매장 정보 조회 <br>
  - 특정 매장 정보 수정 : Http Request 요청으로 매장의 정보를 수정할 객체를 받아 <br>
                    특정 매장 정보가 존재하는지 확인 후 특정 매장 정보 수정 <br>
  - 특정 매장 정보 삭제 : Http Request 요청으로 매장의 DB 상의 id 를 받아 <br>
                    특정 매장 정보가 존재하는지 확인 후 특정 매장 정보 삭제 <br>

#### 예약
  - 예약 정보 등록(저장) : Http Request 요청으로 예약 정보를 받아 <br>
                      해당 예약의 유효성을 확인후 해당 예약이 존재하지 않는지 확인 후 DB 에 저장 <br>
  - 특정 매장의 예약 목록 조회 : 특정 매장의 DB 상의 id 를 받아 해당 매장의 <br>
                          예약 전체목록 또는 날짜별 예약목록을 조회 <br>
  - 특정 예약 정보 조회 : Http Request 요청으로 예약의 DB 상의 id 를 받아 <br>
                    특정 예약 정보가 존재하는지 확인 후 특정 예약 정보 조회 <br>
  - 특정 예약 정보 수정 : Http Request 요청으로 예약의 정보를 수정할 객체를 받아 <br>
                    특정 예약 정보가 존재하는지 확인 후 특정 예약 정보 수정 <br>
  - 특정 예약 정보 삭제 : Http Request 요청으로 예약의 DB 상의 id 를 받아 <br>
                    특정 예약 정보가 존재하는지 확인 후 특정 예약 정보 삭제 <br>
  - 예약 승인 : 매장의 점주가 예약 등록 요청을 승인 <br>
  - 예약 거절 : 매장의 점주가 예약 등록 요청을 거절 <br>
  - 도착 확인 : 예약 후 매장 방문시 도착확인 요청 <br>

#### 리뷰
  - 리뷰 정보 등록(저장) : Http Request 요청으로 리뷰 정보를 받아 <br>
                      해당 리뷰가 존재하지 않는지 확인 후 DB 에 저장 <br>
  - 특정 가게에 대한 리뷰 목록 조회 <br>
  - 특정 사용자가 작성한 리뷰 목록 조회 <br>
  - 특정 리뷰 정보 조회 : Http Request 요청으로 리뷰의 DB 상의 id 를 받아 <br>
                    특정 리뷰 정보가 존재하는지 확인 후 특정 리뷰 정보 조회 <br>
  - 특정 리뷰 정보 수정 : Http Request 요청으로 리뷰의 정보를 수정할 객체를 받아 <br>
                    특정 리뷰 정보가 존재하는지 확인 후 특정 리뷰 정보 수정 <br>
  - 특정 리뷰 정보 삭제 : Http Request 요청으로 리뷰의 DB 상의 id 를 받아 <br>
                    특정 리뷰 정보가 존재하는지 확인 후 특정 리뷰 정보 삭제 <br>
  
<br>
<br>

# API 스펙
<table>
  <thead>
    <th>분류</th>
    <th>기능</th>
    <th>URI</th>
    <th>Method</th>
    <th>Status Code</th>
  </thead>
  <tbody>
    <tr>
      <td rowspan="5">사용자</td>
      <td>사용자 등록</td>
      <td>/api/v1/members/register</td>
      <td>POST</td>
      <td>200</td>
    </tr>
    <tr>
      <td>사용자 로그인</td>
      <td>/api/v1/members/login</td>
      <td>POST</td>
      <td>200</td>
    </tr>
    <tr>
      <td>사용자 조회</td>
      <td>/api/v1/members/{memberId}</td>
      <td>GET</td>
      <td>200</td>
    </tr>
    <tr>
      <td>사용자 수정</td>
      <td>/api/v1/members/{memberId}</td>
      <td>PATCH</td>
      <td>200</td>
    </tr>
    <tr>
      <td>사용자 삭제</td>
      <td>/api/v1/members/{memberId}</td>
      <td>DELETE</td>
      <td>200</td>
    </tr>
    <tr>
      <td rowspan="5">매장</td>
      <td>매장 정보 등록</td>
      <td>/company</td>
      <td>POST</td>
      <td>200</td>
    </tr>
    <tr>
      <td>매장 목록 조회</td>
      <td>/api/v1/stores?sortType=""&userLat=0&userLng=0</td>
      <td>GET</td>
      <td>200</td>
    </tr>
    <tr>
      <td>특정 매장 조회</td>
      <td>/api/v1/stores/{storeId}</td>
      <td>GET</td>
      <td>200</td>
    </tr>
    <tr>
      <td>특정 매장 수정</td>
      <td>/api/v1/stores/{storeId}</td>
      <td>PATCH</td>
      <td>200</td>
    </tr>
    <tr>
      <td>특정 매장 삭제</td>
      <td>/api/v1/stores/{storeId}</td>
      <td>DELETE</td>
      <td>200</td>
    </tr>
    <tr>
      <td rowspan="8">예약</td>
      <td>예약 등록</td>
      <td>/api/v1/reservations</td>
      <td>POST</td>
      <td>200</td>
    </tr>
    <tr>
      <td>특정 매장 예약 목록 조회</td>
      <td>/api/v1/reservations/store/{storeId}?date="2024-10-07"</td>
      <td>GET</td>
      <td>200</td>
    </tr>
    <tr>
      <td>특정 매장 조회</td>
      <td>/api/v1/reservations/{reservationId}</td>
      <td>GET</td>
      <td>200</td>
    </tr>
    <tr>
      <td>특정 매장 수정</td>
      <td>/api/v1/reservations/{reservationId}</td>
      <td>PATCH</td>
      <td>200</td>
    </tr>
    <tr>
      <td>특정 매장 삭제</td>
      <td>/api/v1/reservations/{reservationId}</td>
      <td>DELETE</td>
      <td>200</td>
    </tr>
    <tr>
      <td>예약 승인</td>
      <td>/api/v1/reservations/{reservationId}/confirm</td>
      <td>POST</td>
      <td>200</td>
    </tr>
    <tr>
      <td>예약 거절</td>
      <td>/api/v1/reservations/{reservationId}/reject</td>
      <td>POST</td>
      <td>200</td>
    </tr>
    <tr>
      <td>도착 확인</td>
      <td>/api/v1/reservations/{reservationId}/visit/{memberId}</td>
      <td>POST</td>
      <td>200</td>
    </tr>
    <tr>
      <td rowspan="6">리뷰</td>
      <td>리뷰 등록</td>
      <td>/api/v1/reviews</td>
      <td>POST</td>
      <td>200</td>
    </tr>
    <tr>
      <td>특정 가게 리뷰 목록 조회</td>
      <td>/api/v1/reviews/store/{storeId}</td>
      <td>GET</td>
      <td>200</td>
    </tr>
    <tr>
      <td>특정 사용자가 작성한 리뷰 목록 조회</td>
      <td>/api/v1/reviews/member/{memberId}</td>
      <td>GET</td>
      <td>200</td>
    </tr>
    <tr>
      <td>특정 리뷰 조회</td>
      <td>/api/v1/reviews/{reviewId}</td>
      <td>GET</td>
      <td>200</td>
    </tr>
    <tr>
      <td>특정 리뷰 수정</td>
      <td>/api/v1/reviews/{reviewId}</td>
      <td>PATCH</td>
      <td>200</td>
    </tr>
    <tr>
      <td>특정 리뷰 삭제</td>
      <td>/api/v1/reviews/{reviewId}</td>
      <td>DELETE</td>
      <td>200</td>
    </tr>
  </tbody>
</table>

<br>
<br>
