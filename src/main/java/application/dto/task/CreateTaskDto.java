package application.dto.task;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateTaskDto {
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private boolean personal;
    private String priority;
    private List<String> receivers;
}
