package controller;

import exceptions.CotacaoException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CotacaoController {
    private static final String API_URL = "https://economia.awesomeapi.com.br/json/last/";

    public static double buscarCotacoes(String moeda1, String moeda2, Double valor) throws CotacaoException {
        try {
            JSONObject moedaData = obterMoedaData(moeda1, moeda2);
            if (moedaData != null && moedaData.has("bid")) {
                return moedaData.getDouble("bid") * valor;
            } else {
                throw new CotacaoException("As informações da cotação estão incompletas ou indisponíveis.");
            }
        } catch (CotacaoException e) {
            throw new CotacaoException("Erro ao buscar cotações: " + e.getMessage());
        }
    }

    private static JSONObject obterMoedaData(String moeda1, String moeda2) throws CotacaoException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(API_URL + moeda1 + "-" + moeda2);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return new JSONObject(response.toString()).optJSONObject(moeda1 + moeda2);
            } else {
                throw new CotacaoException("Erro na requisição à API: Código " + responseCode);
            }
        } catch (Exception e) {
            throw new CotacaoException("Erro na requisição à API: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
