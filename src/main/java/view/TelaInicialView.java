package view;

import model.Moedas;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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
        frame.setContentPane(criarPanel(frame));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel criarPanel(JFrame frame) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = new ImageIcon("background.jpg").getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setLayout(new BorderLayout());

        JLabel boasVindasLabel = new JLabel("Bem-vindo ao Conversor de Moedas!");
        boasVindasLabel.setForeground(Color.WHITE);
        boasVindasLabel.setFont(new Font("Arial", Font.BOLD, 24));
        boasVindasLabel.setHorizontalAlignment(SwingConstants.CENTER);
        boasVindasLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        panel.add(boasVindasLabel, BorderLayout.NORTH);

        JTextArea moedasTextArea = new JTextArea();
        moedasTextArea.setEditable(false);
        moedasTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        moedasTextArea.setLineWrap(true);
        moedasTextArea.setWrapStyleWord(true);
        moedasTextArea.setText("Moedas disponíveis:\n" +
                "\n" + obterMoedasDisponiveis());
        moedasTextArea.setBorder(new EmptyBorder(10, 20, 10, 20));

        panel.add(new JScrollPane(moedasTextArea), BorderLayout.CENTER);

        JButton abrirConversorButton = new JButton("Abrir Conversor");
        abrirConversorButton.setFont(new Font("Arial", Font.BOLD, 16));
        abrirConversorButton.setBackground(new Color(60, 179, 113));
        abrirConversorButton.setForeground(Color.WHITE);
        abrirConversorButton.setBorderPainted(false);
        abrirConversorButton.setFocusPainted(false);
        abrirConversorButton.setPreferredSize(new Dimension(200, 40));
        abrirConversorButton.addActionListener(e -> abrirConversor(frame));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(abrirConversorButton);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        panel.setBorder(new LineBorder(new Color(150, 150, 150), 2));

        return panel;
    }

    private static String obterMoedasDisponiveis() {
        StringBuilder moedas = new StringBuilder();
        Moedas[] opcoes = {
                Moedas.USD, Moedas.EUR, Moedas.BRL, Moedas.CAD, Moedas.JPY,
                Moedas.GBP, Moedas.ARS, Moedas.CNY, Moedas.RUB, Moedas.INR,
                Moedas.BTC, Moedas.ETH, Moedas.DOGE, Moedas.LTC, Moedas.XRP
        };
        for (Moedas moeda : opcoes ) {
            moedas.append(" ").append(moeda).append(" - ").append(moeda.getDescricao()).append("\n");
        }
        return moedas.toString();
    }

    private static void abrirConversor(JFrame frame) {
        frame.dispose();
        inicializarOpcoesDeConversao();
        mostrarOpcoes();
    }
}
