package Model.ProductPackage.category;

public class Drink implements ProductCategory{
    private String type;
    public Drink(int type){
        if(type == 0) this.type = "cold";
        if(type == 1) this.type = "hot";
    }
    public Drink(){}


    @Override
    public String getCategoryName() {
        return "Drink";
    }

    @Override
    public String getCategoryType() {
        return type;
    }

    @Override
    public void setCategory(int type) {
        if(type == 0) this.type = "cold";
        if(type == 1) this.type = "hot";
    }

    @Override
    public String getCategory() {
        return this.getCategoryType()+" "+this.getCategoryName();
    }
}
