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
package c482final;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * @author Jeremiah McElroy
 */
public class C482Final extends Application {
    
   //initialized inventory object to hold data
   public static DataModel.Inventory inventory = new DataModel.Inventory();
   
   
   //Populates the Inventory with some fake data to use 
   public void createFakeData(){
        
       //add dummy products
        inventory.addProduct(new DataModel.Product(0, "Computer", 600, 2, 0, 10));
        inventory.addProduct(new DataModel.Product(1, "Desk", 200.3, 1, 0, 5));
        
        //add dummy parts
        inventory.AddPart(new DataModel.InHouse(0, "Graphics Card", 100.25, 3, 0, 10, 7));
        inventory.AddPart(new DataModel.InHouse(1, "CPU", 80, 6, 0, 10, 4));
        inventory.AddPart(new DataModel.InHouse(2, "PSU", 93.25, 3, 0, 10, 7));
        inventory.AddPart(new DataModel.Outsourced(3, "Tape", 25.32, 2, 0, 50, "Teflon"));
        inventory.AddPart(new DataModel.Outsourced(4, "Screws", 30, 32, 20, 100, "Screwsmith"));
        inventory.AddPart(new DataModel.Outsourced(5, "MonitorCable", 22.88, 3, 2, 20, "MonitorCables LLC"));
        inventory.AddPart(new DataModel.Outsourced(6, "DeskLeg", 22.88, 3, 2, 20, "Desk Corp"));
        
        //associate some of the dummy parts and products
        inventory.lookupProduct(0).addAssociatedPart(inventory.lookupPart(0));
        inventory.lookupProduct(0).addAssociatedPart(inventory.lookupPart(1));
        inventory.lookupProduct(0).addAssociatedPart(inventory.lookupPart(5));
        inventory.lookupProduct(1).addAssociatedPart(inventory.lookupPart(3));
        inventory.lookupProduct(1).addAssociatedPart(inventory.lookupPart(4));
        inventory.lookupProduct(1).addAssociatedPart(inventory.lookupPart(6));
        
    }
   
    @Override
    public void start(Stage stage) throws Exception {
        //makes sure you want the dummy data to be loaded with a pop up
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to populate the program with some fake data?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        
        //populate inventory with some data at the start
        if(alert.getResult() == ButtonType.YES)
        createFakeData();
        
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
