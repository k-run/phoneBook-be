package com.example.phonebook.Repository;

import com.example.phonebook.Model.PhoneBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneBookRepo extends JpaRepository<PhoneBook, String> {
    PhoneBook findByContactName(String name);
    PhoneBook findBymobileNumber(Long mobileNumber);
}
