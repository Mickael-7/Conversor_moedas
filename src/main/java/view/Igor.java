package view;

import controller.CotacaoController;

import javax.swing.*;
import java.awt.*;

public class Igor {
    public static void main(String[] args) {
        mostrarOpcoes();
    }

    private static void mostrarOpcoes() {
        String[] opcoes1 = {"USD", "BTC", "EUR", "ETH", "BRL"};
        String[] opcoes2 = {"USD", "BTC", "EUR", "ETH", "BRL"};


        JComboBox<String> comboBox1 = new JComboBox<>(opcoes1);
        JComboBox<String> comboBox2 = new JComboBox<>(opcoes2);


        Dimension comboBoxDimension = new Dimension(150, 30);
        comboBox1.setPreferredSize(comboBoxDimension);
        comboBox2.setPreferredSize(comboBoxDimension);


        JPanel panel = new JPanel();
        panel.setLayout(null);


        JLabel label1 = new JLabel("Converter de:");
        label1.setBounds(70, 100, 100, 90);
        comboBox1.setBounds(235, 120, 230, 60);
        JLabel label2 = new JLabel("Para:");
        label2.setBounds(70, 200, 100, 90);
        comboBox2.setBounds(235, 220, 230, 60);

        panel.add(label1);
        panel.add(comboBox1);
        panel.add(label2);
        panel.add(comboBox2);


        panel.setPreferredSize(new Dimension(600, 400));


        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog("Escolha suas opções");


        dialog.setPreferredSize(new Dimension(400, 200));

        dialog.setVisible(true);


        if ((Integer) optionPane.getValue() == JOptionPane.OK_OPTION) {
            String escolha1 = (String) comboBox1.getSelectedItem();
            String escolha2 = (String) comboBox2.getSelectedItem();
            mostrarEscolhas(escolha1, escolha2);
        }
    }


    private static void mostrarEscolhas(String escolha1, String escolha2) {
        try {

            String mensagem = "Escolhas selecionadas:\n" +
                    "Moeda 1: " + escolha1 + "\n" +
                    "Moeda 2: " + escolha2;
            JOptionPane.showMessageDialog(null, mensagem, "Suas Escolhas", JOptionPane.INFORMATION_MESSAGE);


            CotacaoController.buscarCotacoes(escolha1, escolha2);
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
