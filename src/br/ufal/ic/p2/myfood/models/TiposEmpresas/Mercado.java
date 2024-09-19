package br.ufal.ic.p2.myfood.models.TiposEmpresas;

import br.ufal.ic.p2.myfood.models.entidades.Empresa;

public class Mercado extends Empresa {
    private String horarioAbertura;
    private String horarioFechamento;
    private String tipoMercado;

    public Mercado() {
        super();
    }

    public Mercado(int id, String nome, String endereco, String horarioAbertura, String horarioFechamento, String tipoMercado, int donoId, String dono, String tipoEmpresa) {
        super(id, nome, endereco, donoId, dono, tipoEmpresa);
        this.horarioAbertura = horarioAbertura;
        this.horarioFechamento = horarioFechamento;
        this.tipoMercado = tipoMercado;
    }

    public String getHorarioAbertura() {
        return horarioAbertura;
    }

    public void setHorarioAbertura(String horarioAbertura) {
        this.horarioAbertura = horarioAbertura;
    }

    public String getHorarioFechamento() {
        return horarioFechamento;
    }

    public void setHorarioFechamento(String horarioFechamento) {
        this.horarioFechamento = horarioFechamento;
    }

    public String getTipoMercado() {
        return tipoMercado;
    }

    public void setTipoMercado(String tipoMercado) {
        this.tipoMercado = tipoMercado;
    }

    @Override
    public boolean isRestaurante() {
        return false;
    }

    @Override
    public boolean isMercado() {
        return true;
    }

    @Override
    public boolean isFarmacia() {
        return false;
    }
}
