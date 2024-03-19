package dev.tugbaislyn.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequest {
    @NotNull
    @Length(min = 2,max = 100)
    private String name;
    @NotNull
    @Length(min = 10,max = 11)
    private String phone;
    @Email
    @NotNull
    private String mail;
    private String address;
    private String city;
}
