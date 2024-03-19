package dev.tugbaislyn.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.tugbaislyn.entity.Animal;
import dev.tugbaislyn.entity.Doctor;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class AppointmentRequest {
    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime appointmentDate;

    @NotNull
    private Doctor doctor;
    @NotNull
    private Animal animal;
}
