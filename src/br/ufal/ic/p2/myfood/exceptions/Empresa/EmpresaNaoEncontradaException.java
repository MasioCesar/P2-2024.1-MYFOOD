package br.ufal.ic.p2.myfood.exceptions.Empresa;

public class EmpresaNaoEncontradaException extends Exception {
    public EmpresaNaoEncontradaException() {
        super("Empresa nao encontrada");
    }
}
