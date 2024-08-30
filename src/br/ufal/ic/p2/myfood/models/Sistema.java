package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.services.managers.EmpresaManager;
import br.ufal.ic.p2.myfood.services.managers.PedidoManager;
import br.ufal.ic.p2.myfood.services.managers.ProdutoManager;
import br.ufal.ic.p2.myfood.services.managers.UsuarioManager;

import java.io.File;

public class Sistema {

    private UsuarioManager usuarioManager;
    private EmpresaManager empresaManager;
    private ProdutoManager produtoManager;
    private PedidoManager pedidosManager;

    public Sistema() {
        this.usuarioManager = new UsuarioManager();
        this.empresaManager = new EmpresaManager(usuarioManager);
        this.produtoManager = new ProdutoManager(empresaManager);
        this.pedidosManager = new PedidoManager(empresaManager, produtoManager, usuarioManager);

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
