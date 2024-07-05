package application.dto.request;

import lombok.Data;

@Data
public class UpdateRequestDto {
    private String message;
    private String newState;
    private int id;
}
