package br.ufal.ic.p2.myfood.services;

import br.ufal.ic.p2.myfood.services.XMLFunctions.XMLEmpresa;
import br.ufal.ic.p2.myfood.models.TiposUsuarios.DonoRestaurante;
import br.ufal.ic.p2.myfood.models.Empresa;
import br.ufal.ic.p2.myfood.models.Usuario;

import java.util.HashMap;
import java.util.Map;

public class EmpresaManager {
    private Map<Integer, Empresa> empresas = new HashMap<>();
    private final UsuarioManager usuarioManager;
    private int nextEmpresaId = 0;

    public EmpresaManager(UsuarioManager usuarioManager) {
        this.usuarioManager = usuarioManager;
    }

    public int criarEmpresa(String nome, int donoId, String endereco, String tipoCozinha, String tipoEmpresa) {
        Usuario usuario = usuarioManager.getUser(donoId);

        for (Empresa empresaExistente : empresas.values()) {
            if (empresaExistente.getNome().equals(nome) && empresaExistente.getDonoId() != donoId) {
                throw new IllegalArgumentException("Empresa com esse nome ja existe");
            }
            if (empresaExistente.getNome().equals(nome) && empresaExistente.getEndereco().equals(endereco)) {
                throw new IllegalArgumentException("Proibido cadastrar duas empresas com o mesmo nome e local");
            }
        }

        if (!(usuario instanceof DonoRestaurante)) {
            throw new IllegalArgumentException("Usuario nao pode criar uma empresa");
        }

        DonoRestaurante dono = (DonoRestaurante) usuario;

        int empresaId = nextEmpresaId++;
        Empresa empresa = new Empresa(empresaId, nome, endereco, tipoCozinha, dono.getId(), dono.getNome(), tipoEmpresa);
        empresas.put(empresaId, empresa);
        XMLEmpresa.saveEmpresas(empresas);

        return empresaId;
    }

    public String getEmpresasDoUsuario(int idDono) {
        Usuario usuario = usuarioManager.getUser(idDono);

        if (!(usuario instanceof DonoRestaurante dono)) {
            throw new IllegalArgumentException("Usuario nao pode criar uma empresa");
        }

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

        // Verifica se o �ndice � negativo
        if (indiceInt < 0) {
            throw new IllegalArgumentException("Indice invalido");
        }

        // Verifica se o usu�rio � um DonoRestaurante
        Usuario usuario = usuarioManager.getUser(idDono);

        if (!(usuario instanceof DonoRestaurante dono)) {
            throw new IllegalArgumentException("Usuario nao � um dono de restaurante.");
        }

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

    public String getAtributoEmpresa(int empresaId, String atributo) {
        Empresa empresa = getEmpresa(empresaId);

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
        XMLEmpresa.saveEmpresas(empresas);
    }

    public boolean isDonoEmpresa(int donoId, int empresaId) {
        Empresa empresa = empresas.get(empresaId);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa nao cadastrada");
        }
        return empresa.getDonoId() == donoId;
    }

}