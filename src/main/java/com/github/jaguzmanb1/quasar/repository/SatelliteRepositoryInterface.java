package com.github.jaguzmanb1.quasar.repository;

import com.github.jaguzmanb1.quasar.entity.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface SatelliteRepositoryInterface extends JpaRepository<Satellite, Long> {

    Satellite findByName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Satellite s SET s.distance = :distance WHERE s.name = :name")
    int updateDistanceByName(@Param("name") String name, @Param("distance") Double distance);

    @Transactional
    @Modifying
    @Query("UPDATE Satellite s SET s.receivedMessage = :receivedMessage WHERE s.name = :name")
    int updateReceivedMessageByName(@Param("name") String name, @Param("receivedMessage") String receivedMessage);

    @Transactional
    @Modifying
    @Query("UPDATE Satellite s SET s.distance = :distance, s.receivedMessage = :receivedMessage WHERE s.name = :name")
    void updateDistanceAndMessageByName(
            @Param("name") String name,
            @Param("distance") Double distance,
            @Param("receivedMessage") String receivedMessage
    );
}

