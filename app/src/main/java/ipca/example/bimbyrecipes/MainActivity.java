package ipca.example.bimbyrecipes;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;
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
        realm = getRealmIntance();


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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            } else {
                Log.i(TAG,"BLE permission has already been granted.");
            }
        }
    }

    Realm getRealmIntance(){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        try {
            return Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e){
            try {
                Realm.deleteRealm(realmConfiguration);
                //Realm file has been deleted.
                return Realm.getInstance(realmConfiguration);
            } catch (Exception ex){
                throw ex;
                //No Realm file to remove.
            }
        }
    }

    private static final int PERMISSION_REQUEST_WRITE_EXSD = 1002;

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("This app needs to write on SD Card!");
            builder.setMessage("Please grant permisso to write on SD Card");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    ActivityCompat.requestPermissions(MainActivity.this,                  new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },
                            PERMISSION_REQUEST_WRITE_EXSD);
                }
            });
            builder.show();
        } else {
            ActivityCompat.requestPermissions(this,                  new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    PERMISSION_REQUEST_WRITE_EXSD);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_WRITE_EXSD) {
            if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this, "Premission Granted", Toast.LENGTH_SHORT).show();


            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Permission not Granted");
                builder.setMessage("Go to settings to change permisson");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }

                });
                builder.show();

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

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
            String pathImage=recipes.get(i).getImageUri();
            if (pathImage!=null){
                imageView.setImageBitmap(Utils.loadBitmap(pathImage));
            }

            return view;
        }
    }


}
