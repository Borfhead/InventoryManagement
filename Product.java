/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorymanagement;

import java.util.ArrayList;

/**
 *
 * @author Dylan
 */
public class Product {
    
    private ArrayList<Part> associatedParts;
    private int productID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;
    
    public Product(){
        name = "";
        price = 0.0;
        inStock = 0;
        min = 0;
        max = 0;
    }
    
    public Product(String name, double price, int inStock,
            int min, int max){
        this.productID = -1;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
        
    }
    
    public Product(int productID, String name, double price, int inStock,
            int min, int max){
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }
    
    public void setName(String newName){
        name = newName;
    }
    
    public String getname(){
        return name;
    }
    
    public void setPrice(double newPrice){
        price = newPrice;
    }
    
    public void setInStock(int num){
        inStock = num;
    }
    
    public int getInStock(){
        return inStock;
    }
    
    public void setMin(int newMin){
        min = newMin;
    }
    
    public int getMin(){
        return min;
    }
    
    public void setMax(int newMax){
        max = newMax;
    }
    
    public int getMax(){
        return max;
    }
    
    public void addAssociatedPart(Part toAdd){
        associatedParts.add(toAdd);
    }
    
    public boolean removeAssociatedPart(int index){
        try{
            associatedParts.remove(index);
            return true;
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Index is out of bounds");
            return false;
        }
    }
    
    public Part lookupAssociatedPart(int index){
        try{
            return associatedParts.get(index);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Index is out of bounds");
            return null;
        }
        
    }
    
    public void setProductID(int newID){
        productID = newID;
    }
    
    public int getProductID(){
        return productID;
    }
}
