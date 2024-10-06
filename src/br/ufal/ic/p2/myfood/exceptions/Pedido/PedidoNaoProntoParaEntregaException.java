package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class PedidoNaoProntoParaEntregaException extends Exception {
    public PedidoNaoProntoParaEntregaException() {
        super("Pedido nao esta pronto para entrega");
    }
}
