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

public class Recipe extends RealmObject {

    String title;
    String procedures;
    String imageUri;
    //List<Ingredient> ingredients = new ArrayList<>();
    RealmList<Ingredient> ingredients;

    public Recipe(String title, String procedures, String imageUri, RealmList<Ingredient> ingredients) {
        this.title = title;
        this.procedures = procedures;
        this.imageUri = imageUri;
        this.ingredients = ingredients;
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

    public RealmList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(RealmList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", procedures='" + procedures + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }

    public static void addItem(final Recipe recipe){
        Realm realm=Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                Recipe recipeNew=realm.createObject(Recipe.class);
                recipeNew.setTitle(recipe.getTitle());
                recipeNew.setProcedures(recipe.getProcedures());
                recipeNew.setImageUri(recipe.getImageUri());
                recipeNew.setIngredients(recipe.getIngredients());
            }
        });
    }
}
