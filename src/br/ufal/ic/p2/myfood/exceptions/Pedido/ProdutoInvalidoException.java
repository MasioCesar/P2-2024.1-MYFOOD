package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class ProdutoInvalidoException extends Exception {
    public ProdutoInvalidoException() {
        super("Produto invalido");
    }
}