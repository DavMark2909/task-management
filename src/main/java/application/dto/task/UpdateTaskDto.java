package application.dto.task;

import lombok.Data;

import java.util.List;

@Data
public class UpdateTaskDto {
    private int id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private boolean isPersonal;
    private String priority;
    private List<String> receivers;
    private boolean changed;
}
