package serega.apps.caloriecounter;

public class Product {
    private String id;
    private String name;
    private String calories_per_hundred;
    private String proteins;
    private String fats;
    private String carbo;

    public Product(){}

    public Product(String name, String calories_per_hundred, String proteins, String fats, String carbo) {
        this.name = name;
        this.calories_per_hundred = calories_per_hundred;
        this.proteins = proteins;
        this.fats = fats;
        this.carbo = carbo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories_per_hundred() {
        return calories_per_hundred;
    }

    public void setCalories_per_hundred(String calories_per_hundred) {
        this.calories_per_hundred = calories_per_hundred;
    }

    public String getProteins() {
        return proteins;
    }

    public void setProteins(String proteins) {
        this.proteins = proteins;
    }

    public String getFats() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats = fats;
    }

    public String getCarbo() {
        return carbo;
    }

    public void setCarbo(String carbo) {
        this.carbo = carbo;
    }
}
