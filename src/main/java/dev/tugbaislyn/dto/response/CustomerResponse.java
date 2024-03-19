package dev.tugbaislyn.dto.response;

import dev.tugbaislyn.entity.Animal;
import jakarta.validation.constraints.Email;
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
public class CustomerResponse {
    private  Long id;
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
}
