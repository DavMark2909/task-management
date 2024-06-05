package application.dto.item;

import application.entity.Item;

public class ItemConverter {

    public static ItemDto from(Item item){
        return ItemDto.builder().name(item.getName()).quantity(item.getQuantity())
                .type(item.getType().getName()).measurement(item.getType().getMeasurement())
                .build();
    }
}
