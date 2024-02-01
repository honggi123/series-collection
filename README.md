
## Series Collection

|<img src="https://github.com/honggi123/series-collection/assets/89631493/78d55852-67e1-4042-866d-3ed326df6763" height="20%"/>|<img width=“20”% src="https://github.com/honggi123/series-collection/assets/89631493/43d20e3a-03f5-40aa-9c12-730c57170ca7" width="20%" height="20%"/>|<img width=“20”% src="https://github.com/honggi123/series-collection/assets/89631493/83ced6b3-4a0d-4be9-b784-fb9d343af29c" width="20%" height="20%"/>
|----|----|----|

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

- Local : Room db
- Remote : Firebase FireStore


