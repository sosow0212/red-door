# Core Module

해당 프로젝트에선 Domain, Entity를 분리해서 사용하고 있습니다.

현재는 Persistence를 Core에 POJO로 이뤄진 Domain과 함께 두고 있습니다. 또한 현재는 Port의 구현체인 Adapter도 해당 모듈에서 관리합니다.

현재 시점에서 이를 모두 분리하는 것은 오히려 작업이 번거로워진다고 생각이 들었습니다.
만약 추후 모듈 분리가 필요하다고 판단되면 분리할 계획을 가지고 있습니다.

- Aggregate
    - domain (POJO)
        - model
        - port
          - useCase
          - etc

    - adapter
      - persistence (외부 영속성 기술 의존)
          - entity
          - mapper
      - etc
