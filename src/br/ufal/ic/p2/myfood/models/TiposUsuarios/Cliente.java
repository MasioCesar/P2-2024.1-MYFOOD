package br.ufal.ic.p2.myfood.models.TiposUsuarios;

import br.ufal.ic.p2.myfood.models.Usuario;

public class Cliente extends Usuario {
    public Cliente() {
        super();
    }

    public Cliente(int id, String nome, String email, String senha, String endereco) {
        super(id, nome, email, senha, endereco);
    }
}
