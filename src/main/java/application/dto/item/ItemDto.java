package application.dto.item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDto {
    private String name;
    private String type;
    private String measurement;
    private double quantity;
    private double price;
}
