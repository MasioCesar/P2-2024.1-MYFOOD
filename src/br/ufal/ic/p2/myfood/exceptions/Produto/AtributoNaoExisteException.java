package br.ufal.ic.p2.myfood.exceptions.Produto;

public class AtributoNaoExisteException extends Exception {
    public AtributoNaoExisteException() {
        super("Atributo nao existe");
    }
}