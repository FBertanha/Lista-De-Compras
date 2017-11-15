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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lista_de_compras.R;
import com.lista_de_compras.adapter.ListaAdapter;
import com.lista_de_compras.dao.ListaDAO;
import com.lista_de_compras.dao.ProdutoDAO;
import com.lista_de_compras.model.Lista;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String TAG = getClass().getSimpleName();
    private ListView listViewLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        carregarViewComponents();
        configurarListView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListaCadastroActivity.class);
                //intent.putExtra("lista", lista);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu_listas) {

        } else if (id == R.id.nav_menu_produtos) {
            Intent intent = new Intent(this, ProdutoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_menu_categoria_produtos) {
            Intent intent = new Intent(this, CategoriaDeProdutoActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_menu_categoria_listas) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarProdutosNoListView();
    }

    //Obrigatório para onCreateContextMenu
    private void configurarListView() {
        registerForContextMenu(listViewLista);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        //Pega lista selecionado
        final Lista lista = (Lista) listViewLista.getItemAtPosition(info.position);

        MenuItem menuEditar = menu.add(R.string.menu_activity_produto_editar);
        menuEditar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, ListaDetalhesActivity.class);
                intent.putExtra("lista", lista);
                startActivity(intent);
                return true;
            }
        });

        //Monta menu de contexto
        MenuItem menuExcluir = menu.add(R.string.menu_activity_produto_excluir);
        menuExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //Monta uma dialogo de confirmação, sim/não

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.menu_activity_produto_excluir)
                        .setMessage(R.string.menu_activity_produto_excluir_mensagem)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new ProdutoDAO(MainActivity.this).excluir(lista.getCodigo());
                                carregarProdutosNoListView();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                return true;
            }
        });

    }

    private void carregarProdutosNoListView() {
        ListaDAO listaDAO = new ListaDAO(this);
        List<Lista> todasListas = listaDAO.todos();

        ListaAdapter listaAdapter = new ListaAdapter(this, todasListas);
        //ArrayAdapter<Produto> listaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_2, todasListas);


        listViewLista.setAdapter(listaAdapter);
    }

    private void carregarViewComponents() {
        listViewLista = (ListView) findViewById(R.id.listView_listas);

        listViewLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
