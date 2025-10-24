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

public class CadastroAdminActivity extends AppCompatActivity {

    //Criar os componentes
    EditText edNome, edSenha, edConfirmarSenha;

    Button btCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_admin);

        //Vincular os componentes
        edNome = findViewById(R.id.edNomeAdmin);
        edSenha = findViewById(R.id.edSenhaAdmin);
        edConfirmarSenha = findViewById(R.id.edConfirmarSenhaAdmin);

        btCadastrar = findViewById(R.id.btCadastrarAdmin);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Variáveis
                String nome, senha, confimarSenha;

                //Recebendo os textos
                nome = edNome.getText().toString();
                senha = edSenha.getText().toString();
                confimarSenha = edConfirmarSenha.toString();

                //Verifico se os campos estão vazios
                if (nome.isEmpty() || senha.isEmpty() || confimarSenha.isEmpty()){
                    //Mostra a mensagem
                    Toast.makeText(v.getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
                //Se as senhas estão batendo
                //Assim eu checo a igualdade: senha == confimarSenha
                //Assim verificar se a senha contem a senha, senha.contains(confimarSenha) (jeito errado pois pode ter mais que isto)
                //OU senha.equals(confirmarSenha) que checa se o que está em senha é equivalente à o que há em confimar senha
                else if (senha.equals(confimarSenha)){
                    //Mostra a mensagem
                    Toast.makeText(v.getContext(), "As senhas não batem!", Toast.LENGTH_SHORT).show();
                }
                else {

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