package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class NaoEntregadorValidoException extends Exception {
    public NaoEntregadorValidoException() {
        super("Nao e um entregador valido");
    }
}
