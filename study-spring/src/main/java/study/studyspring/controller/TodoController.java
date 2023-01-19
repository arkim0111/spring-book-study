package study.studyspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.studyspring.dto.ResponseDTO;
import study.studyspring.dto.TodoDTO;
import study.studyspring.model.TodoEntity;
import study.studyspring.service.TodoService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService(); // 테스트서비스 이용
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {

        try {
            String temporaryUserId = "temporary-user"; // temporary user id

            // 1. todoEntity로 변환
            TodoEntity entity = TodoDTO.toEntity(dto);

            // 2. id를 null로 초기화
            entity.setId(null);

            // 3. 임시 유저 아이디를 설정해줌. 한 유저만 로그인없이 사용가능하게 임시로 만들기
            entity.setUserId(temporaryUserId);

            // 4. 서비스를 이용해 todoEntity 생성
            List<TodoEntity> entities = service.create(entity);

            // 5. 자바 스트림을 이용해 리턴된 엔티티 리스트를 todoDTO 리스트로 변환 entity -> dto
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // 6. 변환된 TodoDTO 리스트를 이용해 ResponseDTO 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // 7. reponseDTO 리턴
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // 8. 에러 나면 dto 대신 메세지 띄우기
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        String temporyUserId = "temporary-user";

        // 1. 서비스메서드에서 retrieve 메서드를 사용해 todoList 가져온다
        List<TodoEntity> entities = service.retrieve(temporyUserId);

        // 2. 자바 스트림 이용해서 리턴된 엔티티 리스트를 todo 리스트로 변환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        
        // 6. 변환된 TodoDTO 리스트를 이용해서 responseDTO 초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // 7. responseDTO 리턴
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {

        String temporaryUserId = "temporary-user";

        // 1. dto -> entity 변환
        TodoEntity entity = TodoDTO.toEntity(dto);

        // 2. id를 temporaryUserId로 초기화.
        entity.setUserId(temporaryUserId);

        // 3. 서비스를 이용해 entity 업데이트
        List<TodoEntity> entities = service.update(entity);

        // 4. 자바 스트림 이용해서 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        
        // 5. 변환된 TodoDTO 리스트를 이용해서 ResponseDTO 초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // 6. ResponseDTO return
        return ResponseEntity.ok().body(response);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteTODO(@RequestBody TodoDTO dto) {

        try {
            String temporaryUserId = "temporary-user";

            // 1. TodoEntity로 변환
            TodoEntity entity = TodoDTO.toEntity(dto);

            // 2. 임시 유저 아이디 설정.
            entity.setUserId(temporaryUserId);

            // 3. 서비스를 이용해 entity 삭제
            service.delete(entity);
        }

    }

}
