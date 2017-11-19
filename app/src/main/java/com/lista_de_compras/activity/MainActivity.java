package com.lista_de_compras.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lista_de_compras.R;
import com.lista_de_compras.adapter.ListaAdapter;
import com.lista_de_compras.dao.ListaDAO;
import com.lista_de_compras.model.Lista;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String TAG = getClass().getSimpleName();
    private ListView listViewListas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carregarViewComponents();
        configurarSupportActionBar();
        configurarListView();
        configurarDrawerLayout();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListaCadastroActivity.class);
                startActivity(intent);
            }
        });

    }

    private void configurarSupportActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void configurarDrawerLayout() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, (Toolbar) findViewById(R.id.toolbar), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu_produtos) {
            Intent intent = new Intent(this, ProdutoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_menu_categoria_produtos) {
            Intent intent = new Intent(this, CategoriaDeProdutoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_menu_categoria_listas) {
            Intent intent = new Intent(this, CategoriaDeListaActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarListasNoListView();
    }

    //Obrigatório para onCreateContextMenu
    private void configurarListView() {
        registerForContextMenu(listViewListas);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        //Pega lista selecionado
        final Lista lista = (Lista) listViewListas.getItemAtPosition(info.position);

        MenuItem menuEditar = menu.add(R.string.editar);
        menuEditar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, ListaCadastroActivity.class);
                intent.putExtra("lista", lista);
                startActivity(intent);
                return true;
            }
        });

        MenuItem menuExcluir = menu.add(R.string.excluir);
        menuExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.confirmar_exclusao)
                        .setMessage(R.string.confirmar_exclusao_lista_mensagem)
                        .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new ListaDAO(MainActivity.this).excluir(lista);
                                carregarListasNoListView();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                return true;
            }
        });

    }

    private void carregarListasNoListView() {
        ListaDAO listaDAO = new ListaDAO(this);
        List<Lista> todasListas = listaDAO.todos();

        ListaAdapter listaAdapter = new ListaAdapter(this, todasListas);

        listViewListas.setAdapter(listaAdapter);
    }

    private void carregarViewComponents() {
        listViewListas = (ListView) findViewById(R.id.listView_listas);

        listViewListas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lista lista = (Lista) parent.getAdapter().getItem(position);
                Intent intent = new Intent(MainActivity.this, ListaDetalhesActivity.class);
                intent.putExtra("lista", lista);
                startActivity(intent);
            }
        });
    }
}
