package com.example.cadastrocontatos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.cadastrocontatos.classesDTO.Contato;

public class CadastrarContatoActivity extends AppCompatActivity {


    //Criando os elementos
    Button btCadastrarContato, btCancelarCadastro;
    EditText edCadastrarNomeContato, edCadastrarEmailContato, edCadastrarTelefoneContato;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastrar_contato);

        //Vinculando os elementos
        btCadastrarContato = findViewById(R.id.btCadastrarContato);
        btCancelarCadastro = findViewById(R.id.btCancelarCadastro);

        edCadastrarNomeContato = findViewById(R.id.edCadastrarNomeContato);
        edCadastrarEmailContato = findViewById(R.id.edCadastrarEmailContato);
        edCadastrarTelefoneContato = findViewById(R.id.edCadastrarTelefoneContato);

        //Caso clique em cancelar o cadastro
        btCancelarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarTelaPrincipal();
            }
        });

        //Caso clique em cadastrar
        btCadastrarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crio as variáveis de cada campo
                String nome, email, telefone;


                //Crio um objeto da classe ContatoDAO
                ContatoDAO cttDAO = new ContatoDAO(v.getContext());

                //Recebo os textos de cada uma
                nome = edCadastrarNomeContato.getText().toString();
                email = edCadastrarEmailContato.getText().toString();
                telefone = edCadastrarTelefoneContato.getText().toString();

                //Verifico se há algum campo vazio
                if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()){
                    //Se sim, mostra a mensagem
                    Toast.makeText(v.getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Se não, cadastro o contato
                    try {
                        //Crio um objeto da classe Contato e passo os dados
                        Contato ctt = new Contato(nome, telefone, email, Global.adm.getId());

                        //Chamo a função para cadastrar o contato
                        cttDAO.criarContato(ctt);

                        //Mostro a mensagem
                        Toast.makeText(v.getContext(), "Contato cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                        //Chamo a função para voltar para a tela principal
                        voltarTelaPrincipal();
                    }
                    catch (Exception ex){
                        //Se der erro, mostra a mensagem
                        Toast.makeText(v.getContext(), "Erro ao cadastrar Contato, Erro: " +
                                ex.getMessage(), Toast.LENGTH_SHORT).show();
                        //Mando no log
                        Log.e("Erro: ", ex.getMessage());
                    }
                }

            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Função para voltar para a tela prinicipal, pois para atualizaar a lista,
    // irei fechar a tela principal, não é recomendável fazer isto, mas é o jeito mais rapido
    private void voltarTelaPrincipal(){
        //Crio um objeto do tipo intent
        Intent telaPrincipal;

        //Crio o caminho
        telaPrincipal = new Intent(this, MainActivity.class);

        //Chamo a função que manda para a próxima tela
        startActivity(telaPrincipal);

        //Fecho esta tela
        finish();
    }

}