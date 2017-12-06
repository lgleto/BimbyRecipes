package ipca.example.bimbyrecipes.models;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import ipca.example.bimbyrecipes.models.Ingredient;

/**
 * Created by lourenco on 18/10/17.
 */

public class Recipe  {

    String title;
    String procedures;
    String imageUri;


    public Recipe(String title, String procedures, String imageUri, RealmList<Ingredient> ingredients) {
        this.title = title;
        this.procedures = procedures;
        this.imageUri = imageUri;
    }

    public Recipe() {
        this.title = "";
        this.procedures = "";
        this.imageUri = "";

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProcedures() {
        return procedures;
    }

    public void setProcedures(String procedures) {
        this.procedures = procedures;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }


    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", procedures='" + procedures + '\'' +
                ", imageUri='" + imageUri + '\'' +
                '}';
    }


}
