package com.grupocarles.admin.landsurveys.admin_landsurveys.controller;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Contact;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.ContactService;


@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService service;

    @PostMapping("/create")
    public Contact createContact(@RequestBody Contact contact) {
        return service.createContact(contact);
    }

    @GetMapping("/options")
    public List<SelectOptionDTO> getAllContactOptions() {
        return service.getAllContactsAsOptions();
    }

    @GetMapping
    public List<Contact> getAllContacts() {
        return service.getAllContacts();
    }

    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable long id) {
        return service.getContactById(id);
    }

    @PutMapping("/update/{id}")
    public Contact updateContact(@PathVariable long id, @RequestBody Contact newContact){
        return service.updateContact(id, newContact);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteContact(@PathVariable long id){
        service.deleteContact(id);
        return "Contact deleted successfully";
    }
}
