package com.currencyexchange.services.impl;

import com.currencyexchange.exceptions.CurrencyApiException;
import com.currencyexchange.models.CurrencyModel;
import com.currencyexchange.models.OpenExchangeRatesApiRequest;
import com.currencyexchange.models.OpenExchangeRatesApiResponse;
import com.currencyexchange.repositories.CurrencyRepository;
import com.currencyexchange.services.ICurrencyOpenExchangeRatesApi;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class CurrencyExchangeRatesApiServiceImpl implements ICurrencyOpenExchangeRatesApi {
    private static final String API_BASE_URL =  "https://openexchangerates.org/api/";
    private static final String APP_ID =  ".";

    private final WebClient webClient;
    private final CurrencyRepository currencyRepository;

    public CurrencyExchangeRatesApiServiceImpl(WebClient webClient, CurrencyRepository currencyRepository) {
        this.webClient = webClient;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public OpenExchangeRatesApiResponse fetchAndSaveCurrencies() throws CurrencyApiException {
        OpenExchangeRatesApiRequest request = new OpenExchangeRatesApiRequest();
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
                        .queryParam("base", request.getBase())
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Token " + APP_ID)
                .retrieve()
                .bodyToMono(OpenExchangeRatesApiResponse.class).block();

    }
   @Override
    public void saveCurrenciesToDatabase(Map<String, Double> rates) {
        for (Map.Entry<String, Double> entry : rates.entrySet()) {
            CurrencyModel currency = new CurrencyModel();
            currency.setCurrencyCode(entry.getKey());
            currency.setExchangeRate(entry.getValue());
            currencyRepository.save(currency);
        }
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void fetchAndSaveCurrenciesApi() throws CurrencyApiException {
        fetchAndSaveCurrencies();
    }

}