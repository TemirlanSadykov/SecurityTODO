package com.example.ooo.backend.service;

import com.example.ooo.backend.dto.RoleDTO;
import com.example.ooo.backend.forms.FindTodoForm;
import com.example.ooo.backend.model.Role;
import com.example.ooo.backend.model.Status;
import com.example.ooo.backend.model.Todo;
import com.example.ooo.backend.repository.RoleRepo;
import com.example.ooo.backend.repository.TodoRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PredicateService {

    private final TodoRepo todoRepo;

    public List<Todo> filterTodo(Principal principal, FindTodoForm findTodoForm) {
        List<Todo> intersection1 = new ArrayList<Todo>(filterTodoByStatus(principal, findTodoForm));
        intersection1.retainAll(filterTodoByName(principal, findTodoForm));
        List<Todo> intersection2 = new ArrayList<Todo>(filterByDateStart(principal, findTodoForm));
        intersection2.retainAll(filterByDateFinish(principal, findTodoForm));
        List<Todo> intersection = new ArrayList<Todo>(intersection1);
        intersection.retainAll(intersection2);
        return intersection;
    }
    public List<Todo> filterByDateStart(Principal principal, FindTodoForm findTodoForm){
        if (findTodoForm.getStartDate().equals("")) {
            return todoRepo.findAllByUserLogin(principal.getName());
        }
        return todoRepo.findAllByDateAfterAndUserLogin(LocalDateTime.parse(findTodoForm.getStartDate()), principal.getName());
    }
    public List<Todo> filterByDateFinish(Principal principal, FindTodoForm findTodoForm){
        if (findTodoForm.getFinishDate().equals("")) {
            return todoRepo.findAllByUserLogin(principal.getName());
        }
        return todoRepo.findAllByDateBeforeAndUserLogin(LocalDateTime.parse(findTodoForm.getFinishDate()), principal.getName());
    }
    public List<Todo> filterTodoByName(Principal principal, FindTodoForm findTodoForm) {
        if (findTodoForm.getName().equals("")) {
            return todoRepo.findAllByUserLogin(principal.getName());
        }
        return todoRepo.findAllByNameAndUserLogin(findTodoForm.getName(), principal.getName());
    }

    public List<Todo> filterTodoByStatus(Principal principal, FindTodoForm findTodoForm) {
        if (findTodoForm.getStatus().equals("all")) {
            return todoRepo.findAllByUserLogin(principal.getName());
        }
        return todoRepo.findAllByStatusAndUserLogin(Status.valueOf(findTodoForm.getStatus()), principal.getName());
    }
}
