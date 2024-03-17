package view;

import controller.CotacaoController;
import model.Moedas;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class CotacaoView {
    private static JComboBox<Moedas> comboBox1;
    private static JComboBox<Moedas> comboBox2;
    private static JTextField textField;
    private static Map<Moedas, List<Moedas>> opcoesDeConversao;

    public static void inicializarOpcoesDeConversao() {
        opcoesDeConversao = new HashMap<>();
        opcoesDeConversao.put(Moedas.USD, Arrays.asList(Moedas.BRL, Moedas.EUR, Moedas.JPY, Moedas.CAD));
        opcoesDeConversao.put(Moedas.BTC, Arrays.asList(Moedas.USD, Moedas.EUR, Moedas.BRL));
        opcoesDeConversao.put(Moedas.EUR, Arrays.asList(Moedas.EUR, Moedas.XRP, Moedas.BTC, Moedas.ETH, Moedas.BRL));
        opcoesDeConversao.put(Moedas.ETH, Arrays.asList(Moedas.USD, Moedas.EUR, Moedas.BRL));
        opcoesDeConversao.put(Moedas.BRL, Arrays.asList(Moedas.USD, Moedas.EUR, Moedas.CAD, Moedas.JPY));
        opcoesDeConversao.put(Moedas.CAD, Arrays.asList(Moedas.USD, Moedas.EUR, Moedas.BRL));
        opcoesDeConversao.put(Moedas.JPY, Arrays.asList(Moedas.BRL, Moedas.USD, Moedas.EUR));
    }

    public static void mostrarOpcoes() {
        inicializarOpcoesDeConversao();
        JPanel panel = configurarInterface();
        exibirDialogo(panel);
    }

    private static @NotNull JPanel configurarInterface() {
        Moedas[] opcoes1 = {Moedas.USD, Moedas.BTC, Moedas.EUR, Moedas.ETH, Moedas.BRL, Moedas.CAD, Moedas.JPY};
        Moedas[] opcoes2 = {};

        comboBox1 = new JComboBox<>(opcoes1);
        comboBox2 = new JComboBox<>(opcoes2);
        textField = new JTextField();

        JButton convertButton = new JButton("Converter");

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel label1 = new JLabel("Converter de:");
        JLabel label2 = new JLabel("Para:");
        JLabel label3 = new JLabel("Digite o valor:");

        Dimension textFieldDimension = new Dimension(150, 30);
        textField.setPreferredSize(textFieldDimension);

        formPanel.add(label1);
        formPanel.add(comboBox1);
        formPanel.add(label2);
        formPanel.add(comboBox2);
        formPanel.add(label3);
        formPanel.add(textField);

        buttonPanel.add(convertButton);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarOpcoesComboBox2();
            }
        });
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processarSelecao();
            }
        });

        return panel;
    }

    private static void exibirDialogo(JPanel panel) {
        JFrame frame = new JFrame("Escolha suas opções");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void processarSelecao() {
        Moedas escolha1 = (Moedas) comboBox1.getSelectedItem();
        Moedas escolha2 = (Moedas) comboBox2.getSelectedItem();
        String valorTexto = textField.getText().trim();
        if (!valorTexto.isEmpty()) {
            try {
                double valor = Double.parseDouble(valorTexto);
                mostrarEscolhas(escolha1, escolha2, valor);
            } catch (NumberFormatException ex) {
                exibirMensagemErro("Digite um valor numérico válido.");
            }
        } else {
            exibirMensagemErro("Digite um valor para continuar.");
        }
    }

    private static void exibirMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
        mostrarOpcoes();
    }

    private static void atualizarOpcoesComboBox2() {
        Moedas escolha1 = (Moedas) comboBox1.getSelectedItem();
        Moedas[] opcoes2 = obterOpcoes2(escolha1);
        comboBox2.setModel(new DefaultComboBoxModel<>(opcoes2));
    }

    private static Moedas[] obterOpcoes2(Moedas escolha1) {
        List<Moedas> opcoes2 = opcoesDeConversao.getOrDefault(escolha1, Collections.emptyList());
        return opcoes2.toArray(new Moedas[0]);
    }

    private static void mostrarEscolhas(Moedas escolha1, Moedas escolha2, double valor) {
        try {
            CotacaoController.buscarCotacoes(escolha1.toString(), escolha2.toString(), valor);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar cotações: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
