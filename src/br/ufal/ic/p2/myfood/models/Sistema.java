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
        // Inicializar a única instância do UsuarioManager usando o Singleton
        this.usuarioManager = UsuarioManager.getInstance();
        // Inicializar a única instância do EmpresaManager usando o Singleton
        this.empresaManager = EmpresaManager.getInstance(usuarioManager);
        // Inicializar a única instância do ProdutoManager usando o Singleton
        this.produtoManager = ProdutoManager.getInstance(empresaManager);
        // Inicializar a única instância do PedidoManager usando o Singleton
        this.pedidosManager = PedidoManager.getInstance(empresaManager, produtoManager, usuarioManager);
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
