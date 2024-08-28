package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class CPFInvalidoException extends IllegalArgumentException {
    public CPFInvalidoException() {
        super("CPF invalido");
    }
}