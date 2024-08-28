package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class SenhaInvalidaException extends Exception {
    public SenhaInvalidaException() {
        super("Senha invalido");
    }
}