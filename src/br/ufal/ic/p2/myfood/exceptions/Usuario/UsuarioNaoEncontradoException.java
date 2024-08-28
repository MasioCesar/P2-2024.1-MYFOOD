package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class UsuarioNaoEncontradoException extends IllegalArgumentException {
    public UsuarioNaoEncontradoException() {
        super("Usuario nao encontrado");
    }
}