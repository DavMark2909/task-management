package application.dto.task;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PersonalTaskDto {
    private int id;
    private String name, description, priority, startDate, endDate;
    private String issuer;
    private String status;
}
