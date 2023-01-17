package study.studyspring.dto;

// dto 사용이유?
// http 응답 반환할 때, 비즈니스 로직을 캡슐화하거나 추가적인 정보를 함께 반환하기 위해 사용함

// 컨트롤러는 유저에게서 todoDTO를 요청 바디로 넘겨받고, todoEntity로 변환해 저장해야 하며,
// todoService의 create()이 리턴하는 todoEntity를 todoDTO로 변환해 리턴해야한다.

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.studyspring.model.TodoEntity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {

    private String id;
    // userid 없는 이유 : 스프링 시큐리티를 이용해 구현할 예정.
    // 유저가 자기 아이디를 넘겨주지 않아도 인증이 가능
    private String title;
    private boolean done;


    public TodoDTO(final TodoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    public static TodoEntity toEntity(final TodoDTO dto) { //dto를 entity로 변환하기 위함
         return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .build();
    }

}
