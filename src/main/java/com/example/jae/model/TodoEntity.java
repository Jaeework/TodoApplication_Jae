package com.example.jae.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder    // Builder 클래스를 따로 개발하지 않고도 Builder 패턴을 사용해 오브젝트 생성가능. 생성자의 매개변수 순서를 기억하지 않아도 된다.
@NoArgsConstructor  // 매개변수 없는 생성자를 구현해 준다.
@AllArgsConstructor // 모든 멤버변수를 매개변수로 받는 생성자를 구현해 준다.
@Data   // 멤버변수의 getter; setter;를 구현해 준다.
// 멤버변수 : 선언 위치가 클래스 영역 (인스턴스 변수, 클래스 변수) cf) 지역 변수  : 메소드나 생성자 내부
@Entity
@Table(name="Todo") // 테이블 이름 지정
public class TodoEntity {
    @Id
    @GeneratedValue(generator = "system-uuid") // 자동으로 생성
    @GenericGenerator(name="system-uuid", strategy = "uuid") // Hibernate 가 제공하는 기본 Generator 가 아닌 나만의 Generator 사용하고 싶을 경우
    private String id;  // 이 오브젝트의 아이디
    private String userId;  // 이 오브젝트를 생성한 사용자의 아이디
    private String title;   // Todo 타이틀(예: 운동하기)
    private boolean isDone;



}
