//Değerlendirme Formu 8

package dev.tugbaislyn.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Entity
@Table(name = "available_date")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AvailableDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "available_date")
    private LocalDate availableDate;

    //Değerlendirme Formu 9
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}
