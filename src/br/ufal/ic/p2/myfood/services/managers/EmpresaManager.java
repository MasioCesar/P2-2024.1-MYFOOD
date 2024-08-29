package br.ufal.ic.p2.myfood.services.managers;

import br.ufal.ic.p2.myfood.exceptions.Empresa.*;
import br.ufal.ic.p2.myfood.services.XMLFunctions.XMLEmpresa;
import br.ufal.ic.p2.myfood.models.TiposUsuarios.DonoRestaurante;
import br.ufal.ic.p2.myfood.models.entidades.Empresa;
import br.ufal.ic.p2.myfood.models.entidades.Usuario;

import java.util.HashMap;
import java.util.Map;

public class EmpresaManager {
    private Map<Integer, Empresa> empresas = new HashMap<>();
    private final UsuarioManager usuarioManager;
    private int nextEmpresaId = 0;

    public EmpresaManager(UsuarioManager usuarioManager) {
        this.usuarioManager = usuarioManager;
        this.empresas = XMLEmpresa.load();
    }

    public int criarEmpresa(String nome, int donoId, String endereco, String tipoCozinha, String tipoEmpresa) throws Exception {
        Usuario usuario = usuarioManager.getUser(donoId);

        for (Empresa empresaExistente : empresas.values()) {
            if (empresaExistente.getNome().equals(nome) && empresaExistente.getDonoId() != donoId) {
                throw new EmpresaJaExisteException();
            }
            if (empresaExistente.getNome().equals(nome) && empresaExistente.getEndereco().equals(endereco)) {
                throw new ProibidoCadastrarDuasEmpresasException();
            }
        }

        if (!(usuario.possuiCpf())) {
            throw new UsuarioNaoPodeCriarEmpresaException();
        }

        DonoRestaurante dono = (DonoRestaurante) usuario;

        int empresaId = nextEmpresaId++;
        Empresa empresa = new Empresa(empresaId, nome, endereco, tipoCozinha, dono.getId(), dono.getNome(), tipoEmpresa);
        empresas.put(empresaId, empresa);

        return empresaId;
    }

    public String getEmpresasDoUsuario(int idDono) throws Exception {
        Usuario usuario = usuarioManager.getUser(idDono);

        if (!(usuario.possuiCpf())) {
            throw new UsuarioNaoPodeCriarEmpresaException();
        }

        Map<Integer, Empresa> empresas = getEmpresas();

        StringBuilder sb = new StringBuilder();
        sb.append("{[");
        boolean primeiro = true;

        for (Empresa empresa : empresas.values()) {
            if (empresa.getDonoId() == usuario.getId()) {
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

    public int getIdEmpresa(int idDono, String nome, String indice) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeInvalidoException();
        }

        if (indice == null) {
            throw new IndiceInvalidoException();
        }

        int indiceInt;
        indiceInt = Integer.parseInt(indice);

        // Verifica se o índice é negativo
        if (indiceInt < 0) {
            throw new IndiceInvalidoException();
        }

        // Verifica se o usuário é um DonoRestaurante
        Usuario usuario = usuarioManager.getUser(idDono);

        if (!(usuario instanceof DonoRestaurante dono)) {
            throw new UsuarioNaoEDonoException();
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
            throw new NaoExisteEmpresaComEsseNomeException();
        }

        throw new IndiceMaiorQueOEsperadoException();
    }

    public String getAtributoEmpresa(int empresaId, String atributo) throws Exception {
        Empresa empresa = getEmpresa(empresaId);

        if (empresa == null) {
            throw new EmpresaNaoCadastradaException();
        }

        if (atributo == null || atributo.trim().isEmpty()) {
            throw new AtributoInvalidoException();
        }

        return switch (atributo) {
            case "nome" -> empresa.getNome();
            case "endereco" -> empresa.getEndereco();
            case "dono" -> empresa.getDono();
            case "tipoCozinha" -> empresa.getTipoCozinha();
            default -> throw new AtributoInvalidoException();
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
        XMLEmpresa.save(empresas);
    }

    public boolean isDonoEmpresa(int donoId, int empresaId) throws Exception {
        Empresa empresa = empresas.get(empresaId);
        if (empresa == null) {
            throw new EmpresaNaoCadastradaException();
        }
        return empresa.getDonoId() == donoId;
    }

    public void salvarDados() {
        XMLEmpresa.save(empresas);
    }

}
