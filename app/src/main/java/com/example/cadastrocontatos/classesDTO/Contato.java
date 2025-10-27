package com.example.cadastrocontatos.classesDTO;

public class Contato {
    //Criando os atributos
    private int id;
    private String nome;
    private String celular ;
    private String email;
    private int admId;


    //Construtor
    public Contato() {
    }

    //Construtor com parâmetros exceto id

    public Contato(String nome, String celular, String email, int admId) {
        this.nome = nome;
        this.celular = celular;
        this.email = email;
        this.admId = admId;
    }


    //Construtor com parâmetros e id

    public Contato(int id, String nome, String celular, String email, int admId) {
        this.id = id;
        this.nome = nome;
        this.celular = celular;
        this.email = email;
        this.admId = admId;
    }


    //Métodos get e set

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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getAdmId() {
        return admId;
    }

    public void setAdmId(int admId) {
        this.admId = admId;
    }
}
