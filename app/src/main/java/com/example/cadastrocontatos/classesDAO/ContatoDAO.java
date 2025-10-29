package com.example.cadastrocontatos.classesDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.cadastrocontatos.Global;
import com.example.cadastrocontatos.classesDTO.Contato;

import java.util.ArrayList;

//Extende o Sql lite
public class ContatoDAO extends SQLiteOpenHelper {
    //Crio componentes para o banco de dados
    public static final String NOME_BANCO = "dbCadastrarContatos";
    public static final int VERSAO = Global.versao;
    public static final String NOME_TABELA = "contato";
    public static final String COLUNA_ID = "ctt_id";
    public static final String COLUNA_NOME = "ctt_nome";
    public static final String COLUNA_CELULAR = "ctt_celular";
    public static final String COLUNA_EMAIL = "ctt_email";
    public static final String COLUNA_ADMID= "adm_id";

    //Construtor, ele tem que pegar o contexto, o nome do banco, quantidade de dados (cursor), versão
    public ContatoDAO(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    //Executa quando for criado
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Quando o banco for criado
        //Simula uma tabela SQL
        // excSQL executa uma criação de tabela
        db.execSQL("CREATE TABLE " + NOME_TABELA + "(" +
                COLUNA_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_NOME    + " TEXT NOT NULL, " +
                COLUNA_CELULAR   + " TEXT NOT NULL," +
                COLUNA_EMAIL   + " TEXT NOT NULL," +
                COLUNA_ADMID   + " INTEGER NOT NULL)"
        );
    }

    //Executa quando for atualizado
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Nada por enquanto
        db.execSQL("DROP TABLE IF EXISTS " + NOME_TABELA);
        onCreate(db);
    }

    //CRUD

    //C de CREATE
    public void criarContato(Contato ctt){
        //Abre a conexão

        SQLiteDatabase db = this.getWritableDatabase();

        //Crio a variável para receber os valores
        ContentValues valores = new ContentValues();

        //Insiro os valores
        valores.put(COLUNA_NOME, ctt.getNome());
        valores.put(COLUNA_CELULAR, ctt.getCelular());
        valores.put(COLUNA_EMAIL, ctt.getEmail());
        valores.put(COLUNA_ADMID, ctt.getAdmId());

        //Insere os valores no banco
        db.insert(NOME_TABELA, null, valores);

        //Fecho a conexão
        db.close();
    }

    //R de read

    //Ler um contato
    public Contato buscarContato(int id){
        //Crio um contato vazio
        Contato ctt = null;

        //Crio os parâmetros
        String parametro[] = {String.valueOf(id)};

        //Quais dados quero receber
        String campos[] = {"ctt_id, ctt_nome, ctt_celular, ctt_email, adm_id"};

        //Conecto ao banco
        SQLiteDatabase db = this.getWritableDatabase();

        //Crio o cursor e faço o select
        Cursor  cr = db.query(
                //Tabela (FROM)
                NOME_TABELA,
                //Dados (SELECT)
                campos,
                //Condição (WHERE)
                "ctt_id = ?",
                //Parâmetro no lugar do "?"
                parametro,
                //Outros (Group by, having, order by"
                null,
                null,
                null
        );
        //Se der certo, haverá como mover para a próxima linha
        if (cr.moveToFirst()){
            ctt = new Contato(
                cr.getInt(0),
                cr.getString(1),
                cr.getString(2),
                cr.getString(3),
                cr.getInt(4)
            );
        }

        //Fecha a conexão
        db.close();

        //Retorna o contato
        return ctt;
    }

    //Lista de contatos
    public ArrayList<Contato> listarContatos(int admId){
        //Crio um contato vazio
        Contato ctt = null;

        //Crio uma lista vazia
        ArrayList<Contato> contatos = new ArrayList<>();

        //Crio os parâmetros
        String parametro[] = {String.valueOf(admId)};

        //Quais dados quero receber
        String campos[] = {"ctt_id, ctt_nome, ctt_celular, ctt_email, adm_id"};

        //Conecto ao banco
        SQLiteDatabase db = this.getWritableDatabase();

        //Crio o cursor e faço o select
        Cursor  cr = db.query(
                //Tabela (FROM)
                NOME_TABELA,
                //Dados (SELECT)
                campos,
                //Condição (WHERE)
                "adm_id = ?",
                //Parâmetro no lugar do "?"
                parametro,
                //Outros (Group by, having, order by"
                null,
                null,
                null
        );

        //Se der certo, haverá como mover para a próxima linha
        while (cr.moveToNext()){
            //Crio o contato
            ctt = new Contato(
                    cr.getInt(0),
                    cr.getString(1),
                    cr.getString(2),
                    cr.getString(3),
                    cr.getInt(4)
            );

            //Adiciono a lista
            contatos.add(ctt);
        }

        //Fecha a conexão
        db.close();

        //Retorna a lsita
        return contatos;
    }

    //U de update
    public void atualizarContato(Contato ctt){
        //Abro a conexão
        SQLiteDatabase db = this.getWritableDatabase();

        //Crio um lista valores a serem alterados
        ContentValues valores = new ContentValues();

        //Adiciono os valores
        valores.put(COLUNA_NOME, ctt.getNome());
        valores.put(COLUNA_CELULAR, ctt.getCelular());
        valores.put(COLUNA_EMAIL, ctt.getEmail());

        //Não atualiza o admId

        //Parâmetro
        String parametro[] = {String.valueOf(ctt.getId())};

        //Atualizo od dados
        db.update(
                //Nome da tabela (UPDATE)
                NOME_TABELA,

                //SET valores a serem alterados
                valores,

                //WHERE (Condição)
                "ctt_id = ?",

                //Valor que vai no lugar do "?"
                parametro);

        //Fecha a conexão
        db.close();
    }
    //FUnção para atualizar o adm id (Só vou usar quando o usuário colocar para desfazer o apagamento da conta)

    //D de delete
    public void excluirContato(int id){
        //Abre a conexão
        SQLiteDatabase db = this.getWritableDatabase();

        //Cria os parâmetros
        String parametro[] = {String.valueOf(id)};

        //Manda deletar o contato
        db.delete(
                //Nome da tabela
                NOME_TABELA,

                //Condição
                "ctt_id = ?",

                //Parâmetro
                parametro
        );

        //Fecha a conexão
        db.close();
    }


}
