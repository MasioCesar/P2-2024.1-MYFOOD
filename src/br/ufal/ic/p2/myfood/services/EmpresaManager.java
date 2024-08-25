package br.ufal.ic.p2.myfood.services;

import br.ufal.ic.p2.myfood.XMLFunction.XMLFacade;
import br.ufal.ic.p2.myfood.tipousuario.Empresa;

import java.util.HashMap;
import java.util.Map;

public class EmpresaManager {
    private Map<Integer, Empresa> empresas = new HashMap<>();
    private int nextEmpresaId = 0;

    public int criarEmpresa(String nome, String endereco, String tipoCozinha, int donoId, String nomeDono) {
        for (Empresa empresaExistente : empresas.values()) {
            if (empresaExistente.getNome().equals(nome) && empresaExistente.getDonoId() != donoId) {
                throw new IllegalArgumentException("Empresa com esse nome ja existe");
            }
            if (empresaExistente.getNome().equals(nome) && empresaExistente.getEndereco().equals(endereco)) {
                throw new IllegalArgumentException("Proibido cadastrar duas empresas com o mesmo nome e local");
            }
        }

        int empresaId = nextEmpresaId++;
        Empresa empresa = new Empresa(empresaId, nome, endereco, tipoCozinha, donoId, nomeDono);
        empresas.put(empresaId, empresa);
        XMLFacade.salvarEmpresas(empresas);

        return empresaId;
    }

    public Empresa getEmpresa(int empresaId) {
        return empresas.get(empresaId);
    }

    public Map<Integer, Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Map<Integer, Empresa> empresas) {
        this.empresas = empresas;
    }

    public void clearEmpresas() {
        empresas.clear();
    }
}
