package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class ClienteNaoEncontradoException extends IllegalArgumentException {
    public ClienteNaoEncontradoException() {
        super("Cliente nao encontrado");
    }
}