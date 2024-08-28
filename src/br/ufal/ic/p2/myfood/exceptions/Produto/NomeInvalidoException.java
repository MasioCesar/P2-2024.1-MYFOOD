package br.ufal.ic.p2.myfood.exceptions.Produto;

public class NomeInvalidoException extends Exception {
    public NomeInvalidoException() {
        super("Nome invalido");
    }
}