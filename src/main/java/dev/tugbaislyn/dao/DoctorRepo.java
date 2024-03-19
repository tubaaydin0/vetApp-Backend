package dev.tugbaislyn.dao;

import dev.tugbaislyn.entity.Customer;
import dev.tugbaislyn.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Long> {
    List<Doctor> findByMailOrPhone(String mail, String phone);
}
