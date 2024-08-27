package br.ufal.ic.p2.myfood.models;

public class Empresa {
    private int id;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private String tipoEmpresa;
    private int donoId;
    private String dono;

    public Empresa(int id, String nome, String endereco, String tipoCozinha, int donoId, String dono, String tipoEmpresa) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.tipoEmpresa = tipoEmpresa;
        this.donoId = donoId;
        this.dono = dono;
    }

    public Empresa() {

    }

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

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public String getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(String tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
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

}
