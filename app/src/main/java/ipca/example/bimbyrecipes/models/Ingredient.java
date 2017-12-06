package ipca.example.bimbyrecipes.models;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by lourenco on 18/10/17.
 */

public class Ingredient {

    String title;
    String qtd;


    Recipe recipe;

    public Ingredient(String title, String qtd) {
        this.title = title;
        this.qtd = qtd;
    }

    public Ingredient() {
        this.title = "";
        this.qtd = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }


    @Override
    public String toString() {
        return "Ingredient{" +
                "title='" + title + '\'' +
                ", qtd=" + qtd +
                '}';
    }


}
