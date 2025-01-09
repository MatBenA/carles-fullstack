package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.ExchangeRateResponseDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Currency;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.CurrencyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExchangeRateService {
    @Autowired
    private WebClient webClient;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Value("${exchange.api.url}")
    private String apiUrl;

    public Mono<Double> getBlueDollarRate() {
        return webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(ExchangeRateResponseDTO.class)
                .map(response -> response.blue().value_sell());
    }

    @Scheduled(fixedRate = 3600000) //this is milliseconds
    public void scheduledExchangeRate(){
        getBlueDollarRate()
                .subscribe(rate -> {
                    Currency dolarCurrency = currencyRepository
                            .findByCode("USD")
                            .orElseThrow(EntityNotFoundException::new);
                    dolarCurrency.setExchangeReference(rate);
                    currencyRepository.save(dolarCurrency);
                });
    }
}
