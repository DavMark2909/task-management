package application.controller;

import application.dto.item.CreateItemDto;
import application.dto.item.CreateTypeDto;
import application.exception.MyException;
import application.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories(){
        return ResponseEntity.ok(itemService.getAllCategories());
    }

    @PostMapping("/createItem")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity createItem(@RequestBody CreateItemDto dto) throws MyException {
        return ResponseEntity.ok(itemService.createItem(dto));
    }

    @PostMapping("/createType")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> createType(@RequestBody CreateTypeDto dto) throws MyException {
        itemService.createType(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getItems/{type}")
    public ResponseEntity<?> getItems(@PathVariable(required = false) String type){
        return ResponseEntity.ok(itemService.getItems(type));
    }
}
