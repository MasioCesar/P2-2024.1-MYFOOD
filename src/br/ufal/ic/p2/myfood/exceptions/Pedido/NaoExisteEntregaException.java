package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class NaoExisteEntregaException extends Exception {
    public NaoExisteEntregaException() {
        super("Nao existe entrega com esse id");
    }
}
