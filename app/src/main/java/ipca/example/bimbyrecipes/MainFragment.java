package ipca.example.bimbyrecipes;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ipca.example.bimbyrecipes.models.Recipe;

/**
 * Created by lourencogomes on 13/12/17.
 */

public class MainFragment extends Fragment {

    List<Recipe> recipes = new ArrayList<>();

    ListView listView;
    RecipeAdapter recipeAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =inflater.inflate(R.layout.fragment_main,null);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RecipeAddActivity.class);
                startActivity(intent);
            }
        });

        listView=(ListView)view.findViewById(R.id.listView);
        recipeAdapter=new RecipeAdapter();
        listView.setAdapter(recipeAdapter);

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("recipies");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipes.clear();
                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    //Log.d("test", d.toString());
                    for (DataSnapshot d1: d.getChildren()) {
                        Recipe recipe = d1.getValue(Recipe.class);
                        recipes.add(recipe);
                    }
                }
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    class RecipeAdapter extends BaseAdapter {

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
            final ImageView imageView= (ImageView)view.findViewById(R.id.imageView);

            textView.setText(recipes.get(i).getTitle());
            String pathImage=recipes.get(i).getImageUri();


            new AsyncTask<String,Void,Bitmap>(){

                @Override
                protected Bitmap doInBackground(String... strings) {
                    Bitmap bm=Utils.getBitmapFromURL(strings[0]);
                    return bm;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    imageView.setImageBitmap(bitmap);

                }
            }.execute(pathImage,null,null);

            return view;
        }
    }

}
