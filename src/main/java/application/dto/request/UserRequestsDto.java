package application.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestsDto {
    private String name;
    private String description;
    private String username;
    private String status;
}
