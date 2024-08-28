package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class NaoPodeRemoverProdutoDePedidoFechadoException extends Exception {
    public NaoPodeRemoverProdutoDePedidoFechadoException() {
        super("Nao e possivel remover produtos de um pedido fechado");
    }
}