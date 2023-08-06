package com.currencyexchange.services;

import com.currencyexchange.exceptions.CurrencyApiException;
import com.currencyexchange.models.OpenExchangeRatesApiRequest;
import com.currencyexchange.models.OpenExchangeRatesApiResponse;

import java.util.Map;

/**
 * Interface to interact with the Open Exchange Rates API for obtaining currency exchange rate data.
 */
public interface ICurrencyOpenExchangeRatesApi {
    /**
     * Fetches the latest currency exchange rates from the Open Exchange Rates API and saves them to the database.
     *
     * @return The response containing the latest currency exchange rates and metadata.
     * @throws CurrencyApiException If there's an error while fetching data from the API or saving to the database.
     */
    /**
     * Faz uma chamada à API OpenExchangeRates para buscar as informações das moedas e as salva no banco de dados.
     *
     * @return Um objeto OpenExchangeRatesApiResponse contendo a resposta da API e o status da operação.
     * @throws CurrencyApiException Se ocorrer algum erro durante a chamada à API ou ao salvar os dados no banco de dados.
     */
    OpenExchangeRatesApiResponse fetchAndSaveCurrencies() throws CurrencyApiException;

    /**
     * Salva as informações das moedas no banco de dados.
     *
     * @param rates Um mapa contendo os códigos das moedas como chaves e as taxas de câmbio como valores.
     */
    void saveCurrenciesToDatabase(Map<String, Double> rates);

    /**
     * Faz uma chamada à API OpenExchangeRates para buscar as informações das moedas com base no objeto OpenExchangeRatesApiRequest fornecido.
     *
     * @param request O objeto OpenExchangeRatesApiRequest contendo os parâmetros da chamada à API.
     * @return Um objeto OpenExchangeRatesApiResponse contendo a resposta da API.
     */
    OpenExchangeRatesApiResponse fetchCurrenciesFromApi(OpenExchangeRatesApiRequest request);


}
