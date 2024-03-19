package dev.tugbaislyn.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.tugbaislyn.entity.Animal;
import dev.tugbaislyn.entity.Appointment;
import dev.tugbaislyn.entity.Doctor;
import dev.tugbaislyn.entity.Vaccine;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {

    @NotNull
    private String title;

    @NotNull
    private String diagnosis;

    @NotNull
    private double price;

    @NotNull
    private Appointment appointment;
   // private List <Vaccine> VaccineList;




}
