package application.dto.task;

import application.dto.user.TaskUserDto;
import application.entity.task.Task;

import java.util.List;

public class Converter {

    public static PersonalTaskDto convertToPersonalTaskDto(Task task){
        return PersonalTaskDto.builder().name(task.getTitle()).description(task.getDescription()).priority(task.getPriority())
                .issuer(task.getIssuer().getUsername()).id(task.getId())
                .startDate(task.getIssuedTime().toString()).endDate(task.getDueTime().toString())
                .build();
    }

    public static IssuedTaskDto convertToIssuedTaskDto(Task task){
        List<TaskUserDto> taskUserDtos = task.getReceivers().stream().map(user -> {
            String fullname = user.getName() + " " + user.getLastName();
            return TaskUserDto.builder().fullname(fullname).username(user.getUsername()).build();
        }).toList();
        return IssuedTaskDto.builder().name(task.getTitle()).description(task.getDescription()).priority(task.getPriority())
                .startDate(task.getIssuedTime().toString()).endDate(task.getDueTime().toString())
                .id(task.getId())
                .receivers(taskUserDtos)
                .personal(task.isPersonal())
                .build();
    }
}
