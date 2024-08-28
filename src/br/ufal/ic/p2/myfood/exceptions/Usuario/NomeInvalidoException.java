package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class NomeInvalidoException extends IllegalArgumentException {
    public NomeInvalidoException() {
        super("Nome invalido");
    }
}