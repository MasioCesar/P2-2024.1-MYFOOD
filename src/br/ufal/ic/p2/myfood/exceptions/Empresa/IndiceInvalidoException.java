package br.ufal.ic.p2.myfood.exceptions.Empresa;

public class IndiceInvalidoException extends IllegalArgumentException {
    public IndiceInvalidoException() {
        super("Indice invalido");
    }
}