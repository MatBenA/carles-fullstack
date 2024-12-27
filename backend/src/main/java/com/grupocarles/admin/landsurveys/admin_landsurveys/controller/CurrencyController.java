package com.grupocarles.admin.landsurveys.admin_landsurveys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Currency;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.CurrencyService;


@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyService service;

    @PostMapping("/create")
    public Currency createCurrency(@RequestBody Currency currency) {
        return service.createCurrency(currency);
    }

    @GetMapping
    public List<Currency> getAllCurrencies() {
        return service.getAllCurrencies();
    }

    @GetMapping("/{id}")
    public Currency getCurrencyById(@PathVariable long id) {
        return service.getCurrencyById(id);
    }

    @PutMapping("/update/{id}")
    public Currency updateCurrency(@PathVariable long id, @RequestBody Currency newCurrency){
        return service.updateCurrency(id, newCurrency);
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteCurrency(@PathVariable long id){
        service.deleteCurrency(id);
        return "Currency deleted successfully";
    }
}
