package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class NaoPodeRemoverProdutoDePedidoFechadoException extends IllegalArgumentException {
    public NaoPodeRemoverProdutoDePedidoFechadoException() {
        super("Nao e possivel remover produtos de um pedido fechado");
    }
}