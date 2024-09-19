package br.ufal.ic.p2.myfood.exceptions.Empresa;

public class NaoEMercadoValidoException extends Exception {
    public NaoEMercadoValidoException() {
        super("Nao e um mercado valido");
    }
}
