package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class PedidoEmAbertoException extends IllegalArgumentException {
    public PedidoEmAbertoException() {
        super("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
    }
}