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

/**
 *
 * @author Jeremiah McElroy
 */
public class Outsourced extends Part{
    private String companyName;
    
   public Outsourced(){
        this.id = -1;
        this.name = "Name";
        this.price = 0.00;
        this.stock = 0;
        this.min = 0;
        this.max = 0;
        this.companyName = "Company Name";
    }
    
    //overload default constructor
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.companyName = companyName;
    }
    
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    
    public String getCompanyName(){
        return companyName;
    }
}
