package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class EntregadorNenhumaEmpresaException extends Exception {
    public EntregadorNenhumaEmpresaException() {
        super("Entregador nao estar em nenhuma empresa.");
    }
}
