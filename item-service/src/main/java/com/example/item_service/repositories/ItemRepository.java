package com.example.item_service.repositories;

import com.example.item_service.entities.Item;
import com.example.item_service.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByStatus(Status status);
    List<Item> findByUseremail(String email);

    Item findItemById( long itemId);
}
