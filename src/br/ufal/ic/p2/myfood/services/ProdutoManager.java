package br.ufal.ic.p2.myfood.services;

import br.ufal.ic.p2.myfood.services.XMLFunctions.XMLProduto;
import br.ufal.ic.p2.myfood.models.Empresa;
import br.ufal.ic.p2.myfood.models.Produto;

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

    public int criarProduto(int empresaId, String nome, float valor, String categoria) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome invalido");
        }

        // Validate the product value
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor invalido");
        }

        // Validate the product category
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria invalido");
        }

        Map<Integer, Produto> produtos = produtosPorEmpresa.getOrDefault(empresaId, new HashMap<>());
        Empresa empresa = empresaManager.getEmpresa(empresaId);

        for (Produto p : produtos.values()) {
            if (p.getNome().equals(nome)) {
                throw new IllegalArgumentException("Ja existe um produto com esse nome para essa empresa");
            }
        }

        int id = nextProductId++;
        Produto produto = new Produto(id, nome, valor, categoria, empresa.getNome());
        produtos.put(id, produto);
        produtosPorEmpresa.put(empresaId, produtos);
        XMLProduto.saveProdutos(produtosPorEmpresa);
        return id;
    }


    public void editarProduto(int produtoId, String nome, float valor, String categoria) {
        // Find the product across all companies
        Produto produto = null;
        for (Map<Integer, Produto> produtos : produtosPorEmpresa.values()) {
            if (produtos.containsKey(produtoId)) {
                produto = produtos.get(produtoId);
                break;
            }
        }

        if (produto == null) {
            throw new IllegalArgumentException("Produto nao cadastrado");
        }

        // Validate the product name
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome invalido");
        }

        // Validate the product value
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor invalido");
        }

        // Validate the product category
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria invalido");
        }

        // Update the product attributes
        produto.setNome(nome);
        produto.setValor(valor);
        produto.setCategoria(categoria);

        XMLProduto.saveProdutos(produtosPorEmpresa);
    }



    public String getProduto(String nome, int empresaId, String atributo) {
        Map<Integer, Produto> produtos = produtosPorEmpresa.get(empresaId);

        if (produtos == null) {
            throw new IllegalArgumentException("Empresa nao possui produtos.");
        }

        Produto produto = produtos.values().stream()
                .filter(p -> p.getNome().equals(nome))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Produto nao encontrado"));

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
            default -> throw new IllegalArgumentException("Atributo nao existe");
        };
    }

    public String listarProdutos(int empresaId) {
        Map<Integer, Produto> produtos = produtosPorEmpresa.get(empresaId);

        Empresa empresa = empresaManager.getEmpresa(empresaId);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa nao encontrada");
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
        return null; // Retorna null se o produto não for encontrado
    }

    public void zerarSistema() {
        produtosPorEmpresa.clear();
        nextProductId = 0;
    }
}
