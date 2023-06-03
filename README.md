
## Series Collection

유튜브 채널에는 개인이 보고싶거나, 또는 보고싶지 않은 시리즈들이 존재합니다. <br>
시리즈들만 모아서 볼 수 있고, 개인이 원하는 시리즈들만 관리할 수 있는 프로젝트 입니다. 

|<img src="https://github.com/honggi123/series-collection/assets/89631493/a27aa508-ebfd-43c3-abf7-ddc833321b66" height="20%"/>|<img width=“20”% src="https://github.com/honggi123/series-collection/assets/89631493/3556a33e-2331-47c3-b451-da92bc6e6a0a" width="20%" height="20%"/>|
|----|----|


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
  - Hilt

* Thrid Party Library
  - Coroutine
  - Paging3
  - Retrofit2 
  - Glide

## Main Issue
- 로딩 시간을 줄이기 위한 로컬 캐싱
- 네트워크 연결이 안 되는 오프라인 상황에서 지원
- 중첩 리사이클러의 뷰풀 공유를 통한 성능 개선 <br>
- 다수의 이미지 URL을 가져오기 코루틴 병렬 실행과 오류 핸들 
- NestedScrollView에 리사이클러뷰 구현시 뷰 재활용 문제 해결 <br>
- 카테고리를 추가하는 상황에서의 유지보수성 향상 <br>

## Architecture Overview

Series Collection은 기본적으로 MVVM architecture와 Repository pattern를 기반으로 합니다.

### Unidirection Data Flow

<img src = "https://github.com/honggi123/series-collection/assets/89631493/5494dda1-9d28-45b0-add8-f8a395aad9e0" width="70%" height="50%">

UDF에서 상태는 한 방향으로만 흐릅니다. 데이터 흐름을 수정하는 이벤트는 반대 방향으로 흐릅니다. <br>   
즉 애플리케이션 데이터 흐름은 데이터 레이어에서 ViewModel로 향합니다. <br> UI 상태 흐름은 ViewModel에서 UI 요소로 향하고 이벤트 흐름은 UI 요소에서 다시 ViewModel로 향합니다.
<br>
<br>

### Repository Pattern

<img src = "https://github.com/f-lab-edu/series-collector/assets/89631493/5b326730-ebe2-4276-abd9-5dd60b5bf02e" width="70%" height="50%">

레포지토리 패턴은 데이터 레이어를 앱의 다른 레이어들과 분리하는 디자인 패턴입니다. <br>
실제 앱에서 레포지토리는 네트워크에서 데이터를 가져올지 아니면 로컬 데이터베이스에 캐시된 결과를 사용할지 결정하는 로직을 구현합니다. <br>

- Local : Room db
- Remote : Firebase FireStore

레포지토리를 사용하면 뷰 모델과 같은 호출 코드에 영향을 주지 않고 구현 세부정보를 교체할 수 있습니다.<br>
이는 코드를 모듈식으로, 테스트 가능하게 만드는 데도 도움이 됩니다. 


## App Design
https://ovenapp.io/project/CbZ5G3hIG2UjVuFgwp9SkVRAbf1FJWgo#62N57
