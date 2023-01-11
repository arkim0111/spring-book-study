package study.studyspring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    public String testController() {
        return "HELLO WORLD!";
    }

    @GetMapping("/testGetMapping")
    public String testControllerWithPath() {
        return "HELLO WORLD TestGetMapping!";
    }

    @GetMapping("/{id}")
    public String testControllerWithPathVariable(@PathVariable(required=false) int id) {

        return "Hello World! ID : " + id;
    }

    @GetMapping("/testRequestBody")
    public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
        return "Hello World! ID : " + testRequestBodyDTO.getId() + " Message : " + testRequestBodyDTO.getMessage();

    }

    @GetMapping("/testResponseBody")
    public ResponseDTO<String> testControllerResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("HELLO WORLD! I'm ResponseDTO");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return response;
    }

    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerResponseEntity() {
        List<String> list = new ArrayList<>();
        list.add("HELLO WORLD! I'm ResponseEntity! And you got 400! ");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        // http status를 400으로 설정함
        return ResponseEntity.badRequest().body(response);
        // return ResponseEntity.ok().body(response);
    }

}
