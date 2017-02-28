package com.cursoandroid.whatsapp.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.whatsapp.R;
import com.cursoandroid.whatsapp.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

public class ValidadorActivity extends Activity {

    private EditText codigo;
    private Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        codigo=(EditText)findViewById(R.id.validador_codigo);
        botao=(Button) findViewById(R.id.validador_botao);

        SimpleMaskFormatter mascaraTelefone = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher insereMascaraTelefone = new MaskTextWatcher(codigo, mascaraTelefone);
        codigo.addTextChangedListener(insereMascaraTelefone);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recuperar dados das preferencias do usuario
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String, String> dadosUsuario = preferencias.getDadosUsuario();
                String tokenGerado = dadosUsuario.get("token");
                String tokenDigitado=codigo.getText().toString();
                if(tokenDigitado.equals(tokenGerado)){
                    Toast.makeText(ValidadorActivity.this,"Token validado",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ValidadorActivity.this,"Token inválido",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
