package com.example.jae.controller;

import com.example.jae.dto.ResponseDTO;
import com.example.jae.dto.TodoDTO;
import com.example.jae.model.TodoEntity;
import com.example.jae.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try{
            String temporaryUserId = "temporary-user"; // temporary user id.

            // (1) TodoEntity로 변환
            TodoEntity entity = TodoDTO.toEntity(dto);

            // (2) id를 null 로 초기화 한다. 생성 당시에는 id가 없어야 하기 때문이다.
            entity.setId(null);

            // (3) 임시 사용자 아이디를 설정해 준다. 이 부분은 인증과 인가에서 수정할 예정.
            // 한 사용자(temporary-user)만 로그인 없이 사용할 수 있는 애플리케이션인 셈.
            entity.setUserId(temporaryUserId);

            // (4) 서비스를 이용해 Todo 엔터티 생성
            List<TodoEntity> entities = service.create(entity);

            // (5) 자바 스트링을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // (6) 변환된 TodoDTO 리스트를 이용해 responseDTO를 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // (7) ResponseDTO를 리턴
            return ResponseEntity.ok().body(response);


        } catch (Exception e) {
            // (8) 예외가 있는 경우 dto 대신 error 메세지를 리턴
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/read")
    public ResponseEntity<?> readTodo(@RequestParam String userId) {
        try {
            TodoEntity entity = TodoEntity.builder().userId(userId).build();

            List<TodoEntity> entities = service.read(entity);

            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        } catch(Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }
}
