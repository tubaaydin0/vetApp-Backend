package dev.tugbaislyn.dto.response;
import dev.tugbaislyn.entity.Appointment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    private  Long id;
    //private LocalDateTime appointmentDate;
    private Appointment appointment;
    private String title;
    private String diagnosis;
    private double price;
    private  String animalName;
    private String doctorName;
    private String customerName;
    //private List <Vaccine> VaccineList;




}
