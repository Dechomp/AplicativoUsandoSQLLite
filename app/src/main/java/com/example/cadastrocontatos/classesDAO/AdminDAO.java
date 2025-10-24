package com.example.cadastrocontatos.classesDAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.cadastrocontatos.classesDTO.Admin;

//Extende o SQL lite
public class AdminDAO extends SQLiteOpenHelper {
    //Crio componentes para o banco de dados
    public static final String NOME_BANCO = "dbCadastrarContatos";
    public static final int VERSAO = 1;
    public static final String NOME_TABELA = "admin";
    public static final String COLUNA_ID = "adm_id";
    public static final String COLUNA_NOME = "adm_nome";
    public static final String COLUNA_SENHA = "adm_senha";

    //Construtor, ele tem que pegar o contexto, o nome do banco, quantidade de dados (cursor), versão
    public AdminDAO(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    //Função obrigatória e extendida
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Quando o banco for criado
        //Simula uma tabela SQL
        // excSQL executa uma criação de tabela
        db.execSQL("CREATE TABLE" + NOME_TABELA + "(" +
                    COLUNA_ID + "INTEGER NOT NULL AUTO INCREMENT," +
                    COLUNA_NOME + "TEXT NOT NULL," +
                    COLUNA_SENHA + "TEXT NOT NULL" +
                    ");"
                );
    }

    //Função obrigatória e extendida
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Quando atualiza a tabela
        //Sem versões adicionais
    }

    //Funções criadas relacionadas ao crud

    //C de create
    public void criarAdmin (Admin adm){
        //Crioa a varável que conecta com o banco, e passo o contexto dele
        SQLiteDatabase db = this.getWritableDatabase();

        //Crio um compenete que recebe os valore sa ser inseridos
        ContentValues valores = new ContentValues();

        //Adicionar os valores
        //Estrutura valores.put (nome da coluna, valor adicionado)
        valores.put(COLUNA_NOME, adm.getNome());
        valores.put(COLUNA_SENHA, adm.getSenha());

        //Adiona os valores no banco estrutura : NOME da tabela, cursor, dados a serem inseridos
        db.insert(NOME_TABELA, null, valores);

        //Fecho a conexão com o banco da deados
        db.close();
    }
}
