package study.studyspring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder // builder 어노테이션 사용하면 우리가 builder 클래스를 따로 만들지 않아도 오브젝트 생성 가능
@NoArgsConstructor // 매개변수가 없는 생성자를 구현
@AllArgsConstructor // 클래스의 모든 멤버변수를 매개변수로 받는 생성자를 구현
@Data // getter/setter 매서드 구현
@Entity // 데이터베이스 테이블마다 그에 상응하는 엔티티 클래스가 존재한다.
@Table(name="Todo") // 테이블 이름 지정
// 테이블 name 지정 x => entity name => entity name x => class name
public class TodoEntity {

    @Id // 기본키가 될 필드에 지정
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id; // 오브젝트의 아이디
    private String userId; // 오브젝트를 생성한 유저의 아이디
    private String title; // todo 타이틀
    private boolean done; // true - todo 완료

}
