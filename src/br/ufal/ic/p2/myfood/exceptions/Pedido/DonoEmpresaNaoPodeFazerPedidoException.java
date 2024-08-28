package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class DonoEmpresaNaoPodeFazerPedidoException extends Exception {
    public DonoEmpresaNaoPodeFazerPedidoException() {
        super("Dono de empresa nao pode fazer um pedido");
    }
}