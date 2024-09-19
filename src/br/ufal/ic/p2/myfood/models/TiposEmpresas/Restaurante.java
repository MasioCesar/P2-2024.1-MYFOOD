package br.ufal.ic.p2.myfood.models.TiposEmpresas;

import br.ufal.ic.p2.myfood.models.entidades.Empresa;

public class Restaurante extends Empresa {
    private String tipoCozinha;

    public Restaurante() {
        super();
    }

    public Restaurante(int id, String nome, String endereco, int donoId, String dono, String tipoEmpresa, String tipoCozinha) {
        super(id, nome, endereco, donoId, dono, tipoEmpresa);
        this.tipoCozinha = tipoCozinha;
    }

    @Override
    public boolean isRestaurante() {
        return true;
    }

    @Override
    public boolean isMercado() {
        return false;
    }

    @Override
    public boolean isFarmacia() {
        return false;
    }

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }
}
