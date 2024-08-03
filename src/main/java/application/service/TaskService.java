package application.service;

import application.dto.feign.FeignMessage;
import application.dto.request.*;
import application.dto.task.*;
import application.entity.*;
import application.entity.request.Request;
import application.entity.request.RequestComment;
import application.entity.request.RequestStatus;
import application.entity.request.RequestType;
import application.entity.task.Task;
import application.entity.task.TaskComment;
import application.entity.task.TaskStatus;
import application.exception.MyException;
import application.feign.MessagerProxy;
import application.rabbitmq.RabbitMQProducer;
import application.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;
    private UserService userService;
    private RoleService roleService;
    private RequestTypeRepository requestTypeRepository;
    private RequestStatusRepository requestStatusRepository;
    private RequestRepository requestRepository;
    private TaskStatusRepository taskStatusRepository;
    private TaskCommentRepository taskCommentRepository;
    private MessagerProxy feignProxyClient;
    private RabbitMQProducer rabbitProducer;

    public List<String> getAllRoles(){
        return roleService.getAllRoles();
    }


    public void createTask(CreateTaskDto dto, String username){
        if (dto.isPersonal()) {
            createPersonalTask(dto, username);
            String content = "You were assigned with a new task: " + dto.getDescription();
            System.out.println(content);
//            feignProxyClient.sendSystemMessage(new FeignMessage(dto.getReceivers().get(0), content));
            rabbitProducer.sendTaskMessage(new FeignMessage(dto.getReceivers().get(0), content));
        }
        else
            createGroupTask(dto, username);
    }

    @Transactional
    private void createPersonalTask(CreateTaskDto dto, String username){
        User issuer = userService.getRealUserByUsername(username);
        TaskStatus status = taskStatusRepository.findByName("active").orElseGet(() -> {
            TaskStatus stat = new TaskStatus();
            stat.setName("active");
            return taskStatusRepository.save(stat);
        });
        Task task = new Task();
        task.setDescription(dto.getDescription());
        task.setIssuer(issuer);
        task.setTitle(dto.getName());
        task.setPriority(dto.getPriority());
        task.setIssuedTime(LocalDateTime.parse(dto.getStartDate()));
        task.setDueTime(LocalDateTime.parse(dto.getEndDate()));
        task.setPersonal(dto.isPersonal());
        task.setStatus(status);
        Task save = taskRepository.save(task);
        Set<User> saved = dto.getReceivers().stream().map(name -> {
            String[] names = name.split(" ");
            User user = userService.getUserByFullName(names[0], names[1]);
            Set<Task> tasks = user.getTasks();
            tasks.add(save);
            return userService.saveUser(user);
        }).collect(Collectors.toSet());
    }

    @Transactional
    private void createGroupTask(CreateTaskDto dto, String username){
        User issuer = userService.getRealUserByUsername(username);
        Set<User> receivers = new HashSet<>(userService.getUserByRole(dto.getReceivers().get(0)));
        Task task = new Task();
        task.setDescription(dto.getDescription());
        task.setIssuer(issuer);
        task.setPriority(dto.getPriority());
        task.setPersonal(dto.isPersonal());
        task.setReceivers(receivers);
        task.setTitle(dto.getName());
        task.setIssuedTime(LocalDateTime.parse(dto.getStartDate()));
        task.setDueTime(LocalDateTime.parse(dto.getEndDate()));
        taskRepository.save(task);
    }

    public List<PersonalTaskDto> getUserTasks(String username){
        User current = userService.getRealUserByUsername(username);
        return current.getTasks().stream().map(Converter::convertToPersonalTaskDto).toList();
    }

    public List<IssuedTaskDto> getIssuedTasks(String username){
        return taskRepository.findTasksByIssuerUsername(username).stream().map(Converter::convertToIssuedTaskDto).toList();
    }

    @Transactional
    public void updateTask(UpdateTaskDto dto) throws MyException {
        Task task = taskRepository.findTaskById(dto.getId()).orElseThrow(() -> new MyException("Could not find task with id " + String.valueOf(dto.getId())));
        TaskStatus status = taskStatusRepository.findByName("updated").orElseGet(() -> {
            TaskStatus stat = new TaskStatus();
            stat.setName("updated");
            return taskStatusRepository.save(stat);
        });
        task.setTitle(dto.getName());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setDueTime(LocalDateTime.parse(dto.getEndDate()));
        task.setIssuedTime(LocalDateTime.parse(dto.getStartDate()));
        List<String> updated = dto.getReceivers();
        task.setStatus(status);
        if (dto.isChanged()){
            task.getReceivers().stream().forEach(user -> {
                Set<Task> tasks = user.getTasks();
                tasks.remove(task);
                user.setTasks(tasks);
                userService.saveUser(user);
            });
            Task save = taskRepository.save(task);
            for (String str : updated){
                String[] names = str.split(" ");
                User user = userService.getUserByFullName(names[0], names[1]);
                Set<Task> tasks = user.getTasks();
                tasks.add(save);
                user.setTasks(tasks);
                userService.saveUser(user);
            }
        }
    }

    public Request saveRequest(Request request){
        return requestRepository.save(request);
    }

    public void createRequestComment(String username, TaskCommentDto dto) {
        User issuer = userService.getRealUserByUsername(username);
        User receiver = userService.getRealUserByUsername(dto.getReceiver());
        Request request = new Request();
        request.setName("Task comment");
        request.setReceivers(Set.of(receiver));
        request.setIssuer(issuer);
        request.setDescription(String.valueOf(dto.getTaskId()));
        RequestStatus status = requestStatusRepository.findByName("Active").orElseGet(() -> {
            RequestStatus s = new RequestStatus();
            s.setName("Active");
            return requestStatusRepository.save(s);
        });
        RequestType type = requestTypeRepository.findByName("Comment").orElseGet(() -> {
            RequestType t = new RequestType();
            t.setName("Comment");
            return requestTypeRepository.save(t);
        });
        request.setStatus(status);
        request.setType(type);
        requestRepository.save(request);
    }

    public void createRequest(String username, RequestDto dto){
        User issuer = userService.getRealUserByUsername(username);
        Request request = new Request();
        request.setName(dto.getName());
        request.setDescription(dto.getDescription());
        request.setIssuer(issuer);
        RequestStatus status = requestStatusRepository.findByName("Active").orElseGet(() -> {
            RequestStatus s = new RequestStatus();
            s.setName("Active");
            return requestStatusRepository.save(s);
        });
        RequestType type = requestTypeRepository.findByName("Comment").orElseGet(() -> {
            RequestType t = new RequestType();
            t.setName("Comment");
            return requestTypeRepository.save(t);
        });
        request.setType(type);
        request.setStatus(status);
        Request saved = saveRequest(request);
        if (dto.isPersonal()){
            Set<User> savedUsers = dto.getReceivers().stream().map(name -> {
                User realUserByUsername = userService.getRealUserByUsername(name);
                Set<Request> requests = realUserByUsername.getRequests();
                requests.add(saved);
                return userService.saveUser(realUserByUsername);
            }).collect(Collectors.toSet());
        } else {
            System.out.println("It was a group request");
        }
    }

    public List<UserRequestsDto> getUserRequest(String username){
        User current = userService.getRealUserByUsername(username);
        return current.getRequests().stream().map(RequestConverter::convertToUserRequests).toList();
    }

    public List<IssuedRequestsDto> getIssuedRequests(String username){
        return requestRepository.getRequestByIssuer(username).stream().map(RequestConverter::convertToIssuedRequests).toList();
    }

    public Request updateRequest(int id, String newState){
        Request request = requestRepository.findById(id).orElseThrow();
        RequestStatus status = requestStatusRepository.findByName(newState).orElseGet(() -> {
            RequestStatus s = new RequestStatus();
            s.setName(newState);
            return requestStatusRepository.save(s);
        });
        request.setStatus(status);
        return requestRepository.save(request);
    }

//    I have to create a ChatRoom each time a new user is created. such room will be used for system calls
    public void updateRequestWithMessage(int id, String message, String username, String newState){
        Request request = updateRequest(id, newState);
        String msg = "Your request " + String.valueOf(id) + " was updated";
        RequestComment comment = new RequestComment();
        comment.setComment(message);
        comment.setCommentator(username);
        comment.setRequest(request);
        request.getComments();
        //TODO: send message to the messager
    }

    public void updateRequestWithoutMessage(int id, String username, String newState){
        Request request = updateRequest(id, newState);
        String msg = "Your request " + String.valueOf(id) + " was updated";
        //TODO: send message to the messager
    }


    public void requestProceed(UpdateRequestDto dto, String username){
        if (dto.getMessage().equals(""))
            updateRequestWithoutMessage(dto.getId(), username, dto.getNewState());
        else
            updateRequestWithMessage(dto.getId(), dto.getMessage(), username, dto.getNewState());
    }


    public List<CommentsDto> getTaskComment(int id) {
        Task task = taskRepository.findTaskById(id).orElseThrow();
        Set<TaskComment> comments = task.getComments();
        return comments.stream().map(Converter::convertToCommentsDto).toList();
    }

    public void completeTask(int id, String name) {
        User user = userService.getRealUserByUsername(name);
        Task task = taskRepository.findTaskById(id).orElseThrow();
        task.setPerformer(user);
        task.setStatus(taskStatusRepository.findByName("Closed").orElseGet(() -> {
            TaskStatus status = new TaskStatus();
            status.setName("Closed");
            return taskStatusRepository.save(status);
        }));
        Set<Task> completedTasks = user.getCompletedTasks();
        completedTasks.add(taskRepository.save(task));
        userService.saveUser(user);
    }

    public List<PersonalTaskDto> myCompletedTasks(String name) {
        User user = userService.getRealUserByUsername(name);
        return user.getCompletedTasks().stream().map(Converter::convertToPersonalTaskDto).toList();
    }

    public void createTaskComment(int taskId, String username, String com) {
        Task task = taskRepository.findTaskById(taskId).orElseThrow();
        TaskComment comment = new TaskComment();
        comment.setComment(com);
        comment.setCommentator(username);
        comment.setTask(task);
        TaskComment saved = taskCommentRepository.save(comment);
        Set<TaskComment> comments = task.getComments();
        comments.add(saved);
        //create a future call that will create a system call to the issuer of the task
        taskRepository.save(task);
    }
}
