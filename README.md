# 과제 프로젝트 2020/05/23

- androidx 
- databinding-MVVM
- Redux base architecture
- reactiveX 2.x
- Koin (DI)

Redux 구조를 안드로이드에 적용 하여 MVVM 을 기반으로 개발 하였습니다. 

## 1. Project modules 

이 프로젝트는 아래의 특징으로 인하여 모듈을 분리 하였습니다.    

- 테스트 
  - 특정 모듈에서 특정 유닛 테스트를 실행할 때 해당 모듈의 테스트 코드만 실행하기 때문에 단위 테스트 오버헤드가 줄어듭니다.
- 디펜던시 관리
  - app 모듈에서는 뷰에 대한 처리, model 모듈 에서는 비즈니스 코드와 ViewModel등 과 app 모듈에 대한 디펜던시를 인터페이스화 하여 각 컴포넌트간의 의존을 최소화 합니다.
  
후술할 각 모듈간 디펜던시는 아래와 같습니다. 

> common -> model -> app

### 1.1 `app` module

안드로이드에 대한 디펜던시, 즉 안드로이드 컴포넌트인 Activity, Fragment, View 등과 같은 의존을 갖는 클래스들이 존재 합니다. 그리고 model 모듈 에서 안드로이드에 대한 의존이 필요한 경우를 정의한 인터페이스의 구현 클래스들이 존재 합니다. 
그 외 databinding 을 위한 binding adapter 등의 함수들, view 들에 대한 확장함수, `Koin` 의 모듈부 가 존재 합니다. 

### 1.2 `model` module

비즈니스 코드의 처리를 위한 각 도메인의 Action, State, Reducer, ActionProcessor 등의 구현체와 ViewModel 이 있습니다. model 에서 뷰에 대한 처리를 요청 할 경우(ex, @string 리소스 에 대한 요청, Fragment replace 등) model 모듈 내 에 helper interface 를 두고 이 interface 의 구현체를 `app`모듈 에서 구현합니다. 

### 1.3 `common` module
 
전역 상수, kotlin 혹은 java 에 대한 util class 혹은 확장 함수를 갖습니다. 
 
## 2. Redux-MVVM

[Redux](https://redux.js.org/) 는 자바스크립트 에서 사용 되는 글로벌 상태 관리 도구로서 `단방향 데이터 흐름 (uni-directional flow)`의 특징을 갖고 있습니다. 

알려진 안드로이드 아키텍쳐 중 MVI  Redux 에 영향을 받아 만들어진 UDA(uni-directional architecture) 입니다. 

### 2.1 Redux

이 프로젝트에서 구현한 Redux 구조에서의 데이터 흐름은 아래와 같습니다. 

> User Input -> [Action] -> Array[Middleware] -> Array[Reducer] -> [State] -> View update

위 를 더 자세히 표현하여 예제를 들면 아래와 같습니다. 

1. User input 등으로 인한 Action, 처리 중 dispatch 되었던 Action(2.3 참고)   
2. Array<Middleware> 이터레이셔닝.    
2.2 Middleware 중 ActionProcessorMiddleware 를 통해 Action 을 핸들링 (loal, remote repository 를 통해 비동기 작업을 수행 하는 비즈니스)    
2.3 ActionProcessorMiddleware 를 통해 핸들링 되고 난 후의 Action 을 다음 middleware 에서 처리. 이 때 ActionProcessor 에서는 Action 을 핸들링 하고 난 뒤 새로운 Action 을 dispatch 할 수 있음.   
3. Array<Middleware> 을 모두 이터레이셔닝 하고 난 뒤의 Result Action 을 Array<Reducer> 이터레이셔닝.
4. Array<Reducer> 을 모두 이터레이셔닝 하고 난 뒤의 State 를 AppStore 에 저장 하고 이를 Observable 소스로 발행.
5. 각 Fragment 에서 State type 에 따라 자신의 Type 이 맞을 경우 이 소스를 받아 ViewModel 의 `render()` 함수 에서 처리 하여 에서 뷰를 갱신.

#### 2.1.1 Action

```
interface Action
```

immutable data class인 Action 은 특정 이벤트의 시작점 입니다. 기본적으로 사용 되는 공통 Action 으로는 `MessageAction` 이 있으며 Toast, Dialog 등의 메시징 처리를 요 합니다.  

#### 2.1.2 State

```
interface State
```

Redux 에서의 Store 는 단 한개의 State 를 가질 수 있기 때문에 AppStore 를 만들어 내부에 `Map<String, State>` 를 두어 각 도메인의 State 를 관리 하고 있습니다. 

State 는 immutable data class 로서 화면의 상태를 갱신하는데 필요한 데이터를 가질 수 있습니다. Store 내 BehaviorSubject 로 구성되어 있어 해당 subject에 스트림 구독 시 마지막으로 발행되었던 State 혹은 initialized State 를 받을 수 있습니다.  

공통 State 로 `MessageState` 가 있어 Toast, Dialog 등의 메시징 처리를 합니다.  

#### 2.1.3 Middleware

```
typealias Dispatcher = (Action) -> Unit

interface MiddleWare<S: State> {
  fun create(store: Store<S>, next: Dispatcher): Dispatcher
}
```

Action 을 Reducer 에 전달 하기 전 Action 을 처리 하는 미들웨어 입니다. Middleware 는 여러개가 존재 할 수 있으며 이 를 이터레이셔닝 하면서 기존의 액션을 그대로 반환 하거나, 새로운 액션 (Result, Success, Failed, Error 등..)을 반환 할 수 도 있습니다. 

이 프로젝트 에서는 기본적으로 Action 을 처리하는 비즈니스코드의 구현 인 `ActionProcessorMiddleware` 와 Action 처리 에 대한 로그 미들웨어 인 `LoggerMiddleware` 2개가 존재 합니다. 그리고 ActionProcessorMiddleware 에서는 각 도메인 별로 ActionProcessorMiddleware 를 여러개 갖고 있습니다. 

#### 2.1.4 Reducer

```
interface Reducer<S: State> {
  fun reduce(oldState: S, resultAction: Action): S
}
```

Middleware 를 통해 전달받은 Action 을 이전 State 와 함께 처리 하여 새로운 State 를 생성 합니다. 이 State 는 이전 `oldState` 일 수도 있습니다.  

#### 2.1.5 Store (AppStore)

가장 마지막으로 발행된 State 가 저장됩니다. 

### 2.2 MVVM

후술할 koin 을 이용해 Viewmodel 에 대한 주입을 ViewProvider 를 통해 주입됩니다. 그 외 는 기존 Databinding, MVVM 아키텍쳐와 동일합니다. 

## 3. Koin (DI)

dependency injection tool 으로 `Dagger2` 가 아닌 [Koin](https://github.com/InsertKoinIO/koin) 을 사용 했습니다. koin 은 service locator pattern 구조를 갖고 있으며 코틀린으로 작성 되어 코틀린의 장점들을 그대로 사용 할 수 있으며 가독성이 매우 높습니다.  

코인의 장단점은 아래와 같습니다. 

- 장점
  - 100% 코틀린으로 만들어져 코틀린의 장점을 모두 사용할 수 있음. (개인적으로 가독성이 매우 좋다고 생각합니다)
  - dagger 에 비해 러닝커브가 낮음.
  - 컴파일 오버헤드가 없음.
  - 디펜던시가 복잡하지 않은 소규모 프로젝트에 유리.  
- 단점
  - service locator pattern 특유의 runtime error 발생 가능성 존재. 
  - 규모가 크고 디펜던시가 복잡한 프로젝트에서는 한계가 명확히 존재 하며, 이 경우 Dagger 를 사용 하는게 좋음  
  

 