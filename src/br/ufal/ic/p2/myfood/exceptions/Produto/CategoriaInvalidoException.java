package br.ufal.ic.p2.myfood.exceptions.Produto;

public class CategoriaInvalidoException extends IllegalArgumentException {
    public CategoriaInvalidoException() {
        super("Categoria invalido");
    }
}