package Model.ProductPackage.category;

public class Food extends MyAdapter{
    private String foodCategory;
    public Food(String foodCategory){
        this.foodCategory = foodCategory;
    }
    public String getCategory(){
        return  foodCategory;
    }

}
