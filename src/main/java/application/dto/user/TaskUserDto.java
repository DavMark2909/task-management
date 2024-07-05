package application.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskUserDto {
    private String fullname;
    private String username;
}
