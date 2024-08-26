package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.XMLFunction.XMLFacade;
import br.ufal.ic.p2.myfood.services.EmpresaManager;
import br.ufal.ic.p2.myfood.services.PedidoManager;
import br.ufal.ic.p2.myfood.services.ProdutoManager;
import br.ufal.ic.p2.myfood.services.UserManager;
import br.ufal.ic.p2.myfood.tipousuario.Cliente;
import br.ufal.ic.p2.myfood.tipousuario.DonoRestaurante;
import br.ufal.ic.p2.myfood.tipousuario.Empresa;
import br.ufal.ic.p2.myfood.tipousuario.User;


import java.beans.XMLEncoder;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Facade {
    private UserManager userManager = new UserManager();
    private EmpresaManager empresaManager = new EmpresaManager(userManager);
    private ProdutoManager produtoManager = new ProdutoManager(empresaManager);
    private PedidoManager pedidosManager = new PedidoManager(empresaManager, produtoManager, userManager);

    private int nextUserId = 0; // Gerador de ID único
    private String sessionUser;
    // private static final String DATA_FILE = "users_data.xml";
    // private static final String DATA_FILE = "empresas.xml";
    // private static final String DATA_FILE = "produtos.xml";

    public Facade() {
        carregarUsers();
        carregarEmpresas();
        carregarProdutos();
    }

    // Zera o sistema, removendo todos os dados e sessões
    public void zerarSistema() {
        // Zerar dados de users
        userManager.zerarSistema();

        // Zerar dados de empresas
        empresaManager.zerarSistema();

        // Zerar dados de produtos
        produtoManager.zerarSistema();

        // Apagar os arquivos de dados
        clearDataFile("users_data.xml");
        clearDataFile("empresas.xml");
        clearDataFile("produtos.xml");
    }

    // Método auxiliar para apagar um arquivo de dados
    private void clearDataFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    private void carregarUsers() {
        userManager.setUsers(XMLFacade.loadUsers());
    }

    private void carregarEmpresas() {
        empresaManager.setEmpresas(XMLFacade.loadEmpresas());
    }

    private void carregarProdutos() {
        produtoManager.setProdutosPorEmpresa(XMLFacade.loadProdutos());
    }


    private static final String CPF_DEFAULT = "DEFAULT";

    public void criarUsuario(String nome, String email, String senha, String endereco) {
        criarUsuario(nome, email, senha, endereco, CPF_DEFAULT);
    }
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) {
        userManager.criarUsuario(nome, email, senha, endereco, cpf);
    }

    // Retorna o valor de um atributo específico do usuário
    public String getAtributoUsuario(int id, String atributo) {
        return userManager.getAtributoUsuario(id, atributo);
    }

    public int login(String email, String senha) {
        return userManager.login(email, senha);
    }


    // Encerra o sistema, limpando a sessão
    public void encerrarSistema() {
        sessionUser = null;
    }



    // SEÇÃO EMPRESAS
    public int criarEmpresa(String tipoEmpresa, int donoId, String nome, String endereco, String tipoCozinha) {
        return empresaManager.criarEmpresa(nome, donoId, endereco, tipoCozinha);
    }


    // Obtém todas as empresas do usuário
    public String getEmpresasDoUsuario(int idDono) {
        return empresaManager.getEmpresasDoUsuario(idDono);
    }


    // Obtém o id da empresa pelo nome e índice
    public int getIdEmpresa(int idDono, String nome, String indice) {
        return empresaManager.getIdEmpresa(idDono, nome, indice);
    }


    public String getAtributoEmpresa(int empresaId, String atributo) {
        Empresa empresa = empresaManager.getEmpresa(empresaId);

        if (empresa == null) {
            throw new IllegalArgumentException("Empresa nao cadastrada");
        }

        if (atributo == null || atributo.trim().isEmpty()) {
            throw new IllegalArgumentException("Atributo invalido");
        }

        return switch (atributo) {
            case "nome" -> empresa.getNome();
            case "endereco" -> empresa.getEndereco();
            case "dono" -> empresa.getDono();
            case "tipoCozinha" -> empresa.getTipoCozinha();
            default -> throw new IllegalArgumentException("Atributo invalido");
        };
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

    public void adcionarProduto(int numero, int produto) {
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