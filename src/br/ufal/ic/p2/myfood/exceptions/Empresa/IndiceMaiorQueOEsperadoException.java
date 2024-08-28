package br.ufal.ic.p2.myfood.exceptions.Empresa;

public class IndiceMaiorQueOEsperadoException extends IllegalArgumentException {
    public IndiceMaiorQueOEsperadoException() {
        super("Indice maior que o esperado");
    }
}