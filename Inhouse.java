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
public class Inhouse extends Part{
    
    
    private int machineID;
    
    public Inhouse(){
        super();
        machineID = 0;
    }
    
    public Inhouse(String name, double price, int inStock,
            int min, int max, int machineID){
        super(name, price, inStock, min, max);
        this.machineID = machineID;
    }
    
    public Inhouse(int partID, String name, double price, int inStock,
            int min, int max, int machineID){
        super(partID, name, price, inStock, min, max);
        this.machineID = machineID;
    }
    
    public void setMachineID(int newID){
        machineID = newID;
    }
    
    public int getMachineID(){
        return machineID;
    }
    
}
