package application.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TaskCommentDto {
    private String suggestion;
    private String receiver;
    private int taskId;
}
