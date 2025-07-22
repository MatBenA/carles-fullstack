package com.grupocarles.admin.landsurveys.admin_landsurveys.service.impl;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Contact;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.ContactRepository;

@RequiredArgsConstructor
@Service
public class ContactServiceImp implements ContactService {

    private final ContactRepository repository;

    @Override
    public Contact createContact(Contact contact) {
        return repository.save(contact);
    }

    @Override
    public void deleteContact(long id) {
        repository.deleteById(id);
        ;
    }

    @Override
    public List<SelectOptionDTO> getAllContactsAsOptions() {
        return repository.findAll().stream().map(contact -> new SelectOptionDTO(contact.getId().toString(), contact.getPhone())).toList();
    }

    @Override
    public Contact getContactById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Contact> getAllContacts() {
        return repository.findAll();
    }

    @Override
    public Contact updateContact(long id, Contact newContact) {
        Contact updateContact = repository.findById(id).orElse(null);

        updateContact.setPhone(newContact.getPhone());

        return repository.save(updateContact);
    }
}
