package ipca.example.bimbyrecipes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RecipeAddActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imageView;
    Button buttonPhoto;
    EditText editTextTitle;
    EditText editTextProcedure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_add);

        imageView = (ImageView) findViewById(R.id.imageView);
        buttonPhoto = (Button) findViewById(R.id.buttonPhoto);
        editTextTitle =(EditText) findViewById(R.id.editTitle);
        editTextProcedure =(EditText)findViewById(R.id.editProcedure);

        buttonPhoto.setOnClickListener(this);
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
                    Bitmap bm = (Bitmap) bundle.get("data");
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
                    editTextProcedure.getText().toString(),"",null);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

