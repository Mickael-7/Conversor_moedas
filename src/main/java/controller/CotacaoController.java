package controller;

import javax.swing.JOptionPane;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONObject;

public class CotacaoController {
    private static final String API_URL = "https://economia.awesomeapi.com.br/json/last/";

    public static double buscarCotacoes(String moeda1, String moeda2, Double valor ) {
            JSONObject moedaData = null;
        try {
            URL url = new URL(API_URL + moeda1 + "-" + moeda2);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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

                JSONObject jsonResponse = new JSONObject(response.toString());
                 moedaData =jsonResponse.getJSONObject(moeda1 + moeda2);
              
            } else {
                exibirErro("Erro na requisição à API: Código " + responseCode);
            }
        } catch (Exception e) {
            exibirErro("Erro ao obter cotações: " + e.getMessage());
        }
        return moedaData.getDouble("bid");
    }


    private static void exibirErro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
