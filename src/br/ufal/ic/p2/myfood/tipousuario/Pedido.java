package br.ufal.ic.p2.myfood.tipousuario;

import java.util.*;

public class Pedido {
    private int numero;
    private int cliente;
    private int empresa;
    private String estado;
    private List<Produto> produtos = new ArrayList<>();

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
        System.out.println("OS ITEMS: "+produtos);
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void removerProduto(String nomeProduto) {
        if (nomeProduto == null || nomeProduto.trim().isEmpty()) {
            throw new IllegalArgumentException("Produto invalido");
        }

        Iterator<Produto> iterator = produtos.iterator();
        while (iterator.hasNext()) {
            Produto produto = iterator.next();
            if (produto.getNome().equals(nomeProduto)) {
                iterator.remove();
                return;
            }
        }

        throw new IllegalArgumentException("Produto nao encontrado");
    }

    public double calcularValor() {
        return produtos.stream()
                .mapToDouble(Produto::getValor)
                .sum();
    }

}
