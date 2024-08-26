package br.ufal.ic.p2.myfood.services;

import br.ufal.ic.p2.myfood.XMLFunction.XMLFacade;
import br.ufal.ic.p2.myfood.tipousuario.DonoRestaurante;
import br.ufal.ic.p2.myfood.tipousuario.Empresa;
import br.ufal.ic.p2.myfood.tipousuario.User;

import java.util.HashMap;
import java.util.Map;

public class EmpresaManager {
    private Map<Integer, Empresa> empresas = new HashMap<>();
    private UserManager userManager;
    private int nextEmpresaId = 0;

    public EmpresaManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public int criarEmpresa(String nome, int donoId, String endereco, String tipoCozinha) {
        User user = userManager.getUser(donoId);

        for (Empresa empresaExistente : empresas.values()) {
            if (empresaExistente.getNome().equals(nome) && empresaExistente.getDonoId() != donoId) {
                throw new IllegalArgumentException("Empresa com esse nome ja existe");
            }
            if (empresaExistente.getNome().equals(nome) && empresaExistente.getEndereco().equals(endereco)) {
                throw new IllegalArgumentException("Proibido cadastrar duas empresas com o mesmo nome e local");
            }
        }

        if (!(user instanceof DonoRestaurante)) {
            throw new IllegalArgumentException("Usuario nao pode criar uma empresa");
        }

        DonoRestaurante dono = (DonoRestaurante) user;

        int empresaId = nextEmpresaId++;
        Empresa empresa = new Empresa(empresaId, nome, endereco, tipoCozinha, dono.getId(), dono.getNome());
        empresas.put(empresaId, empresa);
        XMLFacade.salvarEmpresas(empresas);

        return empresaId;
    }

    public String getEmpresasDoUsuario(int idDono) {
        User user = userManager.getUser(idDono);

        if (!(user instanceof DonoRestaurante)) {
            throw new IllegalArgumentException("Usuario nao pode criar uma empresa");
        }

        DonoRestaurante dono = (DonoRestaurante) user;

        Map<Integer, Empresa> empresas = getEmpresas();

        StringBuilder sb = new StringBuilder();
        sb.append("{[");
        boolean primeiro = true;

        for (Empresa empresa : empresas.values()) {
            if (empresa.getDonoId() == dono.getId()) {
                if (!primeiro) {
                    sb.append(", ");
                }
                sb.append("[").append(empresa.getNome()).append(", ").append(empresa.getEndereco()).append("]");
                primeiro = false;
            }
        }

        sb.append("]}");
        return sb.toString();
    }

    public int getIdEmpresa(int idDono, String nome, String indice) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome invalido");
        }

        if (indice == null) {
            throw new IllegalArgumentException("Indice invalido");
        }

        int indiceInt;
        indiceInt = Integer.parseInt(indice);

        // Verifica se o índice é negativo
        if (indiceInt < 0) {
            throw new IllegalArgumentException("Indice invalido");
        }

        // Verifica se o usuário é um DonoRestaurante
        User user = userManager.getUser(idDono);

        if (!(user instanceof DonoRestaurante)) {
            throw new IllegalArgumentException("Usuario nao é um dono de restaurante.");
        }

        DonoRestaurante dono = (DonoRestaurante) user;

        // Verifica empresas associadas ao dono e ao nome fornecido
        int count = 0;
        boolean empresaEncontrada = false;
        for (Empresa empresa : getEmpresas().values()) {

            if (empresa.getNome().equals(nome) && empresa.getDonoId() == dono.getId()) {
                empresaEncontrada = true;
                if (count == indiceInt) {
                    return empresa.getId();
                }
                count++;
            }
        }

        if (!empresaEncontrada) {
            throw new IllegalArgumentException("Nao existe empresa com esse nome");
        }

        throw new IllegalArgumentException("Indice maior que o esperado");
    }

    /*
    public int criarEmpresa(String tipoEmpresa, int donoId, String nome, String endereco, String tipoCozinha) {
        // Verificar se o dono existe e é um DonoRestaurante
        User user = userManager.getUser(donoId);

        if (!(user instanceof DonoRestaurante)) {
            throw new IllegalArgumentException("Usuário não pode criar uma empresa");
        }

        DonoRestaurante dono = (DonoRestaurante) user;

        // Criação da empresa usando EmpresaManager
        return criarEmpresa(nome, endereco, tipoCozinha, dono.getId(), dono.getNome());
    }
    */

    public Empresa getEmpresa(int empresaId) {
        return empresas.get(empresaId);
    }

    public Map<Integer, Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Map<Integer, Empresa> empresas) {
        this.empresas = empresas;
    }

    public void zerarSistema() {
        nextEmpresaId = 0;
        empresas.clear();
        XMLFacade.salvarEmpresas(empresas); // Save the cleared state to the XML file
    }

    public boolean isDonoEmpresa(int donoId, int empresaId) {
        Empresa empresa = empresas.get(empresaId);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        return empresa.getDonoId() == donoId;
    }

}
