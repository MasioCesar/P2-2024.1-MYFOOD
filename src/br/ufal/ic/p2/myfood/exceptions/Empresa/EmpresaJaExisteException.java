package br.ufal.ic.p2.myfood.exceptions.Empresa;

public class EmpresaJaExisteException extends Exception {
    public EmpresaJaExisteException() {
        super("Empresa com esse nome ja existe");
    }
}