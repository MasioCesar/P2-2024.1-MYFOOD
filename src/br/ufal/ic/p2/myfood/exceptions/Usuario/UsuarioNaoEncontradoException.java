package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class UsuarioNaoEncontradoException extends Exception {
    public UsuarioNaoEncontradoException() {
        super("Usuario nao encontrado");
    }
}