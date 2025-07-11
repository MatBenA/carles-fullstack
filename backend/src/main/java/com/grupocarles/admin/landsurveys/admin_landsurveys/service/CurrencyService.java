package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Currency;

public interface CurrencyService {
    Currency createCurrency(Currency currency);

    List<Currency> getAllCurrencies();

    Currency updateCurrency(long id, Currency newCurrency);

    void deleteCurrency(long id);

    Currency getCurrencyByCode(String code);
}