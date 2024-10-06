package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.models.entidades.Empresa;
import br.ufal.ic.p2.myfood.models.entidades.Produto;
import br.ufal.ic.p2.myfood.models.entidades.Usuario;
import br.ufal.ic.p2.myfood.services.managers.EmpresaManager;
import br.ufal.ic.p2.myfood.services.managers.PedidoManager;
import br.ufal.ic.p2.myfood.services.managers.ProdutoManager;
import br.ufal.ic.p2.myfood.services.managers.UsuarioManager;
import br.ufal.ic.p2.myfood.services.mediator.Mediator;

import java.util.Map;

public class Sistema implements Mediator {

    private UsuarioManager usuarioManager;
    private EmpresaManager empresaManager;
    private ProdutoManager produtoManager;
    private PedidoManager pedidosManager;

    public Sistema() {
        // Inicializar a única instância
        this.usuarioManager = UsuarioManager.getInstance(this);
        this.empresaManager = EmpresaManager.getInstance(this);
        this.produtoManager = ProdutoManager.getInstance(this);
        this.pedidosManager = PedidoManager.getInstance(this);
    }

    @Override
    public Usuario getUsuarioById(int id) throws Exception {
        return usuarioManager.getUser(id);
    }

    @Override
    public Empresa getEmpresaById(int id) throws Exception {
        return empresaManager.getEmpresa(id);
    }

    @Override
    public Produto getProdutoById(int id) throws Exception {
        return produtoManager.getProdutoPorId(id);
    }

    @Override
    public boolean isDonoEmpresa(int clienteId, int empresaId) throws Exception {
        return empresaManager.isDonoEmpresa(clienteId, empresaId);
    }

    @Override
    public Map<Integer, Empresa> getAllEmpresas() throws Exception {
        return empresaManager.getAllEmpresas();
    }

    // Zerar o sistema
    public void zerarSistema() {
        usuarioManager.zerarSistema();
        empresaManager.zerarSistema();
        produtoManager.zerarSistema();
        pedidosManager.zerarSistema();
    }

    public UsuarioManager getUsuarioManager() {
        return usuarioManager;
    }

    public EmpresaManager getEmpresaManager() {
        return empresaManager;
    }

    public ProdutoManager getProdutoManager() {
        return produtoManager;
    }

    public PedidoManager getPedidosManager() {
        return pedidosManager;
    }

    public void encerrarSistema() {
        usuarioManager.salvarDados();
        empresaManager.salvarDados();
        produtoManager.salvarDados();
        pedidosManager.salvarDados();
    }
}
