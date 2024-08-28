package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class NaoEPossivelAdcionarProdutosAUmPedidoFechado extends Exception{
    public NaoEPossivelAdcionarProdutosAUmPedidoFechado() {
        super("Nao e possivel adcionar produtos a um pedido fechado");
    }
}
