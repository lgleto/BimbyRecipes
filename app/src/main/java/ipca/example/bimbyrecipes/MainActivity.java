package ipca.example.bimbyrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG="bimbyrecipes";

    List<Recipe> recipes=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecipeAddActivity.class);
                startActivity(intent);
            }
        });

        recipes.add(new Recipe("Bacalhau Brás","sdfwesfwes sdgkjwsdgjsndv ","",null));
        recipes.add(new Recipe("Papas de Sarrabulho","sdfwesfwes sdgkjwsdgjsndv ","",null));
        recipes.add(new Recipe("Vitela Assada","sdfwesfwes sdgkjwsdgjsndv ","",null));
        recipes.add(new Recipe("Croquetes","sdfwesfwes sdgkjwsdgjsndv ","",null));

    }


}
