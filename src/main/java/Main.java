import exceptions.DomainException;

import static view.CotacaoView.inicializarOpcoesDeConversao;
import static view.CotacaoView.mostrarOpcoes;

public class Main {
    public static void main(String[] args) throws DomainException {
        inicializarOpcoesDeConversao();
        mostrarOpcoes();
    }
}
 