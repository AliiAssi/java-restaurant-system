package Model.ProductPackage.forDataBase;

import Model.ProductPackage.Product;

public interface ProductDB<T> {
    int getId();
    T getInstance();
}
