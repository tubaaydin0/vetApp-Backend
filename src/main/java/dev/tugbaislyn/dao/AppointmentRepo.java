package dev.tugbaislyn.dao;

import dev.tugbaislyn.entity.Animal;
import dev.tugbaislyn.entity.Appointment;
import dev.tugbaislyn.entity.Doctor;
import dev.tugbaislyn.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
    //Randevular kullanıcı tarafından girilen tarih aralığına ve hayvana göre filtrelenmesi
    List<Appointment> findByAnimalIdAndAppointmentDateBetween(Long animalId,LocalDateTime startDate, LocalDateTime endDate);

	//Randevular kullanıcı tarafından girilen tarih aralığına ve doktora göre filtrelenmesi
    List<Appointment> findByDoctorIdAndAppointmentDateBetween(Long doctorId, LocalDateTime startDate, LocalDateTime endDate);

    // Belirli tarih ve saatte o doktorun randevusu olup olmadığına bakılır.
    Optional<Appointment> findByAppointmentDateAndDoctorId(LocalDateTime appointmentDate, Long doctorId);

    // Belirli tarih ve saatte o doktorun  ve hayvanın randevusu olup olmadığına bakılır.(updateişleminde)
    Optional<Appointment> findByAppointmentDateAndDoctorIdAndAnimalId(LocalDateTime appointmentDate, Long doctorId,Long animalId);
    List<Appointment> findByAnimalId(Long animalId);

    Optional<Appointment> findByAppointmentDateAndAnimalId(LocalDateTime appointmentDate, Long animalId);
}
