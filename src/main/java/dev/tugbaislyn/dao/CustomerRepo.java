package dev.tugbaislyn.dao;

import dev.tugbaislyn.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {

    List<Customer>  findByMailOrPhone(String mail,String phone);
    //Optional<Customer> findByMailOrPhone(String mail,String phone);

    //İsme göre filtreleme
    @Query("FROM Customer WHERE name LIKE %:name%")
    List<Customer> findByName(@Param("name") String name);
}
