package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class EmpresaNaoEncontradaException extends IllegalArgumentException {
    public EmpresaNaoEncontradaException() {
        super("Empresa nao encontrada");
    }
}