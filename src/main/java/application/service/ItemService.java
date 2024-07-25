package application.service;

import application.dto.item.*;
import application.entity.Item;
import application.entity.ItemType;
import application.exception.ItemAlreadyExists;
import application.exception.MyException;
import application.repository.ItemRepository;
import application.repository.ItemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemTypeRepository itemTypeRepository;

    public List<String> getAllCategories(){
        return itemTypeRepository.getAllCategories();
    }

    public ItemDto createItem(CreateItemDto dto) throws MyException {
        Optional<Item> byName = itemRepository.findByName(dto.getName());
        if (byName.isPresent())
            throw new ItemAlreadyExists(dto.getName());
        Optional<ItemType> type = itemTypeRepository.findByName(dto.getType());
        if (type.isEmpty())
            throw new MyException("There is no such type: " + dto.getType());
        Item item = new Item();
        item.setName(dto.getName());
        item.setType(type.get());
        item.setPrice(dto.getPrice());
        return ItemConverter.from(itemRepository.save(item));
    }

    public void updateItem(UpdateItemDto dto) throws MyException {
        Optional<Item> item = itemRepository.findByName(dto.getName());
        if (item.isEmpty())
            throw new MyException("Item " + dto.getName() + " doesn't exist");
        Item updated = item.get();
        updated.setQuantity(updated.getQuantity() + dto.getChange());
        itemRepository.save(updated);
    }

    public void createType(CreateTypeDto dto) throws MyException {
        Optional<ItemType> typeOptional = itemTypeRepository.findByName(dto.getName());
        if (typeOptional.isPresent())
            throw new MyException("Type " + dto.getName() + " already exists");
        ItemType itemType = new ItemType();
        itemType.setName(dto.getName());
        itemType.setMeasurement(dto.getMeasurement());
        itemTypeRepository.save(itemType);
    }

    public List<ItemDto> getItems(String type){
        if (type == "all") {
            List<Item> all = itemRepository.findAll();
            return all.stream().map(ItemConverter::from).collect(Collectors.toList());
        }
        return itemRepository.findAllByType(type).stream().map(ItemConverter::from).collect(Collectors.toList());
    }
}
