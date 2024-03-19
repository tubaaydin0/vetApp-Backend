//Değerlendirme Formu 8
package dev.tugbaislyn.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "doctor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Doctor extends Person {
    //Değerlendirme Formu 9
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE )
    @JsonIgnore
    List<AvailableDate> availableDateList;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE )
    @JsonIgnore
    List<Appointment> appointmentList;

}
