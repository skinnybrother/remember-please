package com.smalldogg.rememberplease.domain.todo;

import com.smalldogg.rememberplease.domain.todo.dto.CreateTodoDto;
import com.smalldogg.rememberplease.domain.todo.dto.TodoRequestDto;
import com.smalldogg.rememberplease.domain.todo.dto.TodoResponseDto;
import com.smalldogg.rememberplease.domain.todo.mapper.CreateTodoMapper;
import com.smalldogg.rememberplease.domain.todo.mapper.TodoRequestMapper;
import com.smalldogg.rememberplease.domain.todo.mapper.TodoResponseMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class todoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final CreateTodoMapper createTodoMapper = Mappers.getMapper(CreateTodoMapper.class);
    private final TodoResponseMapper todoResponseMapper = Mappers.getMapper(TodoResponseMapper.class);
    private final TodoRequestMapper todoRequestMapper = Mappers.getMapper(TodoRequestMapper.class);

    @Override
    public TodoResponseDto findTodo(Long todoId) {
        todoRepository.findById(todoId);
        return new TodoResponseDto();
    }

    @Override
    public List<TodoResponseDto> findTodos() {
        return todoResponseMapper.toDto(todoRepository.findAll());
    }

    @Override
    public Todo createTodo(CreateTodoDto createTodoDto) {
        return todoRepository.save(createTodoMapper.toEntity(createTodoDto));
    }

    @Override
    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }

    @Override
    public void updateTodo(Long todoId, TodoRequestDto todoRequestDto) {
        Optional<Todo> todoOptional = todoRepository.findById(todoId);
        todoOptional.orElseThrow(()->new NoSuchElementException("대상이 존재하지 않음"));
        if(todoOptional.isPresent()){
            Todo todo = todoOptional.get();

            todoRequestMapper.updateFromDto(todoRequestDto,todo);
        }
    }
}