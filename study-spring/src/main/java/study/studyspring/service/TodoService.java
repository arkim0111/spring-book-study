package study.studyspring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.studyspring.model.TodoEntity;
import study.studyspring.persistence.TodoRepository;

import java.util.List;

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

}
