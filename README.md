
## Series Collection

|<img src="https://github.com/honggi123/series-collection/assets/89631493/78d55852-67e1-4042-866d-3ed326df6763" height="20%"/>|<img width=“20”% src="https://github.com/honggi123/series-collection/assets/89631493/43d20e3a-03f5-40aa-9c12-730c57170ca7" width="20%" height="20%"/>|<img width=“20”% src="https://github.com/honggi123/series-collection/assets/89631493/83ced6b3-4a0d-4be9-b784-fb9d343af29c" width="20%" height="20%"/>
|----|----|----|

<br><br>
## 목차
### 1. 개요
### 2. 프로젝트 주요 기능
### 3. 화면별 기능 소개
### 4. 이슈 해결

<br><br>
## 개요

#### ➀ 시리즈 컬렉션 이란?

- 유튜브 스트리머들은 자신의 채널에 시리즈 별로 동영상을 올리는 경우가 많습니다. 하지만 사용자 마다 선호하는 시리즈는 다릅니다. <br> 유튜브 시리즈 별로 분류되어 모아서 볼 수 있도록 하는 서비스 입니다.

#### ➁ 개발기간: 2023.04 ~ 2023.07

#### ➂ 사용 패키지 & Tools

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

<br><br>
## 프로젝트 주요 기능
🚧

<br><br>
## 화면별 기능 소개
🚧
#### ➀ 스플래시 화면

<details>
  <summary> 화면 동작 보기</summary>

  <img src="https://github.com/honggi123/series-collection/assets/89631493/6416b5a1-eb93-4f83-8568-14294d6b903d" width="250px" height="500px"/>
</details>

#### ➁ 홈 화면

<details>
  <summary> 화면 동작 보기</summary>
</details>

#### ➂ 상세 화면

<details>
  <summary> 화면 동작 보기</summary>
</details>

#### ➃ 북마크 화면

<details>
  <summary> 화면 동작 보기</summary>
  
  <img src="https://github.com/honggi123/series-collection/assets/89631493/3e6c074c-28fd-4e28-905e-85bc0bf190ee" width="250px" height="500px"/>
</details>

<br><br>
## 이슈 해결
- 로컬 캐싱을 통한 로딩 시간 단축  
- 로딩 작업 최소화를 위한 백그라운드 작업 적용
- 다수의 이미지 Url을 가져오기 위한 코루틴 병렬 실행
- 다수의 썸네일 이미지 URL을 가져올 때 오류 핸들
- 스크롤 버벅거림을 일으킬 수 있는 중첩 리사이클러뷰의 뷰풀 개선 




