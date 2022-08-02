package com.CS6310.Team045.repository;

import com.CS6310.Team045.model.ItemLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemLineRepository extends JpaRepository<ItemLine,Long> {
    Optional<ItemLine> findItemLineByOrderAndOrder(String itemLine, String order);
}
