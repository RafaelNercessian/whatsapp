package com.cursoandroid.whatsapp.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.cursoandroid.whatsapp.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class LoginActivity extends Activity {

    private EditText telefone;
    private EditText ddd;
    private EditText ddi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        telefone=(EditText) findViewById(R.id.login_telefone);
        ddd= (EditText) findViewById(R.id.login_ddd);
        ddi= (EditText) findViewById(R.id.login_ddi);

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
