package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class UsuarioNaoCadastradoException extends Exception {
    public UsuarioNaoCadastradoException() {
        super("Usuario nao cadastrado.");
    }
}