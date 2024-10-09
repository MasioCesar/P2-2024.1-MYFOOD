package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class UsuarioNaoEntregadorException extends Exception {
    public UsuarioNaoEntregadorException() {
        super("Usuario nao e um entregador");
    }
}
