package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class NaoExistePedidoEmAberto extends Exception {
    public NaoExistePedidoEmAberto() {
        super("Nao existe pedido em aberto");
    }
}
