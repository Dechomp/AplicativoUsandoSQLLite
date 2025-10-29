package com.example.cadastrocontatos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cadastrocontatos.classesDAO.AdminDAO;
import com.example.cadastrocontatos.classesDAO.ContatoDAO;
import com.example.cadastrocontatos.classesDTO.Contato;
import com.google.android.material.snackbar.Snackbar;

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

        //Quando clicar no botão de criar contato
        btAdicionarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chamo a função de navegação de tela
                try {
                    navegarTela(CadastrarContatoActivity.class);
                }
                catch (Exception ex){
                    Log.e("Erro: ", ex.getMessage());
                }

            }
        });

       //Quando clicar no botão de atualizar informações do Admin
        btAtualizarInformacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chamo a função de navegação de tela local (Já tenho a global, mas para não
                // disperdicar esta, continuo assim)
                navegarTela(EscolherAtualizacaoActivity.class);
            }
        });


        //Quando clicar no botão de deletar a conta
        btApagarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crio um alert Dialog
                AlertDialog alerta = new AlertDialog.Builder(v.getContext())
                        //Defino o titulo
                        .setTitle("Apagar Conta?")
                        //Defino a mensagem
                        .setMessage("Deseja realmente apagar sua conta?")
                        //Caso clique em sim
                        .setPositiveButton("Sim", (dialog, which) -> {
                            //Crio uma instancia da classe AdmDAO
                            AdminDAO admDAO = new AdminDAO(v.getContext());

                            //Chamo a função de apagar a conta
                            admDAO.excluirAdmin(Global.adm.getId());

                            //Pergunto se ele quer desfaze a ação
                            Snackbar.make(findViewById(R.id.main), "Conta apagada!", Snackbar.LENGTH_LONG)
                                    //Defino a ação caso ele queira desfazer
                                    .setAction("Desfazer", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //Se ele clicar que sim, refaço a conta no banco,
                                            // novamente
                                            admDAO.criarAdmin(Global.adm);

                                            //Por enquanto, ele vai perder todos os contato, depois
                                            //adiciono uma função para mudar o id de adm dos contatos
                                        }
                                    }
                            )
                            //Adiciono um roll back caso ele não aperte em desfazer
                                    .addCallback(new Snackbar.Callback() {
                                        @Override
                                        public void onDismissed(Snackbar transientBottomBar, int event) {
                                            // Só navega quando o Snackbar for fechado
                                            navegarTela(LoginActivity.class);
                                        }
                                    })
                            //Mostro o snack bar
                                .show();


                        })
                        //Caso clique que não
                        .setNegativeButton("Não", null)
                        //Mostro o Alert
                        .show();

            }
        });

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

        btAtualizarContato = bloco.findViewById(R.id.btTelaAtualizarContato);
        btDeletarContato = bloco.findViewById(R.id.btDeletarContato);

        View dvDivisor = bloco.findViewById(R.id.dvDivisor);


        //Preencho os campos com as informações do contato
        tvNomeCadastrado.setText(ctt.getNome());
        tvCelularCadastrado.setText(ctt.getCelular());
        tvEmailCadastrado.setText(ctt.getEmail());

        //Quando clicar no botão de atualizar contato
        btAtualizarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Configuro qual contato será atualizado
                Global.contato = ctt;

                //Chamo a função de navegação de tela
                navegarTela(AtualizarInformacoesContatoActivity.class);
            }
        });

        //Quando clicar no botão de remover contato
        btDeletarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crio um alert Dialog
                AlertDialog alerta = new AlertDialog.Builder(v.getContext())
                        //Defino o titulo
                        .setTitle("Apagar Contato?")
                        //Defino a mensagem
                        .setMessage("Deseja realmente apagar este contato?")
                        //Caso clique em sim
                        .setPositiveButton("Sim", (dialog, which) -> {
                            //Crio um objeto da classe ContatoDAO
                            ContatoDAO cttDAO = new ContatoDAO(v.getContext());

                            //Chamo a função de apagar o contato
                            cttDAO.excluirContato(ctt.getId());

                            //Removo o container
                            lnContainer.removeView(bloco);

                            //Mostro a mensagem, com um extra, um botão de desfazer a ação
                            Snackbar.make(lnContainer, "Contato apagado!", Snackbar.LENGTH_LONG)
                                // Crio uma ação caso ele queira desfazer o erro
                                    .setAction("Desfazer", v1 -> {
                                        //Recrio o contato no banco(No caso ele vai ter um novo id)
                                        cttDAO.criarContato(ctt);

                                        //Recrio o container(No caso, acredito eu que vai aparecer mais embaixo)
                                        lnContainer.addView(bloco);
                                    }
                            //Mostro o snack bar
                            ).show();
                        })
                        //Caso clique que não
                        .setNegativeButton("Não", null)
                        //Mostra o alerta
                        .show();
            }
        });






        //Adiciono o bloco ao container
        lnContainer.addView(bloco);
    }

    //Função para navegar entre tela
    private void navegarTela(Class telaEscolhida){
        //Crio um objeto da classe Intent
        Intent proximaTela;

        //Faço o caminho
        proximaTela = new Intent(MainActivity.this, telaEscolhida);

        //Chamo a função que manda para a próxima tela
        startActivity(proximaTela);

        //Fecho esta tela
        finish();
    }


}