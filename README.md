
## Series Collection

유튜브 채널에는 개인이 보고싶거나, 또는 보고싶지 않은 시리즈들이 존재합니다. <br>
시리즈들만 모아서 볼 수 있고, 개인이 원하는 시리즈들만 관리할 수 있는 프로젝트 입니다. 

|<img src="https://github.com/honggi123/series-collection/assets/89631493/78d55852-67e1-4042-866d-3ed326df6763" height="20%"/>|<img width=“20”% src="https://github.com/honggi123/series-collection/assets/89631493/43d20e3a-03f5-40aa-9c12-730c57170ca7" width="20%" height="20%"/>|<img width=“20”% src="https://github.com/honggi123/series-collection/assets/89631493/83ced6b3-4a0d-4be9-b784-fb9d343af29c" width="20%" height="20%"/>
|----|----|----|


## Goal
- Mvvm 패턴 적용을 통한 각 레이어간 의존성을 줄이고 유지보수성을 높이고자 합니다.
- 기술의 원리를 파악해 성능을 개선합니다.
- 각 기술의 장단점을 고려해 적합한 기술을 적용하고자 합니다.
- 디자인 패턴 적용을 통해서 확장에 유연한 코드를 만들고자 합니다.

## Tech Stack

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

## Main Issue
- 로딩 시간을 줄이기 위한 로컬 캐싱
- 네트워크 연결이 안 되는 오프라인 상황에서 지원
- 중첩 리사이클러의 뷰풀 공유를 통한 성능 개선 <br>
- 다수의 이미지 URL을 가져오기 코루틴 병렬 실행과 오류 핸들 
- NestedScrollView에 리사이클러뷰 구현시 뷰 재활용 문제 해결 <br>
- 카테고리를 추가하는 상황에서의 유지보수성 향상 <br>

## Architecture Overview

Series Collection은 기본적으로 MVVM architecture와 Repository pattern를 기반으로 합니다.

### Repository Pattern

<img src = "https://github.com/f-lab-edu/series-collector/assets/89631493/5b326730-ebe2-4276-abd9-5dd60b5bf02e" width="70%" height="50%">

- Local : Room db
- Remote : Firebase FireStore

레포지토리를 사용하면 뷰 모델과 같은 호출 코드에 영향을 주지 않고 구현 세부정보를 교체할 수 있습니다.<br>
이는 코드를 모듈식으로, 테스트 가능하게 만드는 데도 도움이 됩니다. 

