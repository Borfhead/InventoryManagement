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
public class Outsourced extends Part{
    
    private String companyName;
    
    public Outsourced(){
        super();
        companyName = "";
    }
    
    public Outsourced(String name, double price, int inStock,
            int min, int max, String companyName){
        super(name, price, inStock, min, max);
        this.companyName = companyName;
    }
    
    public Outsourced(int partID, String name, double price, int inStock,
            int min, int max, String companyName){
        super(partID, name, price, inStock, min, max);
        this.companyName = companyName;
    }
    
    public String getCompanyName(){
        return companyName;
    }
    
    public void setCompanyName(String newName){
        companyName = newName;
    }
}
