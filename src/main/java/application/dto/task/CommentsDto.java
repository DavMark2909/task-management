package application.dto.task;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentsDto {
    private String content, issuer;
}
