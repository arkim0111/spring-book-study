package study.studyspring.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.studyspring.model.TodoEntity;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

    List<TodoEntity> findByUserId(String userId); // id 통해서 list를 빼내기
}
