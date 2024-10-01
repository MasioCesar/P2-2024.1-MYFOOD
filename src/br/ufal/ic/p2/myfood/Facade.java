package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.models.Sistema;

import java.util.Objects;

public class Facade {
    private Sistema sistema;

    public Facade() {
        sistema = new Sistema();
    }

    // Zera o sistema
    public void zerarSistema() {
        sistema.zerarSistema();
    }

    // Cliente
    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        sistema.getUsuarioManager().criarUsuario(nome, email, senha, endereco);
    }

    // Dono Restaurante
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        sistema.getUsuarioManager().criarUsuario(nome, email, senha, endereco, cpf);
    }

    // Entregador
    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws Exception {
        sistema.getUsuarioManager().criarUsuario(nome, email, senha, endereco, veiculo, placa);
    }

    public String getAtributoUsuario(int id, String atributo) throws Exception {
        return sistema.getUsuarioManager().getAtributoUsuario(id, atributo);
    }

    public int login(String email, String senha) throws Exception {
        return sistema.getUsuarioManager().login(email, senha);
    }

    // SEÇÃO EMPRESAS
    public int criarEmpresa(String tipoEmpresa, int donoId, String nome, String endereco, String tipoCozinha) throws Exception {
        return sistema.getEmpresaManager().criarRestaurante(nome, donoId, endereco, tipoCozinha, tipoEmpresa);
    }

    public int criarEmpresa(String tipoEmpresa, int donoId, String nome, String endereco, String abre, String fecha, String tipoMercado) throws Exception {
        return sistema.getEmpresaManager().criarMercado(tipoEmpresa, donoId, nome, endereco, abre, fecha, tipoMercado);
    }

    public int criarEmpresa(String tipoEmpresa, int donoId, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws Exception {
        return sistema.getEmpresaManager().criarFarmacia(nome, donoId, endereco, aberto24Horas, tipoEmpresa, numeroFuncionarios);
    }

    public String getEmpresasDoUsuario(int idDono) throws Exception {
        return sistema.getEmpresaManager().getEmpresasDoUsuario(idDono);
    }

    public int getIdEmpresa(int idDono, String nome, String indice) throws Exception {
        return sistema.getEmpresaManager().getIdEmpresa(idDono, nome, indice);
    }

    public String getAtributoEmpresa(int empresaId, String atributo) throws Exception {
        return sistema.getEmpresaManager().getAtributoEmpresa(empresaId, atributo);
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha) throws Exception {
        sistema.getEmpresaManager().alterarFuncionamento(mercado, abre, fecha);
    }

    // SEÇÃO PRODUTOS
    public int criarProduto(int empresaId, String nome, float valor, String categoria) throws Exception {
        return sistema.getProdutoManager().criarProduto(empresaId, nome, valor, categoria);
    }

    public void editarProduto(int produtoId, String nome, float valor, String categoria) throws Exception {
        sistema.getProdutoManager().editarProduto(produtoId, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresaId, String atributo) throws Exception {
        return sistema.getProdutoManager().getProduto(nome, empresaId, atributo);
    }

    public String listarProdutos(int empresaId) throws Exception {
        return sistema.getProdutoManager().listarProdutos(empresaId);
    }

    // SEÇÃO PEDIDOS
    public int criarPedido(int cliente, int empresa) throws Exception {
        return sistema.getPedidosManager().criarPedido(cliente, empresa);
    }

    public void adicionarProduto(int numero, int produto) throws Exception {
        sistema.getPedidosManager().adicionarProduto(numero, produto);
    }

    public String getPedidos(int numero, String atributo) throws Exception {
        return sistema.getPedidosManager().getPedidos(numero, atributo);
    }

    public void fecharPedido(int numero) throws Exception {
        sistema.getPedidosManager().fecharPedido(numero);
    }

    public void removerProduto(int pedido, String produto) throws Exception {
        sistema.getPedidosManager().removerProduto(pedido, produto);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) throws Exception {
        return sistema.getPedidosManager().getNumeroPedido(cliente, empresa, indice);
    }

    // Encerrar o sistema
    public void encerrarSistema() {
        sistema.encerrarSistema();
    }
}
