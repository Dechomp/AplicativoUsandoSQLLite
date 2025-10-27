package com.example.cadastrocontatos.classesDAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        db.execSQL("CREATE TABLE " + NOME_TABELA + "(" +
                    COLUNA_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUNA_NOME    + " TEXT NOT NULL, " +
                    COLUNA_SENHA   + " TEXT NOT NULL) "
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
        //Só funcionar se não exitir aquele admin

        //Crio a variável que conecta com o banco, e passo o contexto dele
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

    //R de read
    public Admin buscarAdmin(String nome){
        //Crio um admin vazio
        Admin adm = null;

        //Crio o parâmetro para a busca
        String parametro[] = {nome};

        //Campos de quero
        String campos[] = {"adm_id, adm_nome, adm_senha"};

        //Contexto do banco
        SQLiteDatabase db = this.getWritableDatabase();

        //Cria um cursor(quantidade de dados recebidos) e faz o select
        Cursor cursor = db.query(
                //Tabela(FROM)
                NOME_TABELA,
                //Dados (SELECT)
                campos,
                //Condição(WHERE)
                "adm_nome = ?",
                //Dado que vai no lugar do WHERE
                parametro,
                //Outros,(Group by, having, oder by) neste ordem, mas no caso, tudo vazio
                null,
                null,
                null
                );
        //Se der certo, haverá como mover para a próxima linha
        if (cursor.moveToNext()){
            adm = new Admin(
                    //ID
                    cursor.getInt(0),
                    //Nome
                    cursor.getString(1),
                    //Senha
                    cursor.getString(2)
            );
        }

        //Fecha a conexão com o banco
        db.close();

        //Retorna o ADM
        return adm;

        //Quando chamar, se o retorno for vazio, anuncia que deu errado a busca
        //Se não for, continua normal
    }

    //Criando um outro read só para verificar se já existe um admin com o mesmo nome
    public Boolean checarExistencia(String nome){

        //Abre a conexão
        SQLiteDatabase db = this.getWritableDatabase();

        //Faz um select com count, não existir, vai ter 0 no count, ou seja, vai retornar falso
        //Se tiver, vai retornar 1, ou seja, já existe(Sei que não vai retornar mais de um pois na próxima vez
        // já vai existir alguém, impedindo de retornar a mais que 1)

        String parametros[] = {nome};

        //Campos (neste caso, o count)
        String campos[] = {"count(*)"};

        //Crio o cursor
        Cursor cursor = db.query(
                //Tabela
                NOME_TABELA,
                //Dados que queremos(no nosso caso o count)
                campos,
                //Condição
                "adm_nome = ?",
                //Parâ,etro no lugar do "?"
                parametros,
                //Group by e outros, no nosso caso, teoricamente não precisa, mas vamos colocar do mesmo jeito
                //Teoricamente não precisa pois só temos um dado, o count(*), então não precisariamos contar
                //Agora, po rexepplo, se quisermos contar o número de adms com a mesmo nome, teriamos que colocar
                //No group by, o  adm_nome
                null,
                null,
                null
        );

        if (cursor.moveToNext()){
            if (cursor.getInt(0) == 1){
                return true;
            }
        }
        return false;
    }

    //Função para checar Usuário e senha
    public Boolean checarUsuarioSenha(Admin adm){
        //Crio um admin vazio
        Admin admExistente = null;

        //Crio o parâmetro para a busca
        String parametro[] = {adm.getNome(), adm.getSenha()};

        //Campos de quero
        String campos[] = {"adm_id, adm_nome, adm_senha"};

        //Contexto do banco
        SQLiteDatabase db = this.getWritableDatabase();

        //Cria um cursor(quantidade de dados recebidos) e faz o select
        Cursor cursor = db.query(
                //Tabela(FROM)
                NOME_TABELA,
                //Dados (SELECT)
                campos,
                //Condição(WHERE)
                "adm_nome = ? AND adm_senha = ?",
                //Dado que vai no lugar do WHERE
                parametro,
                //Outros,(Group by, having, oder by) neste ordem, mas no caso, tudo vazio
                null,
                null,
                null
        );
        //Se der certo, haverá como mover para a próxima linha
        if (cursor.moveToNext()){
            admExistente = new Admin(
                    //ID
                    cursor.getInt(0),
                    //Nome
                    cursor.getString(1),
                    //Senha
                    cursor.getString(2)
            );
        }


        //Fecha a conexão com o banco
        db.close();

        if (admExistente != null){
                return true;
        }

        return false;
        //Quando chamar, se o retorno for vazio, anuncia que deu errado a busca
        //Se não for, continua normal
    }

    //U de update
    public void atualizarAdmin(Admin adm){
        //Abre a conexão
        SQLiteDatabase db = this.getWritableDatabase();

        //Cria um lista valores a ser alterados
        ContentValues valores = new ContentValues();

        //Insere os valores
        valores.put(COLUNA_NOME, adm.getNome());
        valores.put(COLUNA_SENHA, adm.getSenha());

        //Parâmetro
        String parametro[] = {String.valueOf(adm.getId())};

        //Manda os valores atualizados
        db.update(
                //UPDATE (nome da tabela)
                NOME_TABELA,
                //SET valores a serem alterados
                valores,
                //WHERE (Condição)
                "adm_id = ?",
                //Parâmtro
                parametro);
        //Fecha a conexão com o banco
        db.close();
    }

    //D de delete
    public void excluirAdmin(int id){
        //Pega o contexto
        SQLiteDatabase db = this.getWritableDatabase();

        //Cria os parâmetros
        String parametro[] = {String.valueOf(id)};

        //Manda deletar o adm
        db.delete(
                //Nome da tabela
                NOME_TABELA,
                //Condição
                "id = ?",
                //Parâmetro
                parametro
        );

        //Fecha a conexão
        db.close();
    }
}
