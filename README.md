## 시리즈 컬렉션

유튜브 채널에는 개인이 보고싶거나, 또는 보고싶지 않은 시리즈들이 존재합니다. <br>
유튜브 시리즈들만 모아서 볼 수 있고, 개인이 원하는 시리즈들만 관리할 수 있는 프로젝트 입니다. 

### 목표
- Mvvm 패턴 적용을 통한 각 레이어간 의존성을 줄이고 유지보수성을 높이고자 합니다.
- 기술의 원리를 파악해 성능을 개선합니다.
- 각 기술의 장단점을 고려해 적합한 기술을 적용하고자 합니다.
- 디자인 패턴 적용을 통해서 확장에 유연한 코드를 만들고자 합니다.

### 기술 스택
Mvvm Pattern, Coroutine, Data-Binding, LiveData, Hilt, WorkManager, Room, Navigtaion, Paging3, Retrofit2

### 주요 이슈 
- 로딩 시간을 줄이기 위한 로컬 캐싱
- 네트워크 연결이 안 되는 오프라인 상황에서 지원
- 중첩 리사이클러의 뷰풀 공유를 통한 성능 개선 과정 <br>
https://codingheung.tistory.com/78
- 다수의 이미지 URL을 가져오기 코루틴 병렬 실행과 오류 핸들 
- NestedScrollView에 리사이클러뷰 구현시 뷰 재활용 문제 해결 <br>
https://codingheung.tistory.com/77
- 카테고리를 추가할때 확장성 문제 해결 <br>
https://codingheung.tistory.com/79

### 화면
|home|detail|inventory|
|----|---|---|
|<img src="https://github.com/f-lab-edu/series-collector/assets/89631493/2a0200d3-111a-4b2f-af05-ac6d5c372400" width="100%" height="50%"/>|<img src="https://github.com/f-lab-edu/series-collector/assets/89631493/6da51a76-432e-49b7-b28a-7ff349a7981f" width="100%" height="50%"/>|<img src="https://github.com/f-lab-edu/series-collector/assets/89631493/088d5a35-2756-434d-8235-fdd9698b038e" width="100%" height="50%"/>|

### 아키텍처
![Activity  Fragment](https://github.com/f-lab-edu/series-collector/assets/89631493/5b326730-ebe2-4276-abd9-5dd60b5bf02e)

- Local : Room db
- Remote : Firebase FireStore 

### 어플 기획 및 디자인
https://ovenapp.io/project/CbZ5G3hIG2UjVuFgwp9SkVRAbf1FJWgo#62N57
