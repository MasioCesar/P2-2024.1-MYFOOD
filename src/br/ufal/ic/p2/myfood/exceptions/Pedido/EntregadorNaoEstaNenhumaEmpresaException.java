package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class EntregadorNaoEstaNenhumaEmpresaException extends Exception {
    public EntregadorNaoEstaNenhumaEmpresaException() {
        super("Entregador nao estar em nenhuma empresa.");
    }
}
