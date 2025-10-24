package com.example.cadastrocontatos.classesDTO;

public class Admin {
    //Dados do Admin
    private int id;
    private String nome;
    private String senha;

    //Contrutores
    public Admin(){
        //Construtor vazio
    }

    //Construtor com parâmetros
    public Admin(int id, String nome, String senha) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
    }

    //Gets e Sets

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
