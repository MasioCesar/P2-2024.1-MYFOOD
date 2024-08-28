package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class UsuarioNaoCadastradoException extends IllegalArgumentException {
    public UsuarioNaoCadastradoException() {
        super("Usuario nao cadastrado.");
    }
}