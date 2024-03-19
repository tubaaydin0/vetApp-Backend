package dev.tugbaislyn.dao;
import dev.tugbaislyn.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
public interface ReportRepo extends JpaRepository <Report,Long> {
    Optional <Report> findByAppointmentId(Long appointmentId);

    Optional<Report> findByAppointmentIdAndDiagnosisAndTitleAndPrice(Long appointmentId, String diagnosis, String title,double price);
}
