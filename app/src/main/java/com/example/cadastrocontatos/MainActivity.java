package com.example.cadastrocontatos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cadastrocontatos.classesDAO.ContatoDAO;
import com.example.cadastrocontatos.classesDTO.Contato;

import java.util.ArrayList;

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

        ArrayList<Contato> contatos = cttDAO.listarContatos(Global.adm.getId());

        //Associo o inflater a Activity
        inflater = LayoutInflater.from(this);


        //Enquanto tiver componentes na lista, duplico os componentes com uma função

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    private void duplicarComponente(){
        TextView tvNome, tvEmail, tvCelular, tvNomeCadastrado, tvEmailCadastrado, tvCelularCadastrado;
        Button btAtualizarContato, btDeletarContato;

        //Crio um Contraint Layout para associar os componentes

    }
}