package dev.tugbaislyn.dao;

import dev.tugbaislyn.dto.response.AvailableDateResponse;
import dev.tugbaislyn.entity.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate,Long> {

    Optional<AvailableDate> findByAvailableDateAndDoctorId(LocalDate availableDate, Long id);
    List<AvailableDate> findByDoctorId(Long id); // Doktorun müsait olduğu günleri listeler.

}
