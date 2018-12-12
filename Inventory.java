
package inventorymanagement;
import java.util.ArrayList;
import java.util.HashSet;
/**
 *
 * @author Dylan
 */


public class Inventory {
   private ArrayList<Product> products;
   private ArrayList<Part> allParts;
   private int partIDCounter = 0;
   private int productIDCounter = 0;
   
   public Inventory(){
       products = new ArrayList<>();
       allParts = new ArrayList<>();
   }
   
   public Inventory(ArrayList<Product> prod, ArrayList<Part> parts){
       products = prod;
       allParts = parts;
   }
   
   
   
   public void addProduct(Product product){
       product.setProductID(productIDCounter);
       products.add(product);
       productIDCounter++;
   }
   
   public boolean removeProduct(int prodNum){
       try{
       products.remove(prodNum);
       return true;
       }
       catch(IndexOutOfBoundsException e){
           //Temporary error message
           System.out.println("Index is out of bounds");
           return false;
       }
   }
   
   public Product lookupProduct(int prodNum){
       return products.get(prodNum);
   }
   
   public void updateProduct(int prodNum){
       //Put something here
   }
   
   public void addPart(Part toAdd){
       toAdd.setPartID(partIDCounter);
       allParts.add(toAdd);
       partIDCounter++;
   }
   
   public boolean deletePart(Part toDelete){
       return allParts.remove(toDelete);
   }
   
   public Part lookupPart(int partID){
       for(Part p : allParts){
           if(p.getPartID() == partID){
               return p;
           }
       }
       return null;
   }
   
   public void updatePart(int partID){
       //Fill in later.
   }
   
   public void updatePart(int partID, Part newPart){
       for(int i = 0; i < allParts.size(); i++){
           if(allParts.get(i).getPartID() == partID){
               allParts.set(i, newPart);
           }
       }
   }
   
   public ArrayList<Part> getAllParts(){
       return allParts;
   }
   
   public ArrayList<Product> getProducts(){
       return products;
   }
   
}
