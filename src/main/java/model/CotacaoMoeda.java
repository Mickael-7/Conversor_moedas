package model;

import org.json.JSONObject;

public class CotacaoMoeda {
    private double preco;

    public CotacaoMoeda(double preco) {
        this.preco = preco;
    }

    public double getPreco() {
        return preco;
    }

    @org.jetbrains.annotations.NotNull
    public static CotacaoMoeda parseFromJSON(JSONObject moedaData) {
        double preco = moedaData.getDouble("bid");
        return new CotacaoMoeda(preco);
    }
}
