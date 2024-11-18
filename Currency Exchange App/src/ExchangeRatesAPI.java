import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRatesAPI {

    private static final String API_BASE_URL = "https://data.fixer.io/api/";
    private static final String API_ACCESS_KEY = "WvRwxPWxjVQ7gralDRkbpHUK2LuCUOl9";

    private static final Gson gson = new Gson();

    public static Map<String, BigDecimal> getExchangeRates(String baseCurrency, String targetCurrency) throws IOException {
        String urlString = API_BASE_URL + "latest" + "?access_key=" + API_ACCESS_KEY + "&base=" + baseCurrency;
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder responseBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }

        reader.close();
        connection.disconnect();

        ExchangeRatesApiResponse apiResponse = gson.fromJson(responseBuilder.toString(), ExchangeRatesApiResponse.class);
        BigDecimal targetRate = apiResponse.rates.get(targetCurrency);
        Map<String, BigDecimal> exchangeRates = new HashMap<>();
        exchangeRates.put(targetCurrency, targetRate);
        return exchangeRates;
    }

    static class ExchangeRatesApiResponse {
        Map<String, BigDecimal> rates;

    }

}