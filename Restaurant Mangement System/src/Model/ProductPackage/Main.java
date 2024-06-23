package Model.ProductPackage;

import Model.ProductPackage.category.Drink;
import Model.ProductPackage.category.Food;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        //creating of a base product
        GenericBasedProduct<Drink> originalProduct = new GenericBasedProduct<Drink>("pName",10,new Drink(0));
        ComboDecorator comboDecorator =
                new ComboProductDecorator<Food>
                        (new GenericBasedProduct<Food>("pNameDecorator",100,new Food("fastfood")),
                                "speical",50);
        printProductDetails(originalProduct);
        printProductDetails(comboDecorator);
    }
    private static void printProductDetails(Product product) {
        System.out.println("Name: " + product.getName());
        System.out.println("Price: " + product.getPrice());
        System.out.println("Category : " + product.getCategory());

        if (product instanceof ComboDecorator) {
            ComboDecorator comboDecorator = (ComboDecorator) product;
            System.out.println("Combo Type: " + comboDecorator.getComboType());
        }

        System.out.println();
    }
}
