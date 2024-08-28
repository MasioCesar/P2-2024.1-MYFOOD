package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class AtributoNaoExisteException extends Exception {
    public AtributoNaoExisteException() {
        super("Atributo nao existe");
    }
}
