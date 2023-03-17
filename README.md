- 변수 쪼개기
  - 임시로 할당하는 변수는 의미를 잘 표현해야한다.
  - 누적하는 변수가 존재한다고 해서 모든 누적값을 누적해서는 안되며 
  - 값의 의미를 잘표현하는 별개의 변수로 쪼개야한다
  - 입력값을 변경해서 리턴하는 경우 의미가 명확하지 않다. 입력값을 신규 변수에 할당해 의미를 명확하게 해라
- 질의 함수와 변경함수 분리하기
  - 한 메서드에서 2가지 역할을 한다면 메서드를 쪼갠다 
  - for문을 여러번 돌리는걸 두려워하지 말것
- 계산된 결과는 함수로 대체한다
  - 계산값이 불변인 경우는 제외
  - 변환함수를 통해서 새로운 객체를 만들고 변환함수가 계산된 값을 넣어준다.
  - 중복된 계산 로직을 변한함수에 모아서 재사용가능하다.
- 함수나 클래스는 한가지 이유로 변경해야한다
  - 다른 일을 하는 코드는 다른 모듈로 분리한다
    - 여러 단계로 쪼개인 작업은 메서드로 분리한다.
    - 중간 데이터를 만들어 단계를 구별 + 매개변수를 줄일수 있음
- 함수옮기기
  - 다큰 클래스의 필드를 더 많이 참조하는 경우
  - 다른 클래스에서도 필요하는 경우 
- 클래스 추출하기
  - 데이터, 메서드중 일부가 매우 밀접하다
  - 일부 데이터가 같이 바뀐다