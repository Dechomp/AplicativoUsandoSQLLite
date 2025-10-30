package com.example.cadastrocontatos;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.View;

import com.example.cadastrocontatos.classesDTO.Admin;
import com.example.cadastrocontatos.classesDTO.Contato;

public class Global {
    //Admin logado
    public static Admin adm = null;

    //Versão do banco de dados
    public static int versao = 5;

    //Contato a ser atualizado/deletado
    public static Contato contato = null;

    //Teste para deixar uma função global
    public static void navegarTela(View telaAtual, Class telaEscolhida) {
        //Crio um objeto da classe Intent
        Intent proximaTela;

        //Faço o caminho
        proximaTela = new Intent(telaAtual.getContext(), telaEscolhida);

        //Chamo a função que manda para a próxima tela
        startActivity(telaAtual.getContext(), proximaTela, null);
    }
}
