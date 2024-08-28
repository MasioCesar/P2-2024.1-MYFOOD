package br.ufal.ic.p2.myfood.exceptions.Empresa;

public class EmpresaJaExisteException extends IllegalArgumentException {
    public EmpresaJaExisteException() {
        super("Empresa com esse nome ja existe");
    }
}