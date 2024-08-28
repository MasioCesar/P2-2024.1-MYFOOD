package br.ufal.ic.p2.myfood.exceptions.Empresa;

public class EmpresaNaoCadastradaException extends Exception {
    public EmpresaNaoCadastradaException() {
        super("Empresa nao cadastrada");
    }
}