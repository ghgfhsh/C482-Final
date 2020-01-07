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




import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jeremiah McElroy
 */
public class MainScreenController implements Initializable {

    
    
    //initialize main menu part table
    @FXML private TableView<DataModel.Part> partTable;
    @FXML private TableColumn<DataModel.Part, String> partIdColumn;
    @FXML private TableColumn<DataModel.Part, String> partNameColumn;
    @FXML private TableColumn<DataModel.Part, String> partInvLevelColumn;
    @FXML private TableColumn<DataModel.Part, String> partPriceColumn;
    
    //initialize main menu product table
    @FXML private TableView<DataModel.Product> prodTable;
    @FXML private TableColumn<DataModel.Product, String> prodIdColumn;
    @FXML private TableColumn<DataModel.Product, String> prodNameColumn;
    @FXML private TableColumn<DataModel.Product, String> prodInvLevelColumn;
    @FXML private TableColumn<DataModel.Product, String> prodPriceColumn;
    
    //get search text field refrenced
    @FXML private TextField partSearchField;
    @FXML private TextField prodSearchField;
    
    //get reference to needed labels
    @FXML private Label errorLabel;

    //add part search button implementation
    public void SearchPartButtonPushed(){
        //searches for part and updates the table to show only parts containing search, if search is empty then doesn't search
        if(!"".equals(partSearchField.getText())){
            partTable.setItems(C482Final.inventory.lookupPart(partSearchField.getText()));
        }
        else{
            partTable.setItems(C482Final.inventory.getAllParts());
        }       
    }
    
    public void SearchProdButtonPushed(){
        //searches for product and updates the table to show only parts containing search, if search is empty then doesn't search
        if(!"".equals(prodSearchField.getText())){
            prodTable.setItems(C482Final.inventory.lookupProduct(prodSearchField.getText()));
        }
        else{
            prodTable.setItems(C482Final.inventory.getAllProducts());
        }
    }
    
    //add part delete button implementation
    public void DeletePartButtonPushed(){
        ObservableList<DataModel.Part> selectedRows;
        //grabs all the selected items and puts them in a list
        selectedRows = partTable.getSelectionModel().getSelectedItems();
        
        //for loop to remove parts. Using lambda to mix it up for practice
        selectedRows.stream().forEach((part) -> {
            C482Final.inventory.deletePart(part);
        });
    }
    
    //add product delete button implementation
    public void DeleteProductButtonPushed(){
        ObservableList<DataModel.Product> selectedRows;
        //grabs all the selected items and puts them in a list
        selectedRows = prodTable.getSelectionModel().getSelectedItems();
        
        //for loop to remove parts. Using lambda to mix it up for practice
        selectedRows.stream().forEach((product) -> {
            C482Final.inventory.deleteProduct(product);
        });    
    }
        
    
    //Add Add Part Button Implementation 
    public void AddPartButtonPushed(ActionEvent event) throws IOException{
        //Create a Scene for the Add Part Screen
        Parent AddPartParent = FXMLLoader.load(getClass().getResource("AddPartScreen.fxml"));
        Scene AddPartScene = new Scene(AddPartParent);
        
        //Get the Stage Info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(AddPartScene);
        window.show();
    }
    
    //Add Modify Part Button Implementation 
    public void ModifyPartButtonPushed(ActionEvent event) throws IOException{
        
        //checks that you have something selected to prevent IOException
        try{
        //bit of a different way to call the loader, so we can pass variables over to the new screen. 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyPartScreen.fxml"));

            //Create a Scene for the Add Part Screen
            Parent ModifyPartParent = loader.load();
            Scene ModifyPartScene = new Scene(ModifyPartParent);

            //access the controller to call a method
            ModifyPartScreenController controller = loader.getController();

            //Pass selected person to modify


            controller.initData(partTable.getSelectionModel().getSelectedItem());


            //Get the Stage Info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(ModifyPartScene);
            window.show();
        }
        catch(Exception e){
            System.out.print("No Selection Made\n");
            errorLabel.setText("You must make a selection to modify a part");
        }
    }

    //Add Add Product Button Implementation 
    public void AddProductButtonPushed(ActionEvent event) throws IOException{
        //Create a Scene for the Add Part Screen
        Parent ModifyProductParent = FXMLLoader.load(getClass().getResource("AddProductScreen.fxml"));
        Scene ModifyProductScene = new Scene(ModifyProductParent);
        
        //Get the Stage Info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(ModifyProductScene);
        window.show();
    }
    
     //Add Modify Product Button Implementation 
    public void ModifyProductButtonPushed(ActionEvent event) throws IOException{
        //checks that you have something selected to prevent IOException
        try{
        //bit of a different way to call the loader, so we can pass variables over to the new screen. 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyProductScreen.fxml"));

            //Create a Scene for the Add Part Screen
            Parent ModifyProductParent = loader.load();
            Scene ModifyPartScene = new Scene(ModifyProductParent);

            //access the controller to call a method
            ModifyProductScreenController controller = loader.getController();

            //Pass selected person to modify
            controller.initData(prodTable.getSelectionModel().getSelectedItem());


            //Get the Stage Info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(ModifyPartScene);
            window.show();
        }
        catch(Exception e){
            System.out.print("No Selection Made\n");
            errorLabel.setText("You must make a selection to modify a product");
        }
    }
    
    
    //Exit Button Implementation
    public void ExitButtonPushed(ActionEvent event){
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the part table
        partIdColumn.setCellValueFactory(new PropertyValueFactory<DataModel.Part, String>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<DataModel.Part, String>("name"));
        partInvLevelColumn.setCellValueFactory(new PropertyValueFactory<DataModel.Part, String>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<DataModel.Part, String>("price"));
        //setup columns in the product table
        prodIdColumn.setCellValueFactory(new PropertyValueFactory<DataModel.Product, String>("id"));
        prodNameColumn.setCellValueFactory(new PropertyValueFactory<DataModel.Product, String>("name"));
        prodInvLevelColumn.setCellValueFactory(new PropertyValueFactory<DataModel.Product, String>("stock"));
        prodPriceColumn.setCellValueFactory(new PropertyValueFactory<DataModel.Product, String>("price"));

        //allows you to select multiple rows to delete
        partTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        prodTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        //Load Parts from inventory into the TableView
        partTable.setItems(C482Final.inventory.getAllParts());
        
        //Load products from C482Final.inventory into the Table View
        prodTable.setItems(C482Final.inventory.getAllProducts());   
        
        //sorts tables in the right order because it doesn't always like to do that
        partTable.getSortOrder().add(partIdColumn);
        prodTable.getSortOrder().add(prodIdColumn);
    }    
    
    
    
    
}
