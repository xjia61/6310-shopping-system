package com.CS6310.Team045.repository;

import com.CS6310.Team045.model.Drone;
import com.CS6310.Team045.model.Item;
import com.CS6310.Team045.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone,String> {
    List<Drone> findDroneByStore(Store store);
    Optional<Drone> findByStore_nameAndId(String store, String id);

}
