package com.sabrinahanich.n2_lista_tel;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //List<String> lista = new ArrayList<String>();
    private ListView agenda;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        agenda = (ListView) findViewById(R.id.lista);
        getData();
        Button botao = (Button)findViewById(R.id.button);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText nome = (EditText)findViewById(R.id.nome);
                EditText telefone = (EditText)findViewById((R.id.telefone));

                String nomeString = nome.getText().toString();
                String telefoneString = telefone.getText().toString();

                BancoController crud = new BancoController(getBaseContext());
                String resultado = crud.insereDado(nomeString,telefoneString);

                getData();

                Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getData() {
        BancoController crud = new BancoController(getBaseContext());
        Cursor c = crud.carregaDados();
        List<String> lista = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        agenda.setAdapter(adapter);
        while(c.moveToNext()) {

            int index;

            index = c.getColumnIndexOrThrow("nome");
            String nome = c.getString(index);

            index = c.getColumnIndexOrThrow("telefone");
            String telefone = c.getString(index);

            lista.add(nome + " - " + telefone);


        }
        adapter.notifyDataSetChanged();
    }
}