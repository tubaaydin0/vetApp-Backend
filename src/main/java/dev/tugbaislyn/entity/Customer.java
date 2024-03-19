//Değerlendirme Formu 8
package dev.tugbaislyn.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer extends Person{
    //Değerlendirme Formu 9
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)//Animal da bir customer_id oluşacağı için mapleme işlemi yaptık. Müşteri silindiğinde ona ait tüm hayvan silinecek
    @JsonIgnore
    private List<Animal> animalList;
}
