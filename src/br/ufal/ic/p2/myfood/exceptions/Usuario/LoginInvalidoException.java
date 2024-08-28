package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class LoginInvalidoException extends Exception {
    public LoginInvalidoException() {
        super("Login ou senha invalidos");
    }
}