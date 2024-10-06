package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class PedidoJaLiberadoException extends Exception {
    public PedidoJaLiberadoException() {
        super("Pedido ja liberado");
    }
}
