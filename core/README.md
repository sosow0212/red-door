# Core Module

해당 프로젝트에선 Domain, Entity를 분리해서 사용하고 있습니다.

현재는 Persistence를 Core에 POJO로 이뤄진 Domain과 함께 두고 있습니다.
이 둘을 현재 시점에서 분리하는 것은 오히려 번거롭다고 생각이 들었습니다. 만약 추후 모듈 분리가 필요하다고 판단되면 분리할 계획을 가지고 있습니다.

- Aggregate
    - domain (POJO)
        - model
        - port
        - useCase

    - Persistence (외부 영속성 기술 의존)
        - entity
        - mapper
