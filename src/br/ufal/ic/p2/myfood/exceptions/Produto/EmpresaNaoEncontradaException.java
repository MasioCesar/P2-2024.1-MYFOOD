package br.ufal.ic.p2.myfood.exceptions.Produto;

public class EmpresaNaoEncontradaException extends IllegalArgumentException {
    public EmpresaNaoEncontradaException() {
        super("Empresa nao encontrada");
    }
}