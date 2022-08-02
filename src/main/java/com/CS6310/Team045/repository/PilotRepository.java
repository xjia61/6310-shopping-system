package com.CS6310.Team045.repository;

import com.CS6310.Team045.model.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PilotRepository extends JpaRepository<Pilot,Long> {
    Optional<Pilot> findPilotByAccount(String account);
    Optional<Pilot> findPilotByLicenseID(String id);

}
