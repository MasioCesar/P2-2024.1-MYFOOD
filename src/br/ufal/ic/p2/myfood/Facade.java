package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.services.EmpresaManager;
import br.ufal.ic.p2.myfood.services.XMLFunctions.XMLEmpresa;
import br.ufal.ic.p2.myfood.services.XMLFunctions.XMLPedido;
import br.ufal.ic.p2.myfood.services.XMLFunctions.XMLProduto;
import br.ufal.ic.p2.myfood.services.XMLFunctions.XMLUser;
import br.ufal.ic.p2.myfood.services.PedidoManager;
import br.ufal.ic.p2.myfood.services.ProdutoManager;
import br.ufal.ic.p2.myfood.services.UsuarioManager;

import java.io.*;

public class Facade {
    private UsuarioManager usuarioManager = new UsuarioManager();
    private EmpresaManager empresaManager = new EmpresaManager(usuarioManager);
    private ProdutoManager produtoManager = new ProdutoManager(empresaManager);
    private PedidoManager pedidosManager = new PedidoManager(empresaManager, produtoManager, usuarioManager);

    public Facade() {
        carregarUsers();
        carregarEmpresas();
        carregarProdutos();
        carregarPedidos();
    }

    // Zera o sistema, removendo todos os dados e sessões
    public void zerarSistema() {
        usuarioManager.zerarSistema();
        empresaManager.zerarSistema();
        produtoManager.zerarSistema();
        pedidosManager.zerarSistema();

        clearDataFile("users.xml");
        clearDataFile("empresas.xml");
        clearDataFile("produtos.xml");
        clearDataFile("pedidos.xml");
    }

    private void clearDataFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    private void carregarUsers() {
        usuarioManager.setUsers(XMLUser.loadUsuarios());
    }

    private void carregarEmpresas() {
        empresaManager.setEmpresas(XMLEmpresa.loadEmpresas());
    }

    private void carregarProdutos() {
        produtoManager.setProdutosPorEmpresa(XMLProduto.loadProdutos());
    }

    private void carregarPedidos() {
        pedidosManager.setPedidos(XMLPedido.loadPedidos());
    }


    private static final String CPF_DEFAULT = "DEFAULT";

    public void criarUsuario(String nome, String email, String senha, String endereco) {
        criarUsuario(nome, email, senha, endereco, CPF_DEFAULT);
    }
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) {
        usuarioManager.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public String getAtributoUsuario(int id, String atributo) {
        return usuarioManager.getAtributoUsuario(id, atributo);
    }

    public int login(String email, String senha) {
        return usuarioManager.login(email, senha);
    }


    // Encerra o sistema
    public void encerrarSistema() {

    }

    // SEÇÃO EMPRESAS
    public int criarEmpresa(String tipoEmpresa, int donoId, String nome, String endereco, String tipoCozinha) {
        return empresaManager.criarEmpresa(nome, donoId, endereco, tipoCozinha, tipoEmpresa);
    }

    public String getEmpresasDoUsuario(int idDono) {
        return empresaManager.getEmpresasDoUsuario(idDono);
    }

    public int getIdEmpresa(int idDono, String nome, String indice) {
        return empresaManager.getIdEmpresa(idDono, nome, indice);
    }


    public String getAtributoEmpresa(int empresaId, String atributo) {
        return empresaManager.getAtributoEmpresa(empresaId, atributo);
    }

    // SEÇÃO PRODUTOS
    public int criarProduto(int empresaId, String nome, float valor, String categoria) {
        return produtoManager.criarProduto(empresaId, nome, valor, categoria);
    }

    public void editarProduto(int produtoId, String nome, float valor, String categoria) {
        produtoManager.editarProduto(produtoId, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresaId, String atributo) {
        return produtoManager.getProduto(nome, empresaId, atributo);
    }

    public String listarProdutos(int empresaId) {
        return produtoManager.listarProdutos(empresaId);
    }

    // SEÇÃO PEDIDOS
    public int criarPedido(int cliente, int empresa) {
        return pedidosManager.criarPedido(cliente, empresa);
    }

    public void adicionarProduto(int numero, int produto) {
        pedidosManager.adicionarProduto(numero, produto);
    }

    public String getPedidos(int numero, String atributo) {
        return pedidosManager.getPedidos(numero, atributo);
    }

    public void fecharPedido(int numero) {
        pedidosManager.fecharPedido(numero);
    }

    public void removerProduto(int pedido, String produto) {
        pedidosManager.removerProduto(pedido, produto);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) {
        return pedidosManager.getNumeroPedido(cliente, empresa, indice);
    }

}