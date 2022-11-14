package com.uniftec.apppessoa;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Locale;

import com.uniftec.apppessoa.Uteis.Uteis;
import com.uniftec.apppessoa.model.ProdutoModel;
import com.uniftec.apppessoa.repository.ProdutoRepository;

public class CadastrarActivity extends AppCompatActivity {


    /*COMPONENTES DA TELA*/
    EditText editTextNomeProduto;
    EditText editTextPreco;
    CheckBox checkBoxRegistroAtivo;
    Button buttonSalvar;
    Button buttonVoltar;

    //CRIA POPUP COM O CALENDÁRIO
    DatePickerDialog datePickerDialogDataNascimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        // CHAMA O METODO PARA DIZER QUAL A LOCALIZAÇÃO,
        // USADO PARA TRADUZIR OS TEXTOS DO CALENDÁRIO.
        this.Localizacao();

        //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
        this.CriarComponentes();

        //CRIA OS EVENTOS DOS COMPONENTES
        this.CriarEventos();
    }

    //VINCULA OS COMPONENTES DA TELA COM OS DA ATIVIDADE
    protected  void CriarComponentes(){

        editTextNomeProduto           = (EditText) this.findViewById(R.id.editTextNomeProduto);

        editTextPreco                 = (EditText) this.findViewById(R.id.editTextPreco);

        checkBoxRegistroAtivo         = (CheckBox)this.findViewById(R.id.checkBoxRegistroAtivo);

        buttonSalvar                  = (Button) this.findViewById(R.id.buttonSalvar);

        buttonVoltar                  = (Button) this.findViewById(R.id.buttonVoltar);

    }
    //CRIA OS EVENTOS DOS COMPONENTES
    protected  void CriarEventos(){

        //CRIANDO EVENTO NO BOTÃO SALVAR
        buttonSalvar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Salvar_onClick();
            }
        });

        //CRIANDO EVENTO NO BOTÃO VOLTAR
        buttonVoltar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                finish();
            }
        });
    }

    //VALIDA OS CAMPOS E SALVA AS INFORMAÇÕES NO BANCO DE DADOS
    protected  void Salvar_onClick(){

        if(editTextNomeProduto.getText().toString().trim().equals("")){
            Uteis.Alert(this, "Nome do produto obrigatório");
            editTextNomeProduto.requestFocus();
        }
        else if(editTextPreco.getText().toString().trim().equals("")){
            Uteis.Alert(this, "Endereço Obrigatório");
            editTextPreco.requestFocus();
        }
        else{
            /*CRIANDO UM OBJETO PRODUTO*/
            ProdutoModel produtoModel = new ProdutoModel();
            /*SETANDO O VALOR DO CAMPO NOME DO PRODUTO*/
            produtoModel.setNomeProduto(editTextNomeProduto.getText().toString().trim());
            /*SETANDO O PREÇO*/
            produtoModel.setPreco(editTextPreco.getText().toString().trim());

            produtoModel.setRegistroAtivo((byte)0);
            /*SE TIVER SELECIONADO SETA COMO ATIVO*/
            if(checkBoxRegistroAtivo.isChecked())
                produtoModel.setRegistroAtivo((byte)1);
            /*SALVANDO UM NOVO REGISTRO*/
            new ProdutoRepository(this).Salvar(produtoModel);
            /*MENSAGEM DE SUCESSO!*/
            Uteis.Alert(this,"Registro Salvo");
            LimparCampos();
        }
    }

    //LIMPA OS CAMPOS APÓS SALVAR AS INFORMAÇÕES
    protected void LimparCampos(){
        editTextNomeProduto.setText(null);
        editTextPreco.setText(null);
        checkBoxRegistroAtivo.setChecked(false);
    }
    //DIZ QUAL A LOCALIZAÇÃO PARA TRADUZIR OS TEXTOS DO CALENDÁRIO.
    protected  void Localizacao(){
        Locale locale = new Locale("pt", "BR");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);
    }

}