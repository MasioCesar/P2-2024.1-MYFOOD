package br.ufal.ic.p2.myfood.exceptions.Empresa;

public class UsuarioNaoPodeCriarEmpresaException extends Exception {
    public UsuarioNaoPodeCriarEmpresaException() {
        super("Usuario nao pode criar uma empresa");
    }
}