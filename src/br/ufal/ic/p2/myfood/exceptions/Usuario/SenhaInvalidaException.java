package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class SenhaInvalidaException extends IllegalArgumentException {
    public SenhaInvalidaException() {
        super("Senha invalido");
    }
}