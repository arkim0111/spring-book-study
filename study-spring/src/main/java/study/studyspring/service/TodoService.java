package study.studyspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.studyspring.model.TodoEntity;
import study.studyspring.persistence.TodoRepository;

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

}
