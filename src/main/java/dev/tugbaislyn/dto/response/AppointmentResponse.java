package dev.tugbaislyn.dto.response;

import dev.tugbaislyn.entity.Animal;
import dev.tugbaislyn.entity.Doctor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private Long id;
    private LocalDateTime appointmentDate;
    private Doctor doctor;
    private Animal animal;
}
