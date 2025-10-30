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

import com.example.cadastrocontatos.classesDAO.AdminDAO;
import com.example.cadastrocontatos.classesDTO.Admin;

public class LoginActivity extends AppCompatActivity {



    //Criando os componentes
    Button btTelaCadastrar, btEntrar, btFechar;

    EditText edUsuario, edSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        //Vincular os componentes
        btTelaCadastrar = findViewById(R.id.btTelaCadastrar);
        btEntrar = findViewById(R.id.btEntrar);
        btFechar = findViewById(R.id.btFechar);

        edUsuario = findViewById(R.id.edUsuarioEntrar);
        edSenha = findViewById(R.id.edSenhaEntrar);


        //Quando cliclar no botão de cadastrar
        btTelaCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telaCadastroAdmin;
                //Dois jeito de passar o caminho
                //Passando o nome da tela atual
                //telaCadastroAdmin = new Intent(LoginActivity.this, CadastroAdminActivity.class);

                //Ou mandabdi ele pegar o contexto
                telaCadastroAdmin = new Intent(v.getContext(), CadastroAdminActivity.class);

                //Chamo a função que manda para a próxima tela
                startActivity(telaCadastroAdmin);

                //Aqui em espcifico, eu não quero fechar a tela de login, por isto, não chamarei o finish()
            }
        });

        //Quando clicar no botão de entrar
        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Verifica se há campos vazios]
                //Variáveis
                String usuario, senha;

                //Receber o texto
                usuario = edUsuario.getText().toString();
                senha = edSenha.getText().toString();

                if (usuario.isEmpty() || senha.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Campos vazios, digite novamente", Toast.LENGTH_SHORT).show();
                }
                //Se não tiver, verifica se o usuário existe
                else{
                    //Cria a classe DAO
                    AdminDAO adminDAO = new AdminDAO(v.getContext());

                    //Verifica se o usuário existe
                    if(adminDAO.checarExistencia(usuario)){

                        //Se sim, verifica a senha

                        //Primeiro cria a classse Admin
                        Admin admin = new Admin(usuario, senha);


                        //Verifica se a senha está correta
                        if(adminDAO.checarUsuarioSenha(admin)){
                            //Se sim, mostra a mensagem

                            Toast.makeText(v.getContext(), "Login realizado com sucesso, bem-vindo " + admin.getNome() + "!" , Toast.LENGTH_SHORT).show();


                            //Manda para próxima tela
                            Intent telaPrincipal;
                            telaPrincipal = new Intent(v.getContext(), MainActivity.class);
                            startActivity(telaPrincipal);

                            //Salva o admin logado
                            Global.adm = adminDAO.buscarAdmin(usuario);

                            Log.e("ID do admin", "" + adminDAO.buscarAdmin(usuario).getId());

                            Log.e("admid logado: ", "" + Global.adm.getId());

                            //Fecha a tela de login
                            finish();
                        }
                        //Se não estiver
                        else{
                            //Mostra a mensagem
                            Toast.makeText(v.getContext(), "Senha incorreta", Toast.LENGTH_SHORT).show();
                        }

                    }
                    //Se não exixtir
                    else{
                        //Mostra aa mensagem
                        Toast.makeText(v.getContext(), "Usuário ou senha incorreto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAndRemoveTask();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}