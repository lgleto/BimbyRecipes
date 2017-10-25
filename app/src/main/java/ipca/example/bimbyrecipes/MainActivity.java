package ipca.example.bimbyrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import ipca.example.bimbyrecipes.models.Recipe;

public class MainActivity extends AppCompatActivity {

    public static final String TAG="bimbyrecipes";

    RealmResults<Recipe> recipes;

    ListView listView;
    RecipeAdapter recipeAdapter;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Realm
        Realm.init(this);
        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();



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


        recipes=realm.where(Recipe.class).findAll();
        listView=(ListView)findViewById(R.id.listView);
        recipeAdapter=new RecipeAdapter();
        listView.setAdapter(recipeAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        recipes=realm.where(Recipe.class).findAll();
        recipeAdapter.notifyDataSetChanged();
    }

    class RecipeAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return recipes.size();
        }

        @Override
        public Object getItem(int i) {
            return recipes.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view==null){
                view=getLayoutInflater().inflate(R.layout.recipe_row,null);
            }

            TextView textView = (TextView)view.findViewById(R.id.textViewTitle);
            ImageView imageView= (ImageView)view.findViewById(R.id.imageView);

            textView.setText(recipes.get(i).getTitle());
            if (recipes.get(i).getImageUri()!=null){
                imageView.setImageBitmap(Utils.loadBitmap(recipes.get(i).getImageUri()));
            }

            return view;
        }
    }


}
