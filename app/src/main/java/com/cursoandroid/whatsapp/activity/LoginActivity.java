package com.cursoandroid.whatsapp.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cursoandroid.whatsapp.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.Random;

public class LoginActivity extends Activity {

    private EditText telefone;
    private EditText ddd;
    private EditText ddi;
    private Button botao;
    private EditText nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        telefone=(EditText) findViewById(R.id.login_telefone);
        ddd= (EditText) findViewById(R.id.login_ddd);
        ddi= (EditText) findViewById(R.id.login_ddi);
        botao=(Button) findViewById(R.id.login_botao);
        nome=(EditText) findViewById(R.id.login_nome);

        inserindoMascaraNosEditText();

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUsuario=nome.getText().toString();
                String telefoneCompleto=ddi.getText().toString()+ddd.getText().toString()+telefone.getText().toString();
                String telefoneSemMais=telefoneCompleto.replace("+","");
                String telefoneSemFormatcao = telefoneSemMais.replace("-", "");
                
                
                //Gerando token
                Random random = new Random();
                int numeroRandomico = random.nextInt(9999-1000)+1000;

                String token=String.valueOf(numeroRandomico);
            }
        });
    }

    private void inserindoMascaraNosEditText() {
        SimpleMaskFormatter mascaraTelefone = new SimpleMaskFormatter("NNNNN-NNNN");
        MaskTextWatcher insereMascaraTelefone = new MaskTextWatcher(telefone, mascaraTelefone);
        telefone.addTextChangedListener(insereMascaraTelefone);

        SimpleMaskFormatter mascaraDdd = new SimpleMaskFormatter("NN");
        MaskTextWatcher insereMascaraDdd = new MaskTextWatcher(ddd, mascaraDdd);
        ddd.addTextChangedListener(insereMascaraDdd);

        SimpleMaskFormatter mascaraDdi = new SimpleMaskFormatter("+NN");
        MaskTextWatcher insereMascaraDdi = new MaskTextWatcher(ddi, mascaraDdi);
        ddi.addTextChangedListener(insereMascaraDdi);
    }
}
