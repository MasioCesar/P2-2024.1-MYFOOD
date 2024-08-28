package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class ProdutoInvalidoException extends IllegalArgumentException {
    public ProdutoInvalidoException() {
        super("Produto invalido");
    }
}