package br.ufal.ic.p2.myfood.models.entidades;

import br.ufal.ic.p2.myfood.models.TiposUsuarios.Entregador;

import java.util.ArrayList;
import java.util.List;

public abstract class Empresa {
    private int id;
    private String nome;
    private String endereco;
    private String tipoEmpresa;
    private int donoId;
    private String dono;
    private List<Entregador> entregadores = new ArrayList<>();

    public Empresa(int id, String nome, String endereco, int donoId, String dono, String tipoEmpresa) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoEmpresa = tipoEmpresa;
        this.donoId = donoId;
        this.dono = dono;
    }

    public Empresa() {
    }

    // Métodos Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(String tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public int getDonoId() {
        return donoId;
    }

    public void setDonoId(int donoId) {
        this.donoId = donoId;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public List<Entregador> getEntregadores() {
        return entregadores;
    }

    public void adicionarEntregador(Entregador entregador) {
        entregadores.add(entregador);
    }

    public abstract boolean isRestaurante();

    public abstract boolean isMercado();

    public abstract boolean isFarmacia();
}
