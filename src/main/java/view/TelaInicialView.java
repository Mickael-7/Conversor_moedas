package view;

import model.Moedas;
import javax.swing.*;
import java.awt.*;

import static view.CotacaoView.inicializarOpcoesDeConversao;
import static view.CotacaoView.mostrarOpcoes;

public class TelaInicialView {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaInicialView::exibirTelaInicial);
    }

    public static void exibirTelaInicial() {
        JFrame frame = new JFrame("Conversor de Moedas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(criarPanel(frame));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel criarPanel(JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel boasVindasLabel = new JLabel("Bem-vindo ao Conversor de Moedas!");
        boasVindasLabel.setFont(new Font("Arial", Font.BOLD, 20));
        boasVindasLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextArea moedasTextArea = new JTextArea();
        moedasTextArea.setEditable(false);
        moedasTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        moedasTextArea.setLineWrap(true);
        moedasTextArea.setWrapStyleWord(true);
        moedasTextArea.setText("Moedas disponíveis:\n" + obterMoedasDisponiveis());

        panel.add(boasVindasLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(moedasTextArea), BorderLayout.CENTER);

        JButton abrirConversorButton = new JButton("Abrir Conversor");
        abrirConversorButton.setBackground(new Color(60, 179, 113));
        abrirConversorButton.addActionListener(e -> abrirConversor(frame));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(abrirConversorButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        return panel;
    }

    private static String obterMoedasDisponiveis() {
        StringBuilder moedas = new StringBuilder();
        Moedas[] opcoes = {
                Moedas.USD, Moedas.EUR, Moedas.BRL, Moedas.CAD, Moedas.JPY,
                Moedas.GBP, Moedas.ARS, Moedas.CNY, Moedas.RUB, Moedas.INR,
                Moedas.BTC, Moedas.ETH, Moedas.DOGE, Moedas.LTC, Moedas.XRP
        };
        for (Moedas moeda : opcoes) {
            moedas.append("- ").append(moeda).append("\n");
        }
        return moedas.toString();
    }

    private static void abrirConversor(JFrame frame) {
        frame.dispose();
        inicializarOpcoesDeConversao();
        mostrarOpcoes();
    }
}
