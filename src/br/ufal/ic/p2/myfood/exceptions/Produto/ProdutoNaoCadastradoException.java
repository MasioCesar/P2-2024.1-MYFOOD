package br.ufal.ic.p2.myfood.exceptions.Produto;

public class ProdutoNaoCadastradoException extends IllegalArgumentException {
    public ProdutoNaoCadastradoException() {
        super("Produto nao cadastrado");
    }
}