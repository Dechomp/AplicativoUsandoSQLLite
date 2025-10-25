package com.example.cadastrocontatos;

import android.content.Intent;
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
                
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}