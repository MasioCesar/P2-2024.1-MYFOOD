package br.ufal.ic.p2.myfood.services;

import br.ufal.ic.p2.myfood.exceptions.Produto.*;
import br.ufal.ic.p2.myfood.services.XMLFunctions.XMLProduto;
import br.ufal.ic.p2.myfood.models.Empresa;
import br.ufal.ic.p2.myfood.models.Produto;
import br.ufal.ic.p2.myfood.utils.Validate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProdutoManager {
    private Map<Integer, Map<Integer, Produto>> produtosPorEmpresa = new HashMap<>();
    private final EmpresaManager empresaManager;
    private int nextProductId = 0;

    public ProdutoManager(EmpresaManager empresaManager) {
        this.empresaManager = empresaManager;
    }

    public void setProdutosPorEmpresa(Map<Integer, Map<Integer, Produto>> produtosPorEmpresa) {
        this.produtosPorEmpresa = produtosPorEmpresa;
    }

    public int criarProduto(int empresaId, String nome, float valor, String categoria) throws Exception {
        Validate.validarProduto(nome, valor, categoria);

        Map<Integer, Produto> produtos = produtosPorEmpresa.getOrDefault(empresaId, new HashMap<>());
        Empresa empresa = empresaManager.getEmpresa(empresaId);

        for (Produto p : produtos.values()) {
            if (p.getNome().equals(nome)) {
                throw new ProdutoJaExisteException();
            }
        }

        int id = nextProductId++;
        Produto produto = new Produto(id, nome, valor, categoria, empresa.getNome());
        produtos.put(id, produto);
        produtosPorEmpresa.put(empresaId, produtos);
        XMLProduto.saveProdutos(produtosPorEmpresa);
        return id;
    }

    public void editarProduto(int produtoId, String nome, float valor, String categoria) throws Exception {
        // Find the product across all companies
        Produto produto = null;
        for (Map<Integer, Produto> produtos : produtosPorEmpresa.values()) {
            if (produtos.containsKey(produtoId)) {
                produto = produtos.get(produtoId);
                break;
            }
        }

        if (produto == null) {
            throw new ProdutoNaoCadastradoException();
        }
        Validate.validarProduto(nome, valor, categoria);

        produto.setNome(nome);
        produto.setValor(valor);
        produto.setCategoria(categoria);

        XMLProduto.saveProdutos(produtosPorEmpresa);
    }

    public String getProduto(String nome, int empresaId, String atributo) throws Exception {
        Map<Integer, Produto> produtos = produtosPorEmpresa.get(empresaId);

        if (produtos == null) {
            throw new EmpresaNaoPossuiProdutosException();
        }

        Produto produto = produtos.values().stream()
                .filter(p -> p.getNome().equals(nome))
                .findFirst()
                .orElseThrow(ProdutoNaoEncontradoException::new);

        return switch (atributo) {
            case "nome" -> produto.getNome();
            case "valor" -> {
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
                symbols.setDecimalSeparator('.');
                DecimalFormat df = new DecimalFormat("#.00", symbols);
                yield df.format(produto.getValor());
            }
            case "categoria" -> produto.getCategoria();
            case "empresa" -> produto.getEmpresa();
            default -> throw new AtributoNaoExisteException();
        };
    }

    public String listarProdutos(int empresaId) throws Exception {
        Map<Integer, Produto> produtos = produtosPorEmpresa.get(empresaId);

        Empresa empresa = empresaManager.getEmpresa(empresaId);
        if (empresa == null) {
            throw new EmpresaNaoEncontradaException();
        }

        if (produtos == null || produtos.isEmpty()) {
            return "{[]}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{[");
        boolean primeiro = true;
        for (Produto p : produtos.values()) {
            if (!primeiro) {
                sb.append(", ");
            }
            sb.append(p.getNome());
            primeiro = false;
        }
        sb.append("]}");

        return sb.toString();
    }

    public Produto getProdutoPorId(int produtoId) {
        for (Map<Integer, Produto> produtos : produtosPorEmpresa.values()) {
            Produto produto = produtos.get(produtoId);
            if (produto != null) {
                return produto;
            }
        }
        return null;
    }

    public void zerarSistema() {
        produtosPorEmpresa.clear();
        nextProductId = 0;
    }
}
