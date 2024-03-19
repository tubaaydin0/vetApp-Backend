package dev.tugbaislyn.dao;

import dev.tugbaislyn.entity.Animal;
import dev.tugbaislyn.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepo extends JpaRepository<Animal,Long> {
   // Optional<Animal> findByNameAndSpeciesAndDateOfBirth(String name, String species, LocalDate dateOfBirth);
    Optional<Animal> findByNameAndSpeciesAndBreedAndGenderAndColourAndDateOfBirthAndCustomerId(String name, String species,String Breed,String Gender, String colour,LocalDate dateOfBirth, Long customerId);
    //Hayvan ismine göre listeleme
    @Query("FROM Animal WHERE name LIKE %:name%")
    List<Animal> findByName(@Param("name") String name);
    //Müşterinin hayvanlarını listeleme
    List<Animal> findByCustomerId(Long customerId);

 @Query("FROM Animal a JOIN a.customer c WHERE c.name LIKE %:customerName%")
 List<Animal> findByCustomerName(@Param("customerName") String customerName);


}
