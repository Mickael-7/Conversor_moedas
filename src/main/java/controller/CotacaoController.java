package controller;

import javax.swing.JOptionPane;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONObject;

public class CotacaoController {
    private static final String API_URL = "https://economia.awesomeapi.com.br/json/last/";

    public static void buscarCotacoes(String moeda1, String moeda2, Double valor ) {
        try {
            URL url = new URL(API_URL + moeda1 + "-" + moeda2);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());


            JSONObject moedaData = jsonResponse.getJSONObject(moeda1 + moeda2);
            exibirCotacao(moeda1, moeda2, moedaData, valor);
        } catch (Exception e) {
            exibirErro("Erro ao obter cotações: " + e.getMessage());
        }
    }

    private static void exibirCotacao(String moeda1, String moeda2, JSONObject cotacaoMoedas, Double valor) {

        JOptionPane.showMessageDialog(null, "Cotação entre " + moeda1 + " e " + moeda2 + ": " + cotacaoMoedas.getDouble("bid") + " \nResultado da conversão: " + String.format("%.2f", valor * cotacaoMoedas.getDouble("bid")),  "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void exibirErro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
