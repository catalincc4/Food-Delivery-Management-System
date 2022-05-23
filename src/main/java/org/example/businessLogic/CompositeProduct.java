package org.example.businessLogic;

import java.util.List;

public class CompositeProduct extends MenuItem{
    private List<BaseProduct> productList;

    public void setProductList(List<BaseProduct> productList) {
        this.productList = productList;
    }

    public List<BaseProduct> getProductList() {
        return productList;
    }

    @Override
    public Integer computePrice(){
        Integer p = 0;
        for (BaseProduct baseProduct: productList) {
            p += baseProduct.getPrice();
        }
        return p;
    }
@Override
    public Double getRating(){
       Double p = 0.0;
        for (BaseProduct baseProduct: productList) {
            p += baseProduct.getRating();
        }
        return p/productList.size();
    }
@Override
    public Integer getCalories(){
        Integer p = 0;
        for (BaseProduct baseProduct: productList) {
            p += baseProduct.getCalories();
        }
        return p;
    }
@Override
    public Integer getProtein(){
        Integer p = 0;
        for (BaseProduct baseProduct: productList) {
            p += baseProduct.getProtein();
        }
        return p;
    }
@Override
    public Integer getFat(){
        Integer p = 0;
        for (BaseProduct baseProduct: productList) {
            p += baseProduct.getFat();
        }
        return p;
    }
@Override
    public Integer getSodium(){
        Integer p = 0;
        for (BaseProduct baseProduct: productList) {
            p += baseProduct.getSodium();
        }
        return p;
    }
}
