//Değerlendirme Formu 8
package dev.tugbaislyn.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
@Entity
@Table(name = "vaccine")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "protection_start_date")
    private LocalDate protectionStartDate;

    @NotNull
    @Column(name = "protection_finish_date")
    private LocalDate protectionFinishDate;


    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "animal_id")// Many olan yere animal one olduğundan animal_id yi vaccine entitysine ekleriz.
    private Animal animal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "report_id", nullable = true)
    private Report report;


}
