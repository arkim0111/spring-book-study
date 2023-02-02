package study.studyspring.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.studyspring.model.UserEntity;

@Repository
public class UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByUsername(String username);

    Boolean existsByUsername(String username);
    UserEntity findByUsernameAndPassword(String username, String password);




}
