package com.CS6310.Team045.repository;

import com.CS6310.Team045.model.Item;
import com.CS6310.Team045.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> getItemByStore(Store store);
    Optional<Item> findByStore_nameAndName(String store, String name);

}

