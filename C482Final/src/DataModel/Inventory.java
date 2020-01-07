/* 
 * Copyright (C) 2019 Jeremiah McElroy
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package DataModel;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jeremiah McElroy
 */
public class Inventory {
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public void AddPart(Part newPart){
        allParts.add(newPart);
    }
    
    public void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    public Part lookupPart(int partID){
        Part foundPart = null; // set to null in order to check for anything was found. 
        for (Part searchedpart : allParts) {
            if(searchedpart.getId() == partID){
                foundPart = searchedpart;
                break;
            }
        }
        
       return foundPart;
    }
    
    public ObservableList<Part> lookupPart(String partName){
       ObservableList<Part> foundPartsList = FXCollections.observableArrayList();
        
            for(Part foundpart: allParts){
               if(foundpart.getName().toLowerCase().contains(partName.toLowerCase())){
                   foundPartsList.add(foundpart);
               }
            }

       return foundPartsList;
    }
    
    public Product lookupProduct(int productID){
        Product foundProduct = null; // set to null in order to check for anything was found. 
        for (Product searchedProduct : allProducts) {
            if(searchedProduct.getId() == productID){
                foundProduct = searchedProduct;
                break;
            }
        }
        
       return foundProduct;
    }
    
     public ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
            for (Product searchedproduct : allProducts) {
                if(searchedproduct.getName().toLowerCase().contains(productName.toLowerCase())){
                foundProducts.add(searchedproduct);
            }
        }
        
       return foundProducts;
    }
     
     public void updatePart(int index, Part selectedPart){
         //removes the Part then readds it as a modified part to the same spot as the old one
         allParts.remove(index);
         allParts.add(selectedPart);
     }
     
     public void updateProduct(int index, Product newProduct){
         //removes the Product then readds it as a modified Product to the same spot as the old one
         allProducts.remove(index);
         allProducts.add(newProduct);
     }
     
     public boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
     }
     
     public boolean deleteProduct(Product selectedProduct){
         return allProducts.remove(selectedProduct);
     }
     
     public ObservableList<Part> getAllParts(){   
         return allParts; 
     }
     
     public ObservableList<Product> getAllProducts(){
         return allProducts;
     }
    
    
}
