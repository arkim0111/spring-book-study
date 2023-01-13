package study.studyspring.dto;

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

}
