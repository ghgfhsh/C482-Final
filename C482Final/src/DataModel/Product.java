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
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    //defaults
    public Product(){
        this.id = -1;
        this.name = "Name";
        this.price = 0.00;
        this.stock = 0;
        this.min = 0;
        this.max = 0;
        
    }
    
    //overload default constructor
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Setter Methods">
    public void setId(int id){
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setPrice(double price){
        this.price = price;
    }
    
    public void setStock(int stock){
        this.stock = stock;
    }
    
    public void setMin(int min){
        this.min = min;
    }
    
    public void setMax(int max){
        this.max = max;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter Methods">
    public int  getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public double getPrice(){
        return price;
    }
    
    public int getStock(){
        return stock;
    }
    
    public int getMin(){
        return min;
    }
    
    public int getMax(){
        return max;
    }
    //</editor-fold>
    
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
    public boolean deleteAssociatedPart(Part selectedAspart){
        return associatedParts.remove(selectedAspart);
    }
    
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
    
}
