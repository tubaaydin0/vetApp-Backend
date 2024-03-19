package dev.tugbaislyn.dto.request;

import dev.tugbaislyn.entity.Customer;
import jakarta.validation.constraints.NotBlank;
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
public class AnimalRequest {
    @NotBlank
    @NotNull
    private  String name;
    private String species;
    private String breed;
    private String gender;
    private String colour;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    private Customer customer;
}
