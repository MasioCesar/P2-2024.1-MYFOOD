package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class AtributoDesconhecidoException extends Exception {
    public AtributoDesconhecidoException() {
        super("Atributo desconhecido");
    }
}