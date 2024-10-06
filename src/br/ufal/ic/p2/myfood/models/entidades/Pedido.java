package br.ufal.ic.p2.myfood.models.entidades;

import br.ufal.ic.p2.myfood.exceptions.Pedido.ProdutoInvalidoException;
import br.ufal.ic.p2.myfood.exceptions.Pedido.ProdutoNaoEncontradoException;

import java.util.*;

public class Pedido {
    private int numero;
    private int cliente;
    private int empresa;
    private String estado;
    private List<Produto> produtos = new ArrayList<>();
    private Entrega entrega;

    public Pedido(int numero, int cliente, int empresa, String estado) {
        this.numero = numero;
        this.cliente = cliente;
        this.empresa = empresa;
        this.estado = estado;
    }

    public Pedido() {

    }

    public int getNumero() {
        return numero;
    }

    public int getCliente() {
        return cliente;
    }

    public int getEmpresa() {
        return empresa;
    }

    public String getEstado() {
        return estado;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public void removerProduto(String nomeProduto) throws Exception {
        if (nomeProduto == null || nomeProduto.trim().isEmpty()) {
            throw new ProdutoInvalidoException();
        }

        Iterator<Produto> iterator = produtos.iterator();
        while (iterator.hasNext()) {
            Produto produto = iterator.next();
            if (produto.getNome().equals(nomeProduto)) {
                iterator.remove();
                return;
            }
        }

        throw new ProdutoNaoEncontradoException();
    }

    public double calcularValor() {
        return produtos.stream()
                .mapToDouble(Produto::getValor)
                .sum();
    }

    public void iniciarEntrega(Entrega entrega) {
        this.entrega = entrega;
        this.estado = "entregando";
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void finalizarEntrega() {
        this.estado = "entregue";
    }

}