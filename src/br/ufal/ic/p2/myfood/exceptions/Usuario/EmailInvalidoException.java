package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class EmailInvalidoException extends IllegalArgumentException {
    public EmailInvalidoException() {
        super("Email invalido");
    }
}