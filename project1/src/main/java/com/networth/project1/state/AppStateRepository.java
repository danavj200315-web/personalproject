package com.networth.project1.state;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppStateRepository extends JpaRepository<AppStateEntity, Long> {

    Optional<AppStateEntity> findByProfileKey(String profileKey);
}
