package com.example.recycleview.activity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleview.R;
import com.example.recycleview.activity.ClickListener;
import com.example.recycleview.activity.adapter.Adapter;
import com.example.recycleview.activity.model.Filme;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Filme> listaFilmes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        // Listagem de filmes
        this.criarFilmes();

        //configurando o adapter
        Adapter adapter = new Adapter( listaFilmes );

        //configurar recycle view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL ));
        recyclerView.setAdapter(adapter);

        //evento de clique
        recyclerView.addOnItemTouchListener(
                new ClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new ClickListener.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }

                            @Override
                            public void onItemClick(View view, int position){

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }
                )
        );
    }

    public void criarFilmes(){
        Filme filme1 = new Filme("Inception", "Ficção Científica", "2010");
        Filme filme2 = new Filme("O Poderoso Chefão", "Crime", "1972");
        Filme filme3 = new Filme("Interestelar", "Ficção Científica", "2014");
        Filme filme4 = new Filme("Clube da Luta", "Drama", "1999");
        Filme filme5 = new Filme("Filme Jean", "Terror", "2004");

        this.listaFilmes.add(filme1);
        this.listaFilmes.add(filme2);
        this.listaFilmes.add(filme3);
        this.listaFilmes.add(filme4);
        this.listaFilmes.add(filme5);

    }
//
//    public void addNewMovie(View view){
//        Filme filmeNew = new Filme("New Movie" , "New", "777");
//        this.listaFilmes.add(filmeNew);
//
//    }
}