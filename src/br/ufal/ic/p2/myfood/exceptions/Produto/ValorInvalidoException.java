package br.ufal.ic.p2.myfood.exceptions.Produto;

public class ValorInvalidoException extends IllegalArgumentException {
    public ValorInvalidoException() {
        super("Valor invalido");
    }
}