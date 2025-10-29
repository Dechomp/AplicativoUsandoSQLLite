package com.example.cadastrocontatos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EscolherAtualizacaoActivity extends AppCompatActivity {

    //Crio os elementos
    Button btVoltarMain, btAtualizarSenha, btAtualizarNome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_escolher_atualizacao);

        //Vinculo os componentes
        btVoltarMain = findViewById(R.id.btVoltarMain);
        btAtualizarNome = findViewById(R.id.btAtualizarNome);
        btAtualizarSenha = findViewById(R.id.btAtualizarSenha);

        //Quando clicar para voltar
        btVoltarMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Função global de navegação de telas
                Global.navegarTela(v, MainActivity.class);

                //Fecho esta tela
                finish();
            }
        });

        //Quando clicar para atualizar o nome
        btAtualizarNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Função global de navegação de telas
                Global.navegarTela(v, AtualizarNomeAdminActivity.class);

                //Fecho esta tela
                finish();

            }
        });

        //QUnado clicar para atualizar a senha
        btAtualizarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Função global de navegação de tela
                Global.navegarTela(v, AtualizarSenhaAdminActivity.class);

                //Fecho esta tela
                finish();
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}