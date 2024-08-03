package application.controller;

import application.dto.feign.FeignMessage;
import application.dto.request.IssuedRequestsDto;
import application.dto.request.RequestDto;
import application.dto.request.TaskCommentDto;
import application.dto.request.UserRequestsDto;
import application.dto.task.*;
import application.exception.MyException;
import application.rabbitmq.RabbitMQProducer;
import application.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('manager')")
    public ResponseEntity<List<String>> getRoles(){
        return ResponseEntity.ok(taskService.getAllRoles());
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('manager')")
    public ResponseEntity<String> createTask(Authentication authentication, @RequestBody CreateTaskDto dto){
        taskService.createTask(dto, authentication.getName());
        return ResponseEntity.ok("Created");
    }

    @GetMapping("/my-tasks")
    public ResponseEntity<List<PersonalTaskDto>> getUserTasks(Authentication auth){
        return ResponseEntity.ok(taskService.getUserTasks(auth.getName()));
    }

    @GetMapping("/my-created-tasks")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('manager')")
    public ResponseEntity<List<IssuedTaskDto>> getCreatedTasks(Authentication auth){
        return ResponseEntity.ok(taskService.getIssuedTasks(auth.getName()));
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('manager')")
    public ResponseEntity<String> updateTask(@RequestBody UpdateTaskDto dto) throws MyException {
        taskService.updateTask(dto);
        return ResponseEntity.ok("Updated");
    }

//    adds a comment to the task
    @PostMapping("/request-comment")
    public ResponseEntity<String> createTaskComment(Authentication auth, @RequestBody TaskCommentDto dto){
        taskService.createRequestComment(auth.getName(), dto);
        return ResponseEntity.ok("Created");
    }

    @PostMapping("/request")
    public ResponseEntity<String> createRequest(Authentication auth, @RequestBody RequestDto dto){
        taskService.createRequest(auth.getName(), dto);
        return ResponseEntity.ok("Created");
    }

    @GetMapping("/requests")
    public ResponseEntity<List<UserRequestsDto>> getRequestsForMe(Authentication auth){
        return ResponseEntity.ok(taskService.getUserRequest(auth.getName()));
    }

    @GetMapping("/my-requests")
    public ResponseEntity<List<IssuedRequestsDto>> getMyRequests(Authentication auth){
        return ResponseEntity.ok(taskService.getIssuedRequests(auth.getName()));
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentsDto>> getTaskComments(@RequestParam int id){
        return ResponseEntity.ok(taskService.getTaskComment(id));
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completeTask(@RequestParam(name = "id") int id, Authentication auth){
        taskService.completeTask(id, auth.getName());
        return ResponseEntity.ok("Completed");
    }

    @GetMapping("/my-completed")
    public ResponseEntity<List<PersonalTaskDto>> myCompletedTasks(Authentication auth){
        return ResponseEntity.ok(taskService.myCompletedTasks(auth.getName()));
    }

    @PostMapping("/add-comment")
    public ResponseEntity<String> addComment(@RequestParam int id, Authentication auth, @RequestBody CreateTaskCommentDto dto){
        taskService.createTaskComment(id, auth.getName(), dto.getComment());
        return ResponseEntity.ok("Created");
    }

}
