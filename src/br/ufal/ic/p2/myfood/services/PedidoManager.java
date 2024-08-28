package br.ufal.ic.p2.myfood.services;

import br.ufal.ic.p2.myfood.exceptions.Pedido.*;
import br.ufal.ic.p2.myfood.services.XMLFunctions.XMLPedido;
import br.ufal.ic.p2.myfood.models.Empresa;
import br.ufal.ic.p2.myfood.models.Pedido;
import br.ufal.ic.p2.myfood.models.Produto;
import br.ufal.ic.p2.myfood.models.Usuario;

import java.util.*;
import java.util.stream.Collectors;

public class PedidoManager {
    private Map<Integer, Pedido> pedidosPorCliente = new HashMap<>();
    private final UsuarioManager usuarioManager;
    private final EmpresaManager empresaManager;
    private final ProdutoManager produtoManager;

    private int pedidoNumero = 0;

    public PedidoManager(EmpresaManager empresaManager, ProdutoManager produtoManager, UsuarioManager usuarioManager) {
        this.empresaManager = empresaManager;
        this.produtoManager = produtoManager;
        this.usuarioManager = usuarioManager;
    }

    public void zerarSistema() {
        pedidosPorCliente.clear();
        pedidoNumero = 0;
        XMLPedido.savePedidos(pedidosPorCliente); // Persistir a limpeza do sistema
    }

    public void setPedidos(Map<Integer, Pedido> pedidosPorCliente) {
        this.pedidosPorCliente = pedidosPorCliente;
    }

    public int criarPedido(int clienteId, int empresaId) throws Exception {
        if (empresaManager.isDonoEmpresa(clienteId, empresaId)) {
            throw new DonoEmpresaNaoPodeFazerPedidoException();
        }

        for (Pedido pedido : pedidosPorCliente.values()) {
            if (pedido.getCliente() == clienteId && pedido.getEmpresa() == empresaId && pedido.getEstado().equals("aberto")) {
                throw new PedidoEmAbertoException();
            }
        }

        int numero = pedidoNumero++;
        Pedido pedido = new Pedido(numero, clienteId, empresaId, "aberto");
        pedidosPorCliente.put(numero, pedido);

        XMLPedido.savePedidos(pedidosPorCliente);
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

        Empresa empresaDoPedido = empresaManager.getEmpresa(pedido.getEmpresa());

        Produto produto = getProdutoPorId(produtoId);
        if (produto == null) {
            throw new ProdutoNaoEncontradoException();
        }

        if (!produto.getEmpresa().equals(empresaDoPedido.getNome())) {
            throw new ProdutoNaoPertenceEmpresaException();
        }

        pedido.adicionarProduto(produto);
        XMLPedido.savePedidos(pedidosPorCliente);
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
                Usuario cliente = usuarioManager.getUser(pedido.getCliente());
                if (cliente == null) {
                    throw new ClienteNaoEncontradoException();
                }
                return cliente.getNome();

            case "empresa":
                Empresa empresa = empresaManager.getEmpresa(pedido.getEmpresa());
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
                // Format the value to ensure two decimal places
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
        XMLPedido.savePedidos(pedidosPorCliente);
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

        XMLPedido.savePedidos(pedidosPorCliente);
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

    public void encerrarSistema() {
        XMLPedido.savePedidos(pedidosPorCliente);
    }

    private Produto getProdutoPorId(int produtoId) {
        return produtoManager.getProdutoPorId(produtoId);
    }
}