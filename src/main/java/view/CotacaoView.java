package view;

import javax.swing.JOptionPane;

import controller.CotacaoController;

public class CotacaoView {

    public static void main(String[] args) {
        try {
            String moedas = JOptionPane.showInputDialog(
                    null,
                    "Insira as moedas desejadas separadas por vírgula (Exemplo: USD,BTC,EUR):",
                    "Conversor de Moedas",
                    JOptionPane.QUESTION_MESSAGE
            ).toUpperCase();

            String[] moedaArray = moedas.split(",");
            CotacaoController.buscarCotacoes(moedaArray[0], moedaArray[1]);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao buscar cotações: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }

    }
}    