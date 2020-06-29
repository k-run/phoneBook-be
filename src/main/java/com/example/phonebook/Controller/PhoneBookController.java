package com.example.phonebook.Controller;


import com.example.phonebook.Exception.ResourceNotFoundException;
import com.example.phonebook.Model.PhoneBook;
import com.example.phonebook.Repository.PhoneBookRepo;
import com.example.phonebook.Response.SuccessResponse;
import lombok.Lombok;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class PhoneBookController {

    @Autowired
    private PhoneBookRepo phoneBookRepo;

    // create api
    // inserts into db one contact
    @PostMapping("/create")
    public ResponseEntity<?> createContact(@RequestBody PhoneBook phoneBook) {
        System.out.println("contact email:" + phoneBook.getContactEmail());
        System.out.println("contact name:" + phoneBook.getContactName());
        System.out.println("number: " + phoneBook.getMobileNumber());

        PhoneBook savedContact = phoneBookRepo.save(phoneBook);
        return ResponseEntity.status(200).body(savedContact);
    }

    // read api
    // gets the contact by name or by number
    @GetMapping("/read")
    public ResponseEntity getContact(@RequestParam(required = false) String name, @RequestParam(required = false) String id,
                                     @RequestParam(required = false) Long number) throws ResourceNotFoundException {
       PhoneBook sample = new PhoneBook();
       PhoneBook result;

       if(id != null) {
           result = phoneBookRepo.findById(id).get();
       }

       else {
           if (name != null) {
               sample.setContactName(name);
               result = phoneBookRepo.findByContactName(name);
           } else {
               sample.setMobileNumber(number);
               result = phoneBookRepo.findBymobileNumber(number);
           }
       }
       if(result == null) throw new ResourceNotFoundException("The contact you're trying to read does not exists!");
       return ResponseEntity.status(200).body(result);
    }

    // update api
    // updates the contact

    @PutMapping("/update/{id}")
    public ResponseEntity updateContact(@PathVariable(value = "id") String id, @RequestBody PhoneBook newDetails)
            throws ResourceNotFoundException
    {
        PhoneBook existingContact = phoneBookRepo.findById(id).get();
        if(existingContact == null) {
            throw new ResourceNotFoundException("The contact you're trying to update does not exists!");
        }

        else {
            existingContact.setMobileNumber(newDetails.getMobileNumber());
            existingContact.setContactName(newDetails.getContactName());
            existingContact.setContactEmail(newDetails.getContactEmail());
            existingContact.setNickName(newDetails.getNickName());

            return ResponseEntity.status(HttpStatus.CREATED).body(phoneBookRepo.save(existingContact));
        }
    }

    // deletes the contact by id
    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deleteContact(@PathVariable(value = "id") String id) throws ResourceNotFoundException
    {
        HashMap<String, String> map = new HashMap<>();

        if(id != null) {
            PhoneBook existingContact = phoneBookRepo.findById(id).get();

            phoneBookRepo.delete(existingContact);

            map.put("status", "success");
            map.put("message", "Contact deleted successfully");
            return map;
        }

        else {
            map.put("status", "error");
            map.put("message", "Invalid id");
            return map;
        }
    }

    // gets all the contacts
    @GetMapping("/getAll")
    public List<PhoneBook> getAllContacts() {
        return phoneBookRepo.findAll();
    }
}
