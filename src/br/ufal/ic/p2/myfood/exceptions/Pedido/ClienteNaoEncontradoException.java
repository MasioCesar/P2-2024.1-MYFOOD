package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class ClienteNaoEncontradoException extends Exception {
    public ClienteNaoEncontradoException() {
        super("Cliente nao encontrado");
    }
}