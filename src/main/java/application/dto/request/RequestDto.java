package application.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RequestDto {
    private String name, description, type;
    private boolean personal;
    private List<String> receivers;
}
