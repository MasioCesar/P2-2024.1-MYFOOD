package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class PedidoNaoEncontradoException extends Exception {
    public PedidoNaoEncontradoException() {
        super("Pedido nao encontrado");
    }
}