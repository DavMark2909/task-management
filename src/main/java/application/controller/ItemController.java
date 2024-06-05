package application.controller;

import application.dto.item.CreateItemDto;
import application.dto.item.CreateTypeDto;
import application.exception.MyException;
import application.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping("/createItem")
    public ResponseEntity createItem(@RequestBody CreateItemDto dto) throws MyException {
        return ResponseEntity.ok(itemService.createItem(dto));
    }

    @PostMapping("/createType")
    public ResponseEntity<Void> createType(@RequestBody CreateTypeDto dto) throws MyException {
        itemService.createType(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
