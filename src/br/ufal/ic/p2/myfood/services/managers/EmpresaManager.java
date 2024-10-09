package br.ufal.ic.p2.myfood.services.managers;

import br.ufal.ic.p2.myfood.exceptions.Empresa.*;
import br.ufal.ic.p2.myfood.exceptions.Usuario.UsuarioNaoEncontradoException;
import br.ufal.ic.p2.myfood.exceptions.Usuario.UsuarioNaoEntregadorException;
import br.ufal.ic.p2.myfood.models.TiposEmpresas.Farmacia;
import br.ufal.ic.p2.myfood.models.TiposEmpresas.Mercado;
import br.ufal.ic.p2.myfood.models.TiposEmpresas.Restaurante;
import br.ufal.ic.p2.myfood.models.TiposUsuarios.Entregador;
import br.ufal.ic.p2.myfood.services.XMLFunctions.XMLEmpresa;
import br.ufal.ic.p2.myfood.models.TiposUsuarios.DonoEmpresa;
import br.ufal.ic.p2.myfood.models.entidades.Empresa;
import br.ufal.ic.p2.myfood.models.entidades.Usuario;
import br.ufal.ic.p2.myfood.services.mediator.Mediator;
import br.ufal.ic.p2.myfood.utils.Validate;

import java.util.List;
import java.util.Map;

public class EmpresaManager {
    // Instância única da classe EmpresaManager
    private static EmpresaManager instance;
    private Mediator mediator;

    private Map<Integer, Empresa> empresas;

    private int nextEmpresaId = 0;

    // Construtor privado para evitar instanciação externa
    private EmpresaManager(Mediator mediator) {
        this.mediator = mediator;
        this.empresas = XMLEmpresa.load();
    }

    // Método público e estático para retornar a única instância
    public static EmpresaManager getInstance(Mediator mediator) {
        if (instance == null) {
            // Cria a instância apenas se ela ainda não foi criada
            instance = new EmpresaManager(mediator);
        }
        return instance;
    }

    // RESTAURANTE
    public int criarRestaurante(String nome, int donoId, String endereco, String tipoCozinha, String tipoEmpresa) throws Exception {
        // Utiliza o Mediator para obter a usuario pelo ID
        Usuario usuario = mediator.getUsuarioById(donoId);

        if (tipoEmpresa == null || tipoEmpresa.trim().isEmpty()) {
            throw new TipoEmpresaInvalidoException();
        }

        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeInvalidoException();
        }

        if (endereco == null || endereco.trim().isEmpty()) {
            throw new EnderecoInvalidoException();
        }

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

        DonoEmpresa dono = (DonoEmpresa) usuario;

        int empresaId = nextEmpresaId++;
        Empresa empresa = new Restaurante(empresaId, nome, endereco, dono.getId(), dono.getNome(), tipoEmpresa, tipoCozinha);
        empresas.put(empresaId, empresa);

        return empresaId;
    }

    // MERCADO
    public int criarMercado(String tipoEmpresa, int donoId, String nome, String endereco, String abre, String fecha, String tipoMercado) throws Exception {
        Usuario usuario = mediator.getUsuarioById(donoId);

        if (tipoEmpresa == null || tipoEmpresa.trim().isEmpty()) {
            throw new TipoEmpresaInvalidoException();
        }

        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeInvalidoException();
        }

        if (endereco == null || endereco.trim().isEmpty()) {
            throw new EnderecoInvalidoException();
        }

        if (tipoMercado == null || tipoMercado.trim().isEmpty()) {
            throw new TipoMercadoInvalidoException();
        }

        if (!(usuario.possuiCpf())) {
            throw new UsuarioNaoPodeCriarEmpresaException();
        }

        if (abre == null) {
            throw new HorarioInvalidoException();
        }

        if (abre.trim().isEmpty()) {
            throw new FormatoHoraInvalidoException();
        }

        if (fecha == null) {
            throw new HorarioInvalidoException();
        }

        if (fecha.trim().isEmpty()) {
            throw new FormatoHoraInvalidoException();
        }

        if (!Validate.isHoraValida(abre) || !Validate.isHoraValida(fecha)) {
            throw new FormatoHoraInvalidoException();
        }

        if (Validate.horariosInvalidos(abre, fecha)) {
            throw new HorarioInvalidoException();
        }

        for (Empresa empresaExistente : empresas.values()) {
            if (empresaExistente.getNome().equals(nome) && empresaExistente.getDonoId() != donoId) {
                throw new EmpresaJaExisteException();
            }
            if (empresaExistente.getNome().equals(nome) && empresaExistente.getEndereco().equals(endereco)) {
                throw new ProibidoCadastrarDuasEmpresasException();
            }
        }

        DonoEmpresa dono = (DonoEmpresa) usuario;

        int empresaId = nextEmpresaId++;
        Empresa empresa = new Mercado(empresaId, nome, endereco, abre, fecha, tipoMercado, dono.getId(), dono.getNome(), tipoEmpresa);
        empresas.put(empresaId, empresa);

        return empresaId;
    }

    public int criarFarmacia(String nome, int donoId, String endereco, boolean aberto24Horas, String tipoEmpresa, int numeroFuncionarios) throws Exception {
        Usuario usuario = mediator.getUsuarioById(donoId);

        if (tipoEmpresa == null || tipoEmpresa.trim().isEmpty()) {
            throw new TipoEmpresaInvalidoException();
        }

        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeInvalidoException();
        }

        if (endereco == null || endereco.trim().isEmpty()) {
            throw new EnderecoInvalidoException();
        }

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

        DonoEmpresa dono = (DonoEmpresa) usuario;

        int empresaId = nextEmpresaId++;
        Empresa empresa = new Farmacia(empresaId, nome, endereco, dono.getId(), dono.getNome(), tipoEmpresa, aberto24Horas, numeroFuncionarios);
        empresas.put(empresaId, empresa);

        return empresaId;
    }

    public String getEmpresasDoUsuario(int idDono) throws Exception {
        Usuario usuario = mediator.getUsuarioById(idDono);

        if (!(usuario.possuiCpf())) {
            throw new UsuarioNaoPodeCriarEmpresaException();
        }

        Map<Integer, Empresa> empresas = getAllEmpresas();

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

        // Verifica se o usuário é um DonoEmpresa
        Usuario usuario = mediator.getUsuarioById(idDono);

        if (!usuario.possuiCpf()) {
            throw new UsuarioNaoEDonoException();
        }

        int count = 0;
        boolean empresaEncontrada = false;
        for (Empresa empresa : getAllEmpresas().values()) {
            if (empresa.getNome().equals(nome) && empresa.getDonoId() == usuario.getId()) {
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

        switch (atributo) {
            case "nome":
                return empresa.getNome();
            case "endereco":
                return empresa.getEndereco();
            case "dono":
                return empresa.getDono();
            case "tipoCozinha":
                if (empresa.isRestaurante()) {
                    return ((Restaurante) empresa).getTipoCozinha();
                } else {
                    throw new AtributoInvalidoException();
                }
            case "abre":
                if (empresa.isMercado()) {
                    return ((Mercado) empresa).getHorarioAbertura();
                } else {
                    throw new AtributoInvalidoException();
                }
            case "fecha":
                if (empresa.isMercado()) {
                    return ((Mercado) empresa).getHorarioFechamento();
                } else {
                    throw new AtributoInvalidoException();
                }
            case "tipoMercado":
                if (empresa.isMercado()) {
                    return ((Mercado) empresa).getTipoMercado();
                } else {
                    throw new AtributoInvalidoException();
                }
            case "aberto24Horas":
                if (empresa.isFarmacia()) {
                    Boolean aberto24Horas = ((Farmacia) empresa).getAberto24Horas();
                    return aberto24Horas != null ? aberto24Horas.toString() : "null"; // Verificar se é nulo e converter para String
                } else {
                    throw new AtributoInvalidoException();
                }
            case "numeroFuncionarios":
                if (empresa.isFarmacia()) {
                    int numeroFuncionarios = ((Farmacia) empresa).getNumeroFuncionarios();
                    return String.valueOf(numeroFuncionarios);
                } else {
                    throw new AtributoInvalidoException();
                }
            default:
                throw new AtributoInvalidoException();
        }
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha) throws Exception {
        if (abre == null || abre.trim().isEmpty() || fecha == null || fecha.trim().isEmpty()) {
            throw new HorarioInvalidoException();
        }

        if (!Validate.isHoraValida(abre) || !Validate.isHoraValida(fecha)) {
            throw new FormatoHoraInvalidoException();
        }

        if (Validate.horariosInvalidos(abre, fecha)) {
            throw new HorarioInvalidoException();
        }

        Empresa empresa = getEmpresa(mercado);

        if (!empresa.isMercado()) {
            throw new NaoEMercadoValidoException();
        }

        Mercado mercadoAtual = (Mercado) empresa;
        mercadoAtual.setHorarioAbertura(abre);
        mercadoAtual.setHorarioFechamento(fecha);
    }

    public void cadastrarEntregador(int empresaId, int entregadorId) throws Exception {
        // Primeiro, recuperar o usuário pelo ID
        Usuario usuario = mediator.getUsuarioById(entregadorId);
        Empresa empresa = getEmpresa(empresaId);

        // Verificar se o usuário existe
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException();
        }

        // Verificar se o usuário é um entregador
        if (!usuario.isEntregador()) {
            throw new UsuarioNaoEntregadorException();
        }

        Entregador entregador = (Entregador) usuario;
        empresa.adicionarEntregador(entregador);
    }

    public String getEntregadores(int empresaId) throws Exception {
        Empresa empresa = mediator.getEmpresaById(empresaId);

        if (empresa == null) {
            throw new EmpresaNaoCadastradaException();
        }

        List<Entregador> entregadores = empresa.getEntregadores();

        StringBuilder sb = new StringBuilder();
        sb.append("{[");
        boolean primeiro = true;
        for (Entregador entregador : entregadores) {
            if (!primeiro) {
                sb.append(", ");
            }
            sb.append(entregador.getEmail());
            primeiro = false;
        }
        sb.append("]}");

        return sb.toString();
    }


    public Empresa getEmpresa(int empresaId) {
        return empresas.get(empresaId);
    }

    public Map<Integer, Empresa> getAllEmpresas() {
        return empresas;
    }

    public String getEmpresas(int entregadorId) throws Exception {
        Usuario usuario = mediator.getUsuarioById(entregadorId);

        if (!usuario.isEntregador()) {
            throw new UsuarioNaoEntregadorException();
        }

        Entregador entregador = (Entregador) usuario;

        StringBuilder sb = new StringBuilder();
        sb.append("{[");

        boolean primeiro = true;

        for (Empresa empresa : empresas.values()) {
            if (empresa.getEntregadores().contains(entregador)) {
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
