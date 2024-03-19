package dev.tugbaislyn.dao;

import dev.tugbaislyn.entity.Animal;
import dev.tugbaislyn.entity.Vaccine;
import org.springframework.beans.factory.aot.AotServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine,Long> {
    List<Vaccine> findByNameAndCodeAndAnimalId(String name, String code, Long id);
   Optional<Vaccine> findByNameAndCodeAndAnimalIdAndProtectionStartDateAndProtectionFinishDateAndReportId(String name, String code, Long id, LocalDate protectionStartDate, LocalDate protectionFinishDate, Long reportId);
    Optional<Vaccine> findByNameAndCodeAndAnimalIdAndProtectionStartDateAndProtectionFinishDate(String name, String code, Long id, LocalDate protectionStartDate, LocalDate protectionFinishDate);

    List<Vaccine> findByAnimalId(Long animalId);// Hayvan id'ye göre listeleme

    List<Vaccine> findByProtectionFinishDateBetween(LocalDate startDate,LocalDate finishDate);

    @Query("FROM Vaccine v JOIN v.animal a WHERE a.name LIKE %:animalName%")
    List<Vaccine> findByAnimalName(@Param("animalName") String animalName);
}
