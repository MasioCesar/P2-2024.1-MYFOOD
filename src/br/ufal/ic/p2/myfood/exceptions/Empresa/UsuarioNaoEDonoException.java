package br.ufal.ic.p2.myfood.exceptions.Empresa;

public class UsuarioNaoEDonoException extends Exception {
    public UsuarioNaoEDonoException() {
        super("Usuario nao é um dono de restaurante.");
    }
}