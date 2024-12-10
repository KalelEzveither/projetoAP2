package com.example.projetoap2;

import java.io.Serializable;

public class Conta implements Serializable {
    private static final long serialVersionUID = 1L; // Adicione este ID para compatibilidade futura

    private String login;
    private String senha;

    public Conta(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
}
