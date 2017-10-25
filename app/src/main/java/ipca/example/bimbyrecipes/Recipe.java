package ipca.example.bimbyrecipes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lourenco on 18/10/17.
 */

public class Recipe {

    String title;
    String procedures;
    String imageUri;
    List<Ingredient> ingredients = new ArrayList<>();

    public Recipe(String title, String procedures, String imageUri, List<Ingredient> ingredients) {
        this.title = title;
        this.procedures = procedures;
        this.imageUri = imageUri;
        this.ingredients = ingredients;
    }

    public Recipe() {
        this.title = "";
        this.procedures = "";
        this.imageUri = "";
        this.ingredients = new ArrayList<>();
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
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
}
