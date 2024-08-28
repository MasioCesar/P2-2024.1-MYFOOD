package br.ufal.ic.p2.myfood.exceptions.Usuario;

public class AtributoDesconhecidoException extends IllegalArgumentException {
    public AtributoDesconhecidoException() {
        super("Atributo desconhecido");
    }
}