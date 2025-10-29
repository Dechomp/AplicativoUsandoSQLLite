package com.example.cadastrocontatos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cadastrocontatos.classesDAO.ContatoDAO;

public class AtualizarInformacoesContatoActivity extends AppCompatActivity {

    //Crio os elementos
    EditText edAtualizarNomeContato, edAtualizarEmailContato, edAtualizarTelefoneContato;

    Button btAtualizarContato, btCancelarAtualizacaoContato;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_atualizar_informacoes_contato);

        //Vinculo os componentes
        edAtualizarNomeContato = findViewById(R.id.edAtualizarNomeContato);
        edAtualizarEmailContato = findViewById(R.id.edAtualizarEmailContato);
        edAtualizarTelefoneContato = findViewById(R.id.edAtualizarTelefoneContato);

        btAtualizarContato = findViewById(R.id.btAtualizarContato);
        btCancelarAtualizacaoContato = findViewById(R.id.btCancelarAtualizacaoContato);

        //Defino os textos de cada edit text a partir do contato que está sendo atualizado
        edAtualizarNomeContato.setText(Global.contato.getNome());
        edAtualizarEmailContato.setText(Global.contato.getEmail());
        edAtualizarTelefoneContato.setText(Global.contato.getCelular());

        //Quando clicar para cancelar
        btCancelarAtualizacaoContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chamo a função global para nevegar entre as telas
                Global.navegarTela(v, MainActivity.class);
                finish();
            }
        });

        //Quando clicar para atualizar o contato
        btAtualizarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crio as variáveis para receber o texto
                String nome, email, telefone;

                //Rcebo os textos de cada edit text
                nome = edAtualizarNomeContato.getText().toString();
                email = edAtualizarEmailContato.getText().toString();
                telefone = edAtualizarTelefoneContato.getText().toString();

                //Checo se há alguma vazia
                if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()){
                    //Se sim, mostro a mensagem
                    Toast.makeText(v.getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Se não, atualizo localmente os dados do contato atual
                    Global.contato.setNome(nome);
                    Global.contato.setEmail(email);
                    Global.contato.setCelular(telefone);

                    //Crio um objeto da classe ContatoDAO
                    ContatoDAO cttDAO = new ContatoDAO(v.getContext());

                    //Chamo a função
                    cttDAO.criarContato(Global.contato);

                    //Chamo a função global para voltar para a tela principal
                    Global.navegarTela(v, MainActivity.class);

                    //Fecho esta tela
                    finish();
                }

            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}