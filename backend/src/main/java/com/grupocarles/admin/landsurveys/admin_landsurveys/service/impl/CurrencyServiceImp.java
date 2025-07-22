package com.grupocarles.admin.landsurveys.admin_landsurveys.service.impl;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.service.CurrencyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Currency;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.CurrencyRepository;

@Service
public class CurrencyServiceImp implements CurrencyService {

    @Autowired
    private CurrencyRepository repository;

    @Override
    public Currency createCurrency(Currency currency) {
        return repository.save(currency);
    }

    @Override
    public void deleteCurrency(long id) {
        repository.deleteById(id);
    }

    @Override
    public Currency getCurrencyByCode(String code) {
        return repository
                .findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found for code: " + code));
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return repository.findAll();
    }

    @Override
    public Currency updateCurrency(long id, Currency newCurrency) {
        Currency updateCurrency = repository.findById(id).orElse(null);

        updateCurrency.setCode(newCurrency.getCode());
        updateCurrency.setExchangeReference(newCurrency.getExchangeReference());

        return repository.save(updateCurrency);
    }
}
