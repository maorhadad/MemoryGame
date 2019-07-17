package com.hadadas.memorygame;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hadadas.memorygame.bord.CardsAdapter;


public class MainActivity extends AppCompatActivity implements GameCallBacks, CardsAdapter.ItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Game game;
    private CardsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        game = new Game(this);
        game.getCharactersCount();

    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        int numberOfColumns = (int)Math.sqrt(game.getUniqueIds().size());
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new CardsAdapter( this, game.getUniqueIds());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    @Override
    public void onGetCountSuccess(int count) {
        game.generateUniqueIds(count);
    }

    @Override
    public void onGetCountFail() {
        Toast.makeText(this, "Error on get counts", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinishUniqueIds() {
        Log.d(TAG, "onFinishUniqueIds");
        initRecyclerView();
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position).isFront() + ", which is at cell position " + position);
        adapter.getItem(position).toggle();
        adapter.notifyItemChanged(position);

    }
}
