package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class NaoExisteNadaEntregueIdException extends Exception {
    public NaoExisteNadaEntregueIdException() {
        super("Nao existe nada para ser entregue com esse id");
    }
}
