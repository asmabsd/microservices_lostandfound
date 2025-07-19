package com.example.item_service.services;

import com.example.item_service.entities.Item;
import com.example.item_service.entities.Status;
import com.example.item_service.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item createItem(Item item) {
        item.setStatus(item.getStatus() == null ? Status.LOST : item.getStatus());
        return itemRepository.save(item);
    }



    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    public Item getItem(long id ) {
        return itemRepository.findItemById(id );
    }

    public Item updateItem(Long id, Item updatedItem) {
        Item item = itemRepository.findById(id).orElseThrow();
        item.setDescription(updatedItem.getDescription());
        item.setLocation(updatedItem.getLocation());
        // etc.
        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public Item updateStatus(Long id, Status status) {
        Item item = itemRepository.findById(id).orElseThrow();
        item.setStatus(status);
        return itemRepository.save(item);
    }
}
