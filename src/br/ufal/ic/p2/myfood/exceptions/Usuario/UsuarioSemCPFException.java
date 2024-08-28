package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class UsuarioSemCPFException extends IllegalArgumentException {
    public UsuarioSemCPFException() {
        super("Usuario nao possui CPF");
    }
}