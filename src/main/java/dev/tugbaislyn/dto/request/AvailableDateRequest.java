package dev.tugbaislyn.dto.request;

import dev.tugbaislyn.entity.Doctor;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateRequest {
    @NotNull
    @FutureOrPresent
    private LocalDate availableDate;

    @NotNull
    private Doctor doctor;
}
