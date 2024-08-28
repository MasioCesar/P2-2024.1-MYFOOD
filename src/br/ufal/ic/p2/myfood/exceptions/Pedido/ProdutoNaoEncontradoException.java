package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class ProdutoNaoEncontradoException extends IllegalArgumentException {
    public ProdutoNaoEncontradoException() {
        super("Produto nao encontrado");
    }
}