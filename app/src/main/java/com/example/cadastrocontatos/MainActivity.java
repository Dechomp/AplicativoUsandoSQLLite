package com.example.cadastrocontatos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cadastrocontatos.classesDAO.ContatoDAO;
import com.example.cadastrocontatos.classesDTO.Contato;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    //Crio os componentes
    LinearLayout lnContainer;

    Button btAdicionarContato, btAtualizarInformacoes, btApagarConta;

    TextView tvUsuarioAtual;

    //Crio um inflater para poder duplicar componetes e as suas configurações
    LayoutInflater inflater = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Vinculando componentes
        lnContainer = findViewById(R.id.lnContainer);

        btAdicionarContato = findViewById(R.id.btAdicionarContato);
        btAtualizarInformacoes = findViewById(R.id.btAtualizarInformacoes);
        btApagarConta = findViewById(R.id.btApagarConta);

        tvUsuarioAtual = findViewById(R.id.tvUsuarioAtual);

        tvUsuarioAtual.setText(Global.adm.getNome());

        //Crio um objeto da classe ContatoDAO para conectar ao banco de dados
        ContatoDAO cttDAO = new ContatoDAO(this);

        //Crio uma lista e recebo as informações dos contatos criados no Banco de dados passando o
        // id do usuário

        ArrayList<Contato> contatos = null;
        try {
            contatos = cttDAO.listarContatos(Global.adm.getId());
        }
        catch (Exception ex){
            Toast.makeText(this, "Receber os contatos" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Erro: ", ex.getMessage());
        }


        //Associo o inflater a Activity
        inflater = LayoutInflater.from(this);


        //Enquanto tiver componentes na lista, duplico os componentes com uma função
       if (contatos != null){
           try {
               for (int i = 0; i < contatos.size(); i++){
                   duplicarComponente(contatos.get(i));
               }
           }
           catch (Exception ex){
               Toast.makeText(this, "Erro ao listar os contatos" + ex.getMessage(), Toast.LENGTH_SHORT).show();
               Log.e("Erro: ", ex.getMessage());
           }

       }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    private void duplicarComponente(Contato ctt){
        TextView tvNome, tvEmail, tvCelular, tvNomeCadastrado, tvEmailCadastrado, tvCelularCadastrado;
        Button btAtualizarContato, btDeletarContato;

        //Crio um Contrain Layout para associar os componentes
        //Ordem (aonde eu criei o layout, qual componente eu vou adicionar o container, como eu vou adicionar o container não precisa colocar nada)
        ConstraintLayout bloco = (ConstraintLayout) inflater.inflate(R.layout.layout_container_contato, lnContainer, false);

        //Vinculo os componentes
        tvNome = bloco.findViewById(R.id.tvNome);
        tvEmail = bloco.findViewById(R.id.tvEmail);
        tvCelular = bloco.findViewById(R.id.tvCelular);
        tvNomeCadastrado = bloco.findViewById(R.id.tvNomeCadastrado);
        tvEmailCadastrado = bloco.findViewById(R.id.tvEmailCadastrado);
        tvCelularCadastrado = bloco.findViewById(R.id.tvCelularCadastrado);

        btAtualizarContato = bloco.findViewById(R.id.btAtualizarContato);
        btDeletarContato = bloco.findViewById(R.id.btDeletarContato);


        //Preencho os campos com as informações do contato
        tvNomeCadastrado.setText(ctt.getNome());
        tvCelularCadastrado.setText(ctt.getCelular());
        tvEmailCadastrado.setText(ctt.getEmail());






        //Adiciono o bloco ao container
        lnContainer.addView(bloco);




    }
}