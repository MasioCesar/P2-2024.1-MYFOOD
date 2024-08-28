package br.ufal.ic.p2.myfood.exceptions.Usuario;


public class EnderecoInvalidoException extends IllegalArgumentException {
    public EnderecoInvalidoException() {
        super("Endereco invalido");
    }
}