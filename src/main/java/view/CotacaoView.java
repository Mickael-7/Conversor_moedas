package view;

import controller.CotacaoController;
import controller.DomainException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class CotacaoView {
    private static JComboBox<String> comboBox1;
    private static JComboBox<String> comboBox2;
    private static JTextField textField;
    private static Map<String, List<String>> opcoesDeConversao;

    public static void inicializarOpcoesDeConversao() {
        opcoesDeConversao = new HashMap<>();
        opcoesDeConversao.put("USD", Arrays.asList("BRL", "EUR", "JPY", "CAD"));
        opcoesDeConversao.put("BTC", Arrays.asList("USD", "EUR", "BRL"));
        opcoesDeConversao.put("EUR", Arrays.asList("EUR", "XRP", "BTC", "ETH", "BRL"));
        opcoesDeConversao.put("ETH", Arrays.asList("USD", "EUR", "BRL"));
        opcoesDeConversao.put("BRL", Arrays.asList("USD", "EUR", "CAD", "JPY"));
        opcoesDeConversao.put("CAD", Arrays.asList("USD", "EUR", "BRL"));
        opcoesDeConversao.put("JPY", Arrays.asList("BRL", "USD", "EUR"));
    }

    public static void mostrarOpcoes() {
        inicializarOpcoesDeConversao();

        String[] opcoes1 = {"USD", "BTC", "EUR", "ETH", "BRL", "CAD", "JPY"};
        String[] opcoes2 = {""};

        comboBox1 = new JComboBox<>(opcoes1);
        comboBox2 = new JComboBox<>(opcoes2);
        textField = new JTextField();

        Dimension comboBoxDimension = new Dimension(150, 30);
        comboBox1.setPreferredSize(comboBoxDimension);
        comboBox2.setPreferredSize(comboBoxDimension);
        textField.setPreferredSize(comboBoxDimension);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel label1 = new JLabel("Converter de:");
        label1.setBounds(70, 100, 100, 90);
        comboBox1.setBounds(235, 120, 230, 60);
        JLabel label2 = new JLabel("Para:");
        label2.setBounds(70, 200, 100, 90);
        comboBox2.setBounds(235, 220, 230, 60);
        JLabel label3 = new JLabel("Digite o valor:");
        label3.setBounds(70, 290, 100, 90);
        textField.setBounds(235, 320, 230, 30);

        panel.add(label1);
        panel.add(comboBox1);
        panel.add(label2);
        panel.add(comboBox2);
        panel.add(label3);
        panel.add(textField);

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
            String valorTexto = textField.getText().trim();
            if (!valorTexto.isEmpty()) {
                try {
                    double valor = Double.parseDouble(valorTexto);
                    mostrarEscolhas(escolha1, escolha2, valor);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Digite um valor numérico válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    mostrarOpcoes();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Digite um valor para continuar.", "Erro", JOptionPane.ERROR_MESSAGE);
                mostrarOpcoes();
            }
        }
    }

    private static void atualizarOpcoesComboBox2() {
        String escolha1 = (String) comboBox1.getSelectedItem();
        String[] opcoes2 = obterOpcoes2BaseadoEm(escolha1);
        comboBox2.setModel(new DefaultComboBoxModel<>(opcoes2));
    }

    private static String[] obterOpcoes2BaseadoEm(String escolha1) {
        List<String> opcoes2 = opcoesDeConversao.getOrDefault(escolha1, Collections.emptyList());
        return opcoes2.toArray(new String[0]);
    }

    private static void mostrarEscolhas(String escolha1, String escolha2, double valor) {
        try {
            CotacaoController.buscarCotacoes(escolha1, escolha2, valor);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar cotações: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
