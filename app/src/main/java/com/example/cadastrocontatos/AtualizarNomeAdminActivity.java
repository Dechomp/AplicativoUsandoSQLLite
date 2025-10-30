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

import com.example.cadastrocontatos.classesDAO.AdminDAO;

public class AtualizarNomeAdminActivity extends AppCompatActivity {

    //Crio os elementos
    EditText edNome, edSenha;

    Button btAtualizar, btCancelar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_atualizar_nome_admin);

        //Vinculo os componentes
        edNome = findViewById(R.id.edAtualizarNomeAdmin);
        edSenha = findViewById(R.id.edConferirSenhaAdmin);

        btAtualizar = findViewById(R.id.btAtualizarNomeAdmin);
        btCancelar = findViewById(R.id.btCancelarAtualizacaoNomeAdmin);

        //Atualizo o texto atual do nome
        edNome.setText(Global.adm.getNome());

        //Caso clique em cancelar
        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mando para a tela principal através da função global
                Global.navegarTela(v, MainActivity.class);

                //Fecho esta tela
                finish();
            }
        });

        //Caso clique em atualizar
        btAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crio as variáveis
                String nome, senha;

                //Recebo os textos
                nome = edNome.getText().toString();
                senha = edSenha.getText().toString();
                //Crio um objeto da classe DAO
                AdminDAO admDAO = new AdminDAO(v.getContext());

                //Confiro se há alguma vazia
                if (nome.isEmpty() || senha.isEmpty()){
                    //Se sim, mostro a mensagem
                    Toast.makeText(v.getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
                //Confiro se a senha está batendo
                else if (!Global.adm.getSenha().equals(senha)){
                    //Se não, mostro a mensagem
                    Toast.makeText(v.getContext(), "A senha está incorreta!", Toast.LENGTH_SHORT).show();
                }
                //Confiro se já existe um admin com o mesmo nome
                else if (admDAO.checarExistencia(nome)){
                    //Se sim, mostro a mensagem
                    Toast.makeText(v.getContext(), "Já existe um admin com este nome! Escolha outro", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Se tudo estiver certo, atualizo o nome

                    //Altero o nome localmente
                    Global.adm.setNome(nome);

                    //Atualizo no banco



                    //Chamo a função que atualiza o Admin
                    admDAO.atualizarAdmin(Global.adm);


                    //Chamo a função de ir para próxima tela
                    Global.navegarTela(v, MainActivity.class);

                    //Mostro a mensagem de sucesso
                    Toast.makeText(v.getContext(), "Nome atualizado com sucesso!", Toast.LENGTH_SHORT).show();

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