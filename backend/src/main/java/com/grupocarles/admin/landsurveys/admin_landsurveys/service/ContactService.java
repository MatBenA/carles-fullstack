package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Contact;

public interface ContactService {
    Contact createContact(Contact contact);

    List<Contact> getAllContacts();

    Contact getContactById(long id);

    Contact updateContact(long id, Contact newContact);

    void deleteContact(long id);

    List<SelectOptionDTO> getAllContactsAsOptions();
}