package study.studyspring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.studyspring.model.TodoEntity;
import study.studyspring.persistence.TodoRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public String testService() {
        //todoentity 생성
        TodoEntity entity = TodoEntity.builder().title("my first todo item").build();
        //todoentity 저장
        repository.save(entity);
        // todoentity 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity) {
        // validations
        validate(entity);
        repository.save(entity); // entity 저장

        log.info("entity id : {} is saved", entity.getId()); // entity 저장 완료

        return repository.findByUserId(entity.getUserId());

    }

    // 리팩토링 메서드
    private void validate(final TodoEntity entity) { // 검증해야하니까 private로 ..?
        if(entity == null) {
            log.warn("entity cannot be null");
            throw new RuntimeException("entity cannot be null");
        }

        if(entity.getUserId() == null) { // entity에서 userid가 null일 경우
            log.warn("unknown user");
            throw new RuntimeException("unknown user");
        }
    }

    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity) { // 수정
        // 1. 저장할 엔티티가 유효한지 확인
        validate(entity);

        // 2. 넘겨받은 엔티티 id를 통해 TodoEntity를 가져옴. 존재하지 않는 엔티티는 업데이트할 수 없기 때문
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        // 3. 반환된 todoEntity가 존재하면 값을 새 entity의 값으로 덮어 씌움
        if(original.isPresent()) {
            final TodoEntity todo = original.get();
            // 사용자가 수정할 수 있는게 제목이랑 완료/미완료 선택뿐
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            // 4. db에 새 값을 저장함
            repository.save(todo);
        }

        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> delete(final TodoEntity entity) {

        // 1. 저장할 엔티티가 유효한지 확인
        validate(entity);

        // 2. 엔티티를 삭제
        try {
            repository.delete(entity);
        } catch (Exception e) {
            // 3. exception 발생 시 id, exception logging
            log.error("error deleting entity", entity.getId(), e);

            // 4. 컨트롤러로 exception 날리기, 데이터베이스 내부 로직 캡슐화하기 위해 e를 리턴하지 않고 새 exception 오브젝트 리턴
            throw new RuntimeException("error deleting entity" + entity.getId());

        }

        // 5. 새 todo 리스트 가져와 리턴함
        return retrieve(entity.getUserId());
    }


}
