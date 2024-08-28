package br.ufal.ic.p2.myfood.exceptions.Produto;

public class ProdutoNaoEncontradoException extends Exception {
    public ProdutoNaoEncontradoException() {
        super("Produto nao encontrado");
    }
}
