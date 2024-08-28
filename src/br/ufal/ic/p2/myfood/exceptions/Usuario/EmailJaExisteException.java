package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class EmailJaExisteException extends Exception {
    public EmailJaExisteException() {
        super("Conta com esse email ja existe");
    }
}