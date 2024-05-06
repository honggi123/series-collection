# Series Collection

|<img src="https://github.com/honggi123/series-collection/assets/89631493/78d55852-67e1-4042-866d-3ed326df6763" height="20%"/>|<img width=“20”% src="https://github.com/honggi123/series-collection/assets/89631493/43d20e3a-03f5-40aa-9c12-730c57170ca7" width="20%" height="20%"/>|<img width=“20”% src="https://github.com/honggi123/series-collection/assets/89631493/83ced6b3-4a0d-4be9-b784-fb9d343af29c" width="20%" height="20%"/>
|----|----|----|

<br>

## 개요

#### ➀ 시리즈 컬렉션 이란?

- 유튜브 스트리머들은 자신의 채널에 시리즈 별로 동영상을 올리는 경우가 많습니다. 하지만 사용자 마다 선호하는 시리즈는 다릅니다. <br> 유튜브 시리즈 별로 분류되어 모아서 볼 수 있도록 하는 서비스 입니다.

#### ➁ 개발기간: 2023.04 ~ 2023.07

<br>

## 이슈 해결
- [로컬 캐싱을 통한 로딩 시간 단축](https://velog.io/@kkk7526/%EB%A1%9C%EC%BB%AC-%EC%BA%90%EC%8B%B1%EC%9D%84-%ED%86%B5%ED%95%9C-%EB%A1%9C%EB%94%A9-%EC%8B%9C%EA%B0%84-%EB%8B%A8%EC%B6%95)

- [로딩 작업 최소화를 위한 백그라운드 작업 적용](https://velog.io/@kkk7526/%EB%A1%9C%EB%94%A9-%EC%9E%91%EC%97%85-%EC%B5%9C%EC%86%8C%ED%99%94%EB%A5%BC-%EC%9C%84%ED%95%9C-%EB%B0%B1%EA%B7%B8%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%9E%91%EC%97%85-%EC%A0%81%EC%9A%A9)

- [다수의 이미지 Url을 가져오기 위한 코루틴 병렬 실행](https://velog.io/@kkk7526/%EB%8B%A4%EC%88%98%EC%9D%98-%EC%9D%B4%EB%AF%B8%EC%A7%80-Url%EC%9D%84-%EA%B0%80%EC%A0%B8%EC%98%A4%EA%B8%B0-%EC%9C%84%ED%95%9C-%EC%BD%94%EB%A3%A8%ED%8B%B4-%EB%B3%91%EB%A0%AC-%EC%8B%A4%ED%96%89-5sxukj7g)

- [다수의 썸네일 이미지 URL을 가져올 때 오류 핸들](https://velog.io/@kkk7526/%EB%8B%A4%EC%88%98%EC%9D%98-%EC%9D%B4%EB%AF%B8%EC%A7%80-Url%EC%9D%84-%EA%B0%80%EC%A0%B8%EC%98%A4%EA%B8%B0-%EC%9C%84%ED%95%9C-%EC%BD%94%EB%A3%A8%ED%8B%B4-%EB%B3%91%EB%A0%AC-%EC%8B%A4%ED%96%89/)

- [스크롤 버벅거림을 일으킬 수 있는 중첩 리사이클러뷰의 뷰풀 개선](https://velog.io/@kkk7526/%EC%8A%A4%ED%81%AC%EB%A1%A4-%EB%B2%84%EB%B2%85%EA%B1%B0%EB%A6%BC%EC%9D%84-%EC%9D%BC%EC%9C%BC%ED%82%AC-%EC%88%98-%EC%9E%88%EB%8A%94-%EC%A4%91%EC%B2%A9-%EB%A6%AC%EC%82%AC%EC%9D%B4%ED%81%B4%EB%9F%AC%EB%B7%B0%EC%9D%98-%EB%B7%B0%ED%92%80-%EA%B0%9C%EC%84%A0) 

<br>

## 사용 패키지 & Tools

* Minimum SDK level 21
* Kotlin based, Coroutines + Flow for asynchronous.
* Foundation
  - Appcompat
  - Android KTX

* JetPack
  - DataBinding
  - LiveData
  - WorkManager
  - Room
  - Navigtaion

* Thrid Party Library
  - Coroutine
  - Paging3
  - Retrofit2 
  - Glide
  - Hilt
    
* Remote : Firebase FireStore
  
<br>

## 화면별 기능 소개

#### ➀ 스플래시 화면

- Room db 캐싱을 통한 로딩 시간 최적화

- Lottie 애니메이션 적용 <details>
  <summary>  화면 동작 보기</summary>
  <img src="https://github.com/honggi123/series-collection/assets/89631493/6416b5a1-eb93-4f83-8568-14294d6b903d" width="230px" height="500px"/>
</details>

#### ➁ 홈 화면
- 중첩 리사이클러뷰 적용 <details>
   <summary>  화면 동작 보기</summary>
       <img src="https://github.com/honggi123/series-collection/assets/89631493/9ac3b535-b2fc-48b9-90d8-475383af3e2b" width="230px" height="500px"/>
  </details>

#### ➂ 시리즈 상세 화면
  - 유튜브 동영상 연결을 위한 암시적 인텐트 적용
    
  - Youtube API를 통해 회차 영상 페이징 <details>
     <summary>  화면 동작 보기</summary>
      <img src="https://github.com/honggi123/series-collection/assets/89631493/479130c2-17e4-476b-ab40-b0bd6e80e970" width="230px" height="500px"/>
    </details>

#### ➂ 검색 화면
 - 시리즈 데이터 키워드 별 검색
 
 - api 호출을 줄이기 위한 flow debouce를 통한 지연 <details>
     <summary>  화면 동작 보기</summary>
     <img src="https://github.com/honggi123/series-collection/assets/89631493/56652ec7-49a2-4a28-afdf-07ce757499d4" width="230px" height="500px"/>
   </details>

#### ➃ 북마크 화면
 - 시리즈 상세화면에서 북마크 추가 / 삭제 

 - 룸 db를 통해 시리즈 저장 및 조회 <details>
   <summary>  화면 동작 보기
   </summary>
     <img src="https://github.com/honggi123/series-collection/assets/89631493/3e6c074c-28fd-4e28-905e-85bc0bf190ee" width="230px" height="500px"/>
 </details>




