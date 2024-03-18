package model;


public enum Moedas {

    USD("Dólar Americano"),
    BTC("Bitcoin"),
    EUR("Euro"),
    ETH("Ethereum"),
    BRL("Real Brasileiro"),
    CAD("Dólar Canadense"),
    XRP("XRP"),
    JPY("Iene Japonês");

    private final String descricao;

    Moedas(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}


