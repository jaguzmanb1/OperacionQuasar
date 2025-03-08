package com.github.jaguzmanb1.quasar.repository;

import com.github.jaguzmanb1.quasar.entity.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SatelliteRepositoryInterface extends JpaRepository<Satellite, Long> {

    Optional<Satellite> findByName(String name);

    List<Satellite> findAllByNameIn(Set<String> names);
}
