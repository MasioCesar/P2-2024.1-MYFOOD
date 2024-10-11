package br.ufal.ic.p2.myfood.services.managers;

import br.ufal.ic.p2.myfood.exceptions.Pedido.*;
import br.ufal.ic.p2.myfood.exceptions.Usuario.NaoEntregadorValidoException;
import br.ufal.ic.p2.myfood.exceptions.Usuario.UsuarioNaoEntregadorException;
import br.ufal.ic.p2.myfood.models.TiposUsuarios.Entregador;
import br.ufal.ic.p2.myfood.models.entidades.*;
import br.ufal.ic.p2.myfood.services.XMLFunctions.XMLPedido;
import br.ufal.ic.p2.myfood.services.mediator.Mediator;

import java.util.*;
import java.util.stream.Collectors;

public class PedidoManager {
    // Instância única da classe PedidoManager
    private static PedidoManager instance;

    private Map<Integer, Pedido> pedidosPorCliente;
    private Mediator mediator;

    private int pedidoNumero = 0;
    private int contadorEntrega = 0;

    public PedidoManager(Mediator mediator) {
        this.mediator = mediator;
        this.pedidosPorCliente = XMLPedido.load();
    }

    public static PedidoManager getInstance(Mediator mediator) {
        if (instance == null) {
            instance = new PedidoManager(mediator);
        }
        return instance;
    }

    public void zerarSistema() {
        pedidosPorCliente.clear();
        pedidoNumero = 0;
        contadorEntrega = 0;
        XMLPedido.save(pedidosPorCliente); // Persistir a limpeza do sistema
    }

    public int criarPedido(int clienteId, int empresaId) throws Exception {
        if (mediator.isDonoEmpresa(clienteId, empresaId)) {
            throw new DonoEmpresaNaoPodeFazerPedidoException();
        }

        // Verifica se já existe um pedido aberto para o cliente na mesma empresa
        for (Pedido pedido : pedidosPorCliente.values()) {
            if (pedido.getCliente() == clienteId && pedido.getEmpresa() == empresaId && pedido.getEstado().equals("aberto")) {
                throw new PedidoEmAbertoException();
            }
        }

        int numero = pedidoNumero++;
        Pedido pedido = new Pedido(numero, clienteId, empresaId, "aberto");
        pedidosPorCliente.put(numero, pedido);

        return numero;
    }

    public void adicionarProduto(int numeroPedido, int produtoId) throws Exception {
        Pedido pedido = pedidosPorCliente.get(numeroPedido);

        if (pedido == null) {
            throw new NaoExistePedidoEmAberto();
        }

        if (pedido.getEstado().equals("preparando")) {
            throw new NaoEPossivelAdcionarProdutosAUmPedidoFechado();
        }

        Empresa empresaDoPedido = mediator.getEmpresaById(pedido.getEmpresa());

        // Obtém o produto usando o Mediator
        Produto produto = mediator.getProdutoById(produtoId);

        if (produto == null) {
            throw new ProdutoNaoEncontradoException();
        }

        if (!produto.getEmpresa().equals(empresaDoPedido.getNome())) {
            throw new ProdutoNaoPertenceEmpresaException();
        }

        pedido.adicionarProduto(produto);
    }

    public String getPedidos(int pedidoId, String atributo) throws Exception {
        Pedido pedido = pedidosPorCliente.get(pedidoId);

        if (pedido == null) {
            throw new PedidoNaoEncontradoException();
        }

        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        }

        switch (atributo) {
            case "cliente":
                Usuario cliente = mediator.getUsuarioById(pedido.getCliente());
                if (cliente == null) {
                    throw new ClienteNaoEncontradoException();
                }
                return cliente.getNome();

            case "empresa":
                Empresa empresa = mediator.getEmpresaById(pedido.getEmpresa());
                if (empresa == null) {
                    throw new EmpresaNaoEncontradaException();
                }
                return empresa.getNome();

            case "estado":
                return pedido.getEstado();

            case "produtos":
                List<Produto> produtos = pedido.getProdutos();
                return produtos.stream()
                        .map(Produto::getNome)
                        .collect(Collectors.joining(", ", "{[", "]}"));

            case "valor":
                double valorTotal = pedido.calcularValor();
                return String.format(Locale.US, "%.2f", valorTotal);

            default:
                throw new AtributoNaoExisteException();
        }
    }

    public void fecharPedido(int numero) throws Exception {
        Pedido pedido = pedidosPorCliente.get(numero);
        if (pedido == null || !pedido.getEstado().equals("aberto")) {
            throw new PedidoNaoEncontradoException();
        }

        pedido.setEstado("preparando");
    }

    public void removerProduto(int numero, String nomeProduto) throws Exception {
        if (nomeProduto == null || nomeProduto.trim().isEmpty()) {
            throw new ProdutoInvalidoException();
        }

        Pedido pedido = pedidosPorCliente.get(numero);

        if (pedido == null || !pedido.getEstado().equals("aberto")) {
            throw new NaoPodeRemoverProdutoDePedidoFechadoException();
        }

        pedido.removerProduto(nomeProduto);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) throws Exception {
        List<Pedido> pedidos = pedidosPorCliente.values().stream()
                .filter(p -> p.getCliente() == cliente && p.getEmpresa() == empresa)
                .toList();

        if (indice >= 0 && indice < pedidos.size()) {
            return pedidos.get(indice).getNumero();
        } else {
            throw new IndicePedidoInvalidoException();
        }
    }

    public Pedido getPedidoById(int id) {
        return pedidosPorCliente.get(id);
    }

    public void liberarPedido(int numeroPedido) throws Exception {
        Pedido pedido = getPedidoById(numeroPedido);

        if (Objects.equals(pedido.getEstado(), "pronto")) {
            throw new PedidoJaLiberadoException();
        }

        if (!Objects.equals(pedido.getEstado(), "preparando")) {
            throw new NaoPossivelLiberarProdutoNaoPreparadoException();
        }

        pedido.setEstado("pronto");
    }

    public List<Pedido> getPedidosByEmpresaId(int empresaId) {
        return pedidosPorCliente.values().stream()
                .filter(pedido -> pedido.getEmpresa() == empresaId)
                .collect(Collectors.toList());
    }

    public int obterPedido(int entregadorId) throws Exception {
        Usuario usuario = mediator.getUsuarioById(entregadorId);

        if (!usuario.isEntregador()) {
            throw new UsuarioNaoEntregadorException();
        }

        Entregador entregador = (Entregador) usuario;
        Pedido pedidoPrioritario = null;

        boolean isInAnyCompany = false;

        for (Map.Entry<Integer, Empresa> entry : mediator.getAllEmpresas().entrySet()) {
            Empresa empresa = entry.getValue();

            if (empresa.getEntregadores().contains(entregador)) {
                isInAnyCompany = true;

                List<Pedido> pedidosEmpresa = getPedidosByEmpresaId(empresa.getId());

                for (Pedido pedido : pedidosEmpresa) {
                    if (pedido.getEstado().equals("pronto")) {
                        if (empresa.isFarmacia() || (pedidoPrioritario == null && !empresa.isFarmacia())) {
                            pedidoPrioritario = pedido;
                        }
                    }
                }
            }
        }

        if (!isInAnyCompany) {
            throw new EntregadorNaoEstaNenhumaEmpresaException();
        }

        if (pedidoPrioritario == null) {
            throw new NaoExistePedidoParaEntregaException();
        }

        return pedidoPrioritario.getNumero();
    }

    // FUNCOES DE ENTREGA

    public int criarEntrega(int pedidoNumero, int entregadorId, String destino) throws Exception {
        Usuario usuario = mediator.getUsuarioById(entregadorId);
        Pedido pedido = getPedidoById(pedidoNumero);


        if (!pedido.getEstado().equals("pronto")) {
            throw new PedidoNaoProntoParaEntregaException();
        }

        if (!usuario.isEntregador()) {
            throw new NaoEntregadorValidoException();
        }

        if (((Entregador) usuario).estaOcupado()) {
            throw new EntregadorEmEntregaException();
        }

        if (destino == null || destino.trim().isEmpty()) {
            Usuario cliente = mediator.getUsuarioById(pedido.getCliente());
            destino = cliente.getEndereco();
        }

        int entregaId = contadorEntrega++;

        Entrega entrega = new Entrega(entregaId, pedido, entregadorId, destino, mediator);

        pedido.iniciarEntrega(entrega);

        ((Entregador) usuario).setOcupado(true);

        return entregaId;
    }

    public Entrega getEntregaById(int entregaId) throws Exception {
        for (Pedido pedido : pedidosPorCliente.values()) {
            if (pedido.getEntrega() != null && pedido.getEntrega().getEntregaId() == entregaId) {
                return pedido.getEntrega();
            }
        }
        throw new NaoExisteEntregaException();
    }

    public Object getAtributoEntrega(int entregaId, String atributo) throws Exception {
        Entrega entrega = getEntregaById(entregaId);

        if (atributo == null || atributo.trim().isEmpty()) {
            throw new AtributoInvalidoException();
        }

        return switch (atributo.toLowerCase()) {
            case "cliente" -> mediator.getUsuarioById(entrega.getPedido().getCliente()).getNome();
            case "empresa" -> mediator.getEmpresaById(entrega.getPedido().getEmpresa()).getNome();
            case "pedido" -> String.valueOf(entrega.getPedido().getNumero());
            case "estado" -> entrega.getPedido().getEstado();
            case "destino" -> entrega.getDestino();
            case "entregador" -> mediator.getUsuarioById(entrega.getEntregadorId()).getNome();
            case "produtos" -> "{[" + entrega.getPedido().getProdutos().stream()
                    .map(Produto::getNome)
                    .collect(Collectors.joining(", ")) + "]}";
            default -> throw new AtributoInvalidoException();
        };
    }

    public int getIdEntrega(int pedidoNumero) throws Exception {
        for (Pedido pedido : pedidosPorCliente.values()) {
            if (pedido.getNumero() == pedidoNumero) {
                if (pedido.getEntrega() != null) {
                    return pedido.getEntrega().getEntregaId();
                } else {
                    throw new NaoExisteEntregaException();
                }
            }
        }
        throw new NaoExisteEntregaException();
    }

    public void entregar(int entregaId) throws Exception {
        Entrega entrega = getEntregaById(entregaId);

        entrega.finalizarEntrega();
    }

    public void salvarDados() {
        XMLPedido.save(pedidosPorCliente);
    }
}
