package dev.tugbaislyn.dto.request;

import dev.tugbaislyn.entity.Animal;
import dev.tugbaislyn.entity.Report;
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
public class VaccineRequest {
    @NotNull
    private String name;
    @NotNull
    private String code;

    @NotNull
    private LocalDate protectionStartDate;

    @NotNull
    private LocalDate protectionFinishDate;


    private Animal animal;


    private Report report;
}
