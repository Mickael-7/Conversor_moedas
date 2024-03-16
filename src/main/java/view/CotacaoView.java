package view;

import controller.CotacaoController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class CotacaoView {
    private static JComboBox<String> comboBox1;
    private static JComboBox<String> comboBox2;
    private static Map<String, List<String>> opcoesDeConversao;

    public static void main(String[] args) {
        inicializarOpcoesDeConversao();
        mostrarOpcoes();
    }

    private static void inicializarOpcoesDeConversao() {
        opcoesDeConversao = new HashMap<>();
        opcoesDeConversao.put("USD", Arrays.asList("EUR", "XRP", "BTC", "ETH", "BRL"));
        opcoesDeConversao.put("BTC", Arrays.asList("USD", "EUR", "BRL"));
        opcoesDeConversao.put("EUR", Arrays.asList("USD", "XRP", "ETH", "BRL"));
        opcoesDeConversao.put("ETH", Arrays.asList("USD", "EUR", "BRL"));
        opcoesDeConversao.put("BRL", Arrays.asList("USD", "EUR", "BTC", "ETH"));
        opcoesDeConversao.put("CAD", Arrays.asList("USD","EUR", "BRL"));
        opcoesDeConversao.put("JPY", Arrays.asList("BRL", "USD", "EUR"));

    }

    private static void mostrarOpcoes() {
        String[] opcoes1 = {"USD", "BTC", "EUR", "ETH", "BRL", "CAD", "JPY"};
        String[] opcoes2 = {""};

        comboBox1 = new JComboBox<>(opcoes1);
        comboBox2 = new JComboBox<>(opcoes2);

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

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarOpcoesComboBox2();
            }
        });

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

    private static void atualizarOpcoesComboBox2() {
        String escolha1 = (String) comboBox1.getSelectedItem();
        String[] opcoes2 = obterOpcoes2BaseadoEm(escolha1);
        comboBox2.setModel(new DefaultComboBoxModel<>(opcoes2));
    }

    private static String[] obterOpcoes2BaseadoEm(String escolha1) {
        List<String> opcoes2 = opcoesDeConversao.get(escolha1);
        if (opcoes2 == null) {
            opcoes2 = Collections.emptyList();
        }
        return opcoes2.toArray(new String[0]);
    }

    private static void mostrarEscolhas(String escolha1, String escolha2) {
        try {
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
