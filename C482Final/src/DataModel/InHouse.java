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
public class InHouse extends Part{
    private int machineId;
    
   public InHouse(){
        this.id = -1;
        this.name = "Name";
        this.price = 0.00;
        this.stock = 0;
        this.min = 0;
        this.max = 0;
        this.machineId = 0;
    }
    
    //overload default constructor
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.machineId = machineId;
    }
    
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }
    
    public int getMachineId(){
        return machineId;
    }
    
}
