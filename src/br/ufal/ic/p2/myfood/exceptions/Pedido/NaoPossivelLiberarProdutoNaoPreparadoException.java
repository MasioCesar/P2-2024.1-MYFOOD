package br.ufal.ic.p2.myfood.exceptions.Pedido;

public class NaoPossivelLiberarProdutoNaoPreparadoException extends Exception {
    public NaoPossivelLiberarProdutoNaoPreparadoException() {
        super("Nao e possivel liberar um produto que nao esta sendo preparado");
    }
}
