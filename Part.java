/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorymanagement;

/**
 *
 * @author Dylan
 */
public abstract class Part {
    
    private int partID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;
    
    
    public Part(){
        partID = 0;
        name = "";
        price = 0.0;
        inStock = 0;
        min = 0;
        max = 0;
    }
    
    public Part(String name, double price, int inStock, int min, int max){
        partID = -1;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }
    
    public Part(int partID, String name, double price, int inStock, int min, int max){
        this.partID = partID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }
    
    public void setName(String newName){
        name = newName;
    }
    
    public String getName(){
        return name;
    }
    
    public void setPrice(double newPrice){
        price = newPrice;
    }
    
    public double getPrice(){
        return price;
    }
    
    public void setIntStock(int newStock){
        inStock = newStock;
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
    
    public void setPartID(int newID){
        partID = newID;
    }
    
    public int getPartID(){
        return partID;
    }
}
