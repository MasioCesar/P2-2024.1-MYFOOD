package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class EntregadorEmEntregaException extends Exception {
    public EntregadorEmEntregaException() {
        super("Entregador ainda em entrega");
    }
}
