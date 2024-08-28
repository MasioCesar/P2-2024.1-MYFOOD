package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class IndicePedidoInvalidoException extends IllegalArgumentException {
    public IndicePedidoInvalidoException() {
        super("Indice do pedido invalido");
    }
}