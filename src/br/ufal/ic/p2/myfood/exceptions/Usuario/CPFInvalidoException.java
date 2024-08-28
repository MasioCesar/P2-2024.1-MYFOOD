package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class CPFInvalidoException extends Exception {
    public CPFInvalidoException() {
        super("CPF invalido");
    }
}