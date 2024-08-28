package br.ufal.ic.p2.myfood.exceptions.Empresa;

public class UsuarioNaoPodeCriarEmpresaException extends IllegalArgumentException {
    public UsuarioNaoPodeCriarEmpresaException() {
        super("Usuario nao pode criar uma empresa");
    }
}