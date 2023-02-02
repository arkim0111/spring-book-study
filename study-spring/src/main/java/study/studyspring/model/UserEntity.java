package study.studyspring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id; // 유저에게 고유하게 부여되는 아이디

    @Column(nullable = false)
    private String username; // 아이디로 사용할 유저네임. 이메일일수도, 문자열일수도 있음
    private String password; // 비밀번호
    private String role; // 사용자의 역할 (관리자, 일반)
    private String authProvider; // 이후 OAuth에서 사용할 유저 정보 제공자 : github
}
