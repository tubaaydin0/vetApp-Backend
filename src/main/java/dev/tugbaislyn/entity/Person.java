package dev.tugbaislyn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@MappedSuperclass// Bu annotation, bir sınıfın diğer sınıflar tarafından genişletilebileceği,
// ancak doğrudan bir veritabanı tablosuna bağlanmayan bir üst sınıf olduğunu belirtmek için kullanılır.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", length = 100)
    private String name;

    @NotNull
    @Column(name = "phone", length = 11)
    private String phone;

    @NotNull
    @Column(name = "mail",length = 100)
    private String mail;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

}
