package application.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequestDto {
    private String name;
    private String suggestion;
    private String type;
    private boolean personal;
    private List<String> receivers;
}
