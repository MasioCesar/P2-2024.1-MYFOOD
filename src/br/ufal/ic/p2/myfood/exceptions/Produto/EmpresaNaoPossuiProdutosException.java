package br.ufal.ic.p2.myfood.exceptions.Produto;

public class EmpresaNaoPossuiProdutosException extends Exception {
    public EmpresaNaoPossuiProdutosException() {
        super("Empresa nao possui produtos.");
    }
}