package com.uniftec.apppessoa;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import com.uniftec.apppessoa.Uteis.LinhaConsultarAdapter;
import com.uniftec.apppessoa.model.ProdutoModel;
import com.uniftec.apppessoa.repository.ProdutoRepository;

public class

ConsultarActivity extends AppCompatActivity {

    //CRIANDO UM OBJETO DO TIPO ListView PARA RECEBER OS REGISTROS DE UM ADAPTER
    ListView listViewProdutos;

    //CRIANDO O BOTÃO VOLTAR PARA RETORNAR PARA A TELA COM AS OPÇÕES
    Button buttonVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        //VINCULANDO O LISTVIEW DA TELA AO OBJETO CRIADO
        listViewProdutos = (ListView)this.findViewById(R.id.listViewProdutos);

        //VINCULANDO O BOTÃO VOLTAR DA TELA AO OBJETO CRIADO
        buttonVoltar    = (Button)this.findViewById(R.id.buttonVoltar);


        //CHAMA O MÉTODO QUE CARREGA AS PESSOAS CADASTRADAS NA BASE DE DADOS
        this.CarregarPessoasCadastradas();

        //CHAMA O MÉTODO QUE CRIA O EVENTO PARA O BOTÃO VOLTAR
        this.CriarEvento();
    }

    //MÉTODO QUE CRIA EVENTO PARA O BOTÃO VOLTAR
    protected  void CriarEvento(){

        buttonVoltar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //REDIRECIONA PARA A TELA PRINCIPAL
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);

                //FINALIZA A ATIVIDADE ATUAL
                finish();
            }
        });
    }

    //MÉTODO QUE CONSULTA AS PESSOAS CADASTRADAS
    protected  void CarregarPessoasCadastradas(){

        ProdutoRepository produtoRepository =  new ProdutoRepository(this);

        //BUSCA AS PESSOAS CADASTRADAS
        List<ProdutoModel> produtos = produtoRepository.SelecionarTodos();

        //SETA O ADAPTER DA LISTA COM OS REGISTROS RETORNADOS DA BASE
        listViewProdutos.setAdapter(new LinhaConsultarAdapter(this, produtos));
    }

}
