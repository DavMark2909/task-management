package application.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class IssuedRequestsDto {
    private String name, description, status;
    private List<String> receivers;
}
