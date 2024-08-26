package br.ufal.ic.p2.myfood.services;

import br.ufal.ic.p2.myfood.XMLFunction.XMLFacade;
import br.ufal.ic.p2.myfood.tipousuario.Empresa;
import br.ufal.ic.p2.myfood.tipousuario.Pedido;
import br.ufal.ic.p2.myfood.tipousuario.Produto;
import br.ufal.ic.p2.myfood.tipousuario.User;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

public class PedidoManager {
    private Map<Integer, Pedido> pedidosPorCliente = new HashMap<>();
    private UserManager userManager;
    private EmpresaManager empresaManager;
    private ProdutoManager produtoManager;

    private int pedidoNumero = 0;

    public PedidoManager(EmpresaManager empresaManager, ProdutoManager produtoManager, UserManager userManager) {
        this.empresaManager = empresaManager;
        this.produtoManager = produtoManager;
        this.userManager = userManager;
    }

    public void zerarSistema() {
        pedidosPorCliente.clear();
        pedidoNumero = 0;
        XMLFacade.salvarPedidos(pedidosPorCliente); // Persistir a limpeza do sistema
    }

    public void setPedidos(Map<Integer, Pedido> pedidosPorCliente) {
        this.pedidosPorCliente = pedidosPorCliente;
    }

    public int criarPedido(int clienteId, int empresaId) {
        if (empresaManager.isDonoEmpresa(clienteId, empresaId)) {
            throw new IllegalArgumentException("Dono de empresa nao pode fazer um pedido");
        }

        for (Pedido pedido : pedidosPorCliente.values()) {
            if (pedido.getCliente() == clienteId && pedido.getEmpresa() == empresaId && pedido.getEstado().equals("aberto")) {
                throw new IllegalArgumentException("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
            }
        }

        int numero = pedidoNumero++;
        Pedido pedido = new Pedido(numero, clienteId, empresaId, "aberto");
        pedidosPorCliente.put(numero, pedido);

        XMLFacade.salvarPedidos(pedidosPorCliente);
        return numero;
    }
/*
    public int getNumeroPedido(int cliente, int empresa, int indice) {
        return pedidos.values().stream()
                .filter(p -> p.getCliente().equals(cliente) && p.getEmpresa().equals(empresa) && p.getEstado().equals("aberto"))
                .skip(indice)
                .map(Pedido::getNumero)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Pedido nao encontrado"));
    }*/

    public void adicionarProduto(int numeroPedido, int produtoId) {
        Pedido pedido = pedidosPorCliente.get(numeroPedido);

        if (pedido == null) {
            throw new IllegalArgumentException("Nao existe pedido em aberto");
        }

        if (pedido.getEstado().equals("preparando")) {
            throw new IllegalArgumentException("Nao e possivel adcionar produtos a um pedido fechado");
        }

        Empresa empresaDoPedido = empresaManager.getEmpresa(pedido.getEmpresa());

        Produto produto = getProdutoPorId(produtoId);
        if (produto == null) {
            throw new IllegalArgumentException("Produto nao encontrado");
        }

        if (!produto.getEmpresa().equals(empresaDoPedido.getNome())) {
            throw new IllegalArgumentException("O produto nao pertence a essa empresa");
        }

        pedido.adicionarProduto(produto);
        XMLFacade.salvarPedidos(pedidosPorCliente); // Persistir alterações
    }

    public String getPedidos(int pedidoId, String atributo) {
        Pedido pedido = pedidosPorCliente.get(pedidoId);

        if (pedido == null) {
            throw new IllegalArgumentException("Pedido nao encontrado");
        }

        if (atributo == null) {
            throw new IllegalArgumentException("Atributo invalido");
        }

        switch (atributo) {
            case "cliente":
                User cliente = userManager.getUser(pedido.getCliente());
                if (cliente == null) {
                    throw new IllegalArgumentException("Cliente nao encontrado");
                }
                return cliente.getNome();

            case "empresa":
                Empresa empresa = empresaManager.getEmpresa(pedido.getEmpresa());
                if (empresa == null) {
                    throw new IllegalArgumentException("Empresa nao encontrada");
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
                throw new IllegalArgumentException("Atributo invalido");
        }
    }

    public void fecharPedido(int numero) {
        Pedido pedido = pedidosPorCliente.get(numero);
        if (pedido == null || !pedido.getEstado().equals("aberto")) {
            throw new IllegalArgumentException("Pedido nao encontrado");
        }

        pedido.setEstado("preparando");
        XMLFacade.salvarPedidos(pedidosPorCliente); // Persistir alterações
    }

    public void removerProduto(int numero, String nomeProduto) {
        if (nomeProduto == null || nomeProduto.trim().isEmpty()) {
            throw new IllegalArgumentException("Produto invalido");
        }

        Pedido pedido = pedidosPorCliente.get(numero);

        if (pedido == null || !pedido.getEstado().equals("aberto")) {
            throw new IllegalArgumentException("Nao e possivel remover produtos de um pedido fechado");
        }

        pedido.removerProduto(nomeProduto);

        XMLFacade.salvarPedidos(pedidosPorCliente);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) {
        // Obtém todos os pedidos do cliente
        List<Pedido> pedidos = pedidosPorCliente.values().stream()
                .filter(p -> p.getCliente() == cliente && p.getEmpresa() == empresa)
                .toList();

        // Verifica se o índice é válido
        if (indice >= 0 && indice < pedidos.size()) {
            // Retorna o número do pedido no índice especificado
            return pedidos.get(indice).getNumero();
        } else {
            throw new IllegalArgumentException("Índice do pedido inválido");
        }
    }

    public void encerrarSistema() {
        XMLFacade.salvarPedidos(pedidosPorCliente);

    }

    private Produto getProdutoPorId(int produtoId) {
        return produtoManager.getProdutoPorId(produtoId);
    }
}
