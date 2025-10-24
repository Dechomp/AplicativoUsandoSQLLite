package com.example.cadastrocontatos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {



    //Criando os componentes
    Button btTelaCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        //Vincular os componentes
        btTelaCadastrar = findViewById(R.id.btTelaCadastrar);

        //Quando cliclar no botão
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}