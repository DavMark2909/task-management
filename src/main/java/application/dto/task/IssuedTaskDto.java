package application.dto.task;

import application.dto.user.TaskUserDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class IssuedTaskDto {
    private int id;
    private String name, description, priority, startDate, endDate;
    private boolean personal;
    private List<TaskUserDto> receivers;
}
