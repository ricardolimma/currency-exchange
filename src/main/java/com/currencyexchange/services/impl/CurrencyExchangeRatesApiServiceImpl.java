package com.currencyexchange.services.impl;

import com.currencyexchange.exceptions.CurrencyApiException;
import com.currencyexchange.models.CurrencyModel;
import com.currencyexchange.models.OpenExchangeRatesApiRequest;
import com.currencyexchange.models.OpenExchangeRatesApiResponse;
import com.currencyexchange.repositories.CurrencyRepository;
import com.currencyexchange.services.ICurrencyOpenExchangeRatesApi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Objects;

@Service
public class CurrencyExchangeRatesApiServiceImpl implements ICurrencyOpenExchangeRatesApi {

    private static final Logger log = LogManager.getLogger(CurrencyExchangeRatesApiServiceImpl.class);
    private static final String APP_ID = "YOUR API KEY";

    private final WebClient webClient;
    private final CurrencyRepository currencyRepository;

    public CurrencyExchangeRatesApiServiceImpl(WebClient webClient, CurrencyRepository currencyRepository) {
        this.webClient = webClient;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public OpenExchangeRatesApiResponse fetchAndSaveCurrencies() throws CurrencyApiException {
        OpenExchangeRatesApiRequest request = new OpenExchangeRatesApiRequest();
        request.setApp_id(APP_ID);
        request.setBase("BRL");

        OpenExchangeRatesApiResponse response = fetchCurrenciesFromApi(request);

        if (response != null && response.getRates() != null) {
            saveCurrenciesToDatabase(response.getRates());
        }

        return response;
    }

    @Override
    public OpenExchangeRatesApiResponse fetchCurrenciesFromApi(OpenExchangeRatesApiRequest request) {
        String apiUrl = "latest.json";

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(apiUrl)
                        .queryParam("app_id", request.getApp_id())
                        .build())
                .retrieve()
                .bodyToMono(OpenExchangeRatesApiResponse.class).block();

    }

    @Override
    public void saveCurrenciesToDatabase(Map<String, Double> rates) {
        for (Map.Entry<String, Double> entry : rates.entrySet()) {
            CurrencyModel currency = new CurrencyModel();
            CurrencyModel currencyCode = currencyRepository.findByCurrencyCode(entry.getKey());

            if (Objects.nonNull(currencyCode) && currencyCode.getCurrencyCode().equals(entry.getKey())) {
                currencyCode.setExchangeRate(entry.getValue());
                currencyRepository.save(currencyCode);
            } else {

                currency.setCurrencyCode(entry.getKey());
                currency.setExchangeRate(entry.getValue());
                currencyRepository.save(currency);
            }

        }
    }

    @Scheduled(cron = "0 0 * * * *")
    public void fetchAndSaveCurrenciesApi() throws CurrencyApiException {
        fetchAndSaveCurrencies();
        log.info("Atualização das taxas de câmbio realizada com sucesso.");
    }
}