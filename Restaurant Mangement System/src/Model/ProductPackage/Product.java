package Model.ProductPackage;

import Model.ProductPackage.category.ProductCategory;

public interface Product<T> {
    String getName();

    int getPrice();

    String getCategory();

    int getComboPrice();

    String getComboType();
}
