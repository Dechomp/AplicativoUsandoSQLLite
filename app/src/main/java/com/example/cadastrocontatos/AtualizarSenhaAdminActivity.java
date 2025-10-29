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

public class AtualizarSenhaAdminActivity extends AppCompatActivity {
    //Crio os elementos
    EditText edNomeAdminAtual, edSenhaAtualAdmin, edAtualizarSenhaAdmin, edConfirmarNovaSenhaAdmin;

    Button btAtualizarAdmin, btCancelarAtualizacaoAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_atualizar_senha_admin);

        //Vinculo os componentes
        edNomeAdminAtual = findViewById(R.id.edNomeAdminAtual);
        edSenhaAtualAdmin = findViewById(R.id.edSenhaAtualAdmin);
        edAtualizarSenhaAdmin = findViewById(R.id.edAtualizarSenhaAdmin);
        edConfirmarNovaSenhaAdmin = findViewById(R.id.edConfirmarNovaSenhaAdmin);

        btAtualizarAdmin = findViewById(R.id.btAtualizarAdmin);
        btCancelarAtualizacaoAdmin = findViewById(R.id.btCancelarAtualizacaoAdmin);

        //Atualizo o nome do Editext do nome Atual
        edNomeAdminAtual.setText(Global.adm.getNome());

        //A senha deixo para o usuário acertar

        //Quando clicar no botão de cancelar
        btCancelarAtualizacaoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chamo a função global para voltar para a tela principal
                Global.navegarTela(v, MainActivity.class);
                finish();
            }
        });

        //Quando clicar no botão de atualizar
        btAtualizarAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Primeiro crios as variáveis
                String senha, novaSenha, confirmarSenha;

                //Recebo o texto de cada uma
                senha = edSenhaAtualAdmin.getText().toString();
                novaSenha = edAtualizarSenhaAdmin.getText().toString();
                confirmarSenha = edConfirmarNovaSenhaAdmin.getText().toString();

                //Checo se há algum campo vazio
                if (senha.isEmpty() || novaSenha.isEmpty() || confirmarSenha.isEmpty()){
                    //Se sim, mostro a mensagem
                    Toast.makeText(v.getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
                //Verifico se a senha é a mesma de agora
                else if (!Global.adm.getSenha().equals(senha)){
                    //Se não, mostro a mensagem
                    Toast.makeText(v.getContext(), "A senha atual está incorreta!", Toast.LENGTH_SHORT).show();
                }
                //Se as duas senhas não batem
                else if (!novaSenha.equals(confirmarSenha)){
                    //Mostro a mensagem
                    Toast.makeText(v.getContext(), "As senhas não batem!", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Se tudo estiver certo, atualizo a senha

                    //Altero a senha atual localmente
                    Global.adm.setSenha(novaSenha);

                    //Altero no banco

                    //Primeiro cria a classe DAO
                    AdminDAO admDAO = new AdminDAO(v.getContext());

                    //Chamo a função que atualiza o Admin
                    admDAO.atualizarAdmin(Global.adm);

                    //Chamo a função de ir para próxima tela
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