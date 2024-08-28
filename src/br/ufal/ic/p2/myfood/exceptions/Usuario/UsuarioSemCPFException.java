package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class UsuarioSemCPFException extends Exception {
    public UsuarioSemCPFException() {
        super("Usuario nao possui CPF");
    }
}