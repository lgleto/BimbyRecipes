package ipca.example.bimbyrecipes;

/**
 * Created by lourenco on 18/10/17.
 */

public class Ingredient {

    String title;
    Float qtd;

    public Ingredient(String title, Float qtd) {
        this.title = title;
        this.qtd = qtd;
    }

    public Ingredient() {
        this.title = "";
        this.qtd = 0.f;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getQtd() {
        return qtd;
    }

    public void setQtd(Float qtd) {
        this.qtd = qtd;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "title='" + title + '\'' +
                ", qtd=" + qtd +
                '}';
    }
}
