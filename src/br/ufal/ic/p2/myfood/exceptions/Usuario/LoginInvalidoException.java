package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class LoginInvalidoException extends IllegalArgumentException {
    public LoginInvalidoException() {
        super("Login ou senha invalidos");
    }
}