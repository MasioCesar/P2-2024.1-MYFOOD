package br.ufal.ic.p2.myfood.exceptions.Produto;

public class ProdutoJaExisteException extends Exception {
    public ProdutoJaExisteException() {
        super("Ja existe um produto com esse nome para essa empresa");
    }
}