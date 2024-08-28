package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class AtributoInvalidoException extends IllegalArgumentException {
    public AtributoInvalidoException() {
        super("Atributo invalido");
    }
}