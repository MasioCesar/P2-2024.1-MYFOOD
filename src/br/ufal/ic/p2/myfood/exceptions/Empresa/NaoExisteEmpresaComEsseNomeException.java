package br.ufal.ic.p2.myfood.exceptions.Empresa;

public class NaoExisteEmpresaComEsseNomeException extends Exception {
    public NaoExisteEmpresaComEsseNomeException() {
        super("Nao existe empresa com esse nome");
    }
}