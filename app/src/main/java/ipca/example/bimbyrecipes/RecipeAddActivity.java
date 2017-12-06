package ipca.example.bimbyrecipes;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ipca.example.bimbyrecipes.models.Ingredient;
import ipca.example.bimbyrecipes.models.Recipe;

public class RecipeAddActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imageView;
    Button buttonPhoto;
    EditText editTextTitle;
    EditText editTextProcedure;
    Bitmap bm;

    List<Ingredient> ingredients=new ArrayList<>();
    ListView listViewIngredients;
    IngredientAdapter ingredientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_add);

        imageView = (ImageView) findViewById(R.id.imageView);
        buttonPhoto = (Button) findViewById(R.id.buttonPhoto);
        editTextTitle =(EditText) findViewById(R.id.editTitle);
        editTextProcedure =(EditText)findViewById(R.id.editProcedure);

        buttonPhoto.setOnClickListener(this);

        listViewIngredients=(ListView)findViewById(R.id.listViewIngredients);
        ingredientAdapter=new IngredientAdapter();
        listViewIngredients.setAdapter(ingredientAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addIngredient);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogAddIngredient = new AlertDialog.Builder(RecipeAddActivity.this);
                alertDialogAddIngredient.setTitle("Add ingredient");
                alertDialogAddIngredient.setMessage("");

                final View addIngredientView=getLayoutInflater().inflate(R.layout.view_add_ingridient,null);
                final EditText editTextQtd =(EditText) addIngredientView.findViewById(R.id.editTextDescription);
                final EditText editTextDescription=(EditText) addIngredientView.findViewById(R.id.editTextQTD);
                alertDialogAddIngredient.setView(addIngredientView);

                alertDialogAddIngredient.setPositiveButton("ADD",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Ingredient ingredient=new Ingredient(
                                        editTextQtd.getText().toString(),
                                        editTextDescription.getText().toString());
                                ingredients.add(ingredient);
                                ingredientAdapter.notifyDataSetChanged();
                            }
                        });

                alertDialogAddIngredient.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialogAddIngredient.show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bm!=null){
            imageView.setImageBitmap(bm);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (bm!=null)
            outState.putByteArray("image", Utils.bitmapToByteArray(bm));

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        bm = Utils.byteArrayToBitmap(savedInstanceState.getByteArray("image"));
    }

    private static final int CAMERA_PIC_REQUEST = 1001;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonPhoto:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_PIC_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode== RESULT_OK){
            switch (requestCode){
                case CAMERA_PIC_REQUEST:
                    Bundle bundle=data.getExtras();
                    bm = (Bitmap) bundle.get("data");
                    imageView.setImageBitmap(bm);
                    break;
            }
        }
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
        if (id == R.id.action_save) {
            Recipe recipe = new Recipe(
                    editTextTitle.getText().toString(),
                    editTextProcedure.getText().toString(),
                    Utils.saveBitmap(bm),
                    null);

            Recipe.addItem(recipe);

            for (Ingredient ing :ingredients){
                ing.setRecipe(recipe);
                Ingredient.addItem(ing);
            }

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class IngredientAdapter extends BaseAdapter implements View.OnClickListener{

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public Object getItem(int i) {
            return ingredients.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view==null){
                view=getLayoutInflater().inflate(R.layout.ingredient_row,null);
            }

            TextView textViewQtd = (TextView)view.findViewById(R.id.textViewQTD);
            TextView textViewTitle = (TextView)view.findViewById(R.id.textViewTitle);


            textViewQtd.setText(ingredients.get(i).getQtd());
            textViewTitle.setText(ingredients.get(i).getTitle());

            return view;
        }

        @Override
        public void onClick(View view) {

        }
    }
}

