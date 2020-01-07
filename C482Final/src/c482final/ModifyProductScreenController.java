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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
public class ModifyProductScreenController implements Initializable {

    private DataModel.Product selectedProdtoMod;
    private ObservableList<DataModel.Part> addedParts = FXCollections.observableArrayList();

    //Creates inventory datafield refrences except the last field which is in the changing ID field as it changes
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField invField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;

    //Creates refrences for table columns
    //initialize main menu parts available table
    @FXML
    private TableView<DataModel.Part> partTable;
    @FXML
    private TableColumn<DataModel.Part, String> partIdColumn;
    @FXML
    private TableColumn<DataModel.Part, String> partNameColumn;
    @FXML
    private TableColumn<DataModel.Part, String> partInvLevelColumn;
    @FXML
    private TableColumn<DataModel.Part, String> partPriceColumn;

    //initialize main menu parts selected table
    @FXML
    private TableView<DataModel.Part> selectedTable;
    @FXML
    private TableColumn<DataModel.Part, String> selectedIdColumn;
    @FXML
    private TableColumn<DataModel.Part, String> selectedNameColumn;
    @FXML
    private TableColumn<DataModel.Part, String> selectedInvLevelColumn;
    @FXML
    private TableColumn<DataModel.Part, String> selectedPriceColumn;

    @FXML
    private TextField partSearchField;

    @FXML
    private Label errorLabel;

    public void SearchPartButtonPushed() {

        //checks if search field is empty
        if (!"".equals(partSearchField.getText())) {
            partTable.setItems(C482Final.inventory.lookupPart(partSearchField.getText()));
        } else {
            partTable.setItems(C482Final.inventory.getAllParts());
        }

    }

    public void savedButtonPressed(ActionEvent event) {

        //catches exception in the users entry
        try {
            //throws exception if the name entry is invalid so it is caught
            if (nameField.getText().isEmpty()) {
                throw new Exception();
            }

            //checks to make sure the inventory field is less than the max and greater than the min and prints out an error otherwise
            if (Integer.parseInt(minField.getText()) <= Integer.parseInt(invField.getText()) && Integer.parseInt(invField.getText()) <= Integer.parseInt(maxField.getText())) {
                //verifies that the product has at least one part
                if (!addedParts.isEmpty()) {
                    //adds all the cost of the parts together
                    double totalPartsCost = 0;

                    for (DataModel.Part part : addedParts) {
                        totalPartsCost += part.getPrice();
                    }

                    //verifies that the cost of the product is not less than the cost of the parts
                    if (totalPartsCost < Double.parseDouble(priceField.getText())) {
                        int partIndex = C482Final.inventory.getAllProducts().indexOf(selectedProdtoMod);
                        DataModel.Product producttemp = new DataModel.Product();

                        producttemp.setId(Integer.parseInt(idField.getText()));
                        producttemp.setName(nameField.getText());
                        producttemp.setStock(Integer.parseInt(invField.getText()));
                        producttemp.setPrice(Double.parseDouble(priceField.getText()));
                        producttemp.setMax(Integer.parseInt(maxField.getText()));
                        producttemp.setMin(Integer.parseInt(minField.getText()));

                        //gets all added parts to save
                        for (DataModel.Part part : addedParts) {
                            producttemp.addAssociatedPart(part);
                        }

                        C482Final.inventory.updateProduct(partIndex, producttemp);

                        //closes window after hitting save
                        //Create a Scene for the Main Window
                        Parent MainParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                        Scene MainScene = new Scene(MainParent);

                        //Get the Stage Info
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        window.setScene(MainScene);
                        window.show();
                    } else {
                        errorLabel.setText("Price cannot be less than combined price of parts");
                    }
                } else {
                    errorLabel.setText("Product Must have at least one part");
                }
            } else {
                errorLabel.setText("Min must be less than max and inventory must be inbetween");
            }
        } catch (Exception e) {
            errorLabel.setText("Invalid Entry");
        }

    }

    public void initData(DataModel.Product newPart) {
        //assign prodtomod to the product passed from previous window
        selectedProdtoMod = newPart;
        
        //setall textfields to the previous data
        idField.setText(Integer.toString(selectedProdtoMod.getId()));
        nameField.setText(selectedProdtoMod.getName());
        invField.setText(Integer.toString(selectedProdtoMod.getStock()));
        priceField.setText(Double.toString(selectedProdtoMod.getPrice()));
        maxField.setText(Integer.toString(selectedProdtoMod.getMax()));
        minField.setText(Integer.toString(selectedProdtoMod.getMin()));
        //load previously associated parts into the selected table
        selectedTable.setItems(selectedProdtoMod.getAllAssociatedParts());

        //Load all previous associated parts into added parts
        for (DataModel.Part part : selectedProdtoMod.getAllAssociatedParts()) {
            addedParts.add(part);
        }
    }

    public void CancelButtonPushed(ActionEvent event) throws IOException {
        //Create confirmation popup box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Cancel?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        //Check if the user hit yes, and if they did return to the main screen
        if (alert.getResult() == ButtonType.YES) {
            //Create a Scene for the Main Window
            Parent MainParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene MainScene = new Scene(MainParent);

            //Get the Stage Info
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(MainScene);
            window.show();
        }
    }

    public void addButtonPushed() {
        //checks if the part is already associated with the product and if it's not adds the part
        if (!selectedProdtoMod.getAllAssociatedParts().contains(partTable.getSelectionModel().getSelectedItem())) {
            selectedProdtoMod.addAssociatedPart(partTable.getSelectionModel().getSelectedItem());
            addedParts.add(partTable.getSelectionModel().getSelectedItem());
        }
        //reloads the table with updated information
        selectedTable.setItems(selectedProdtoMod.getAllAssociatedParts());
    }

    public void deleteButtonPushed() {
        selectedProdtoMod.deleteAssociatedPart(selectedTable.getSelectionModel().getSelectedItem());
        addedParts.remove(partTable.getSelectionModel().getSelectedItem());
        selectedTable.setItems(selectedProdtoMod.getAllAssociatedParts());
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

        //setup columns in the selected table
        selectedIdColumn.setCellValueFactory(new PropertyValueFactory<DataModel.Part, String>("id"));
        selectedNameColumn.setCellValueFactory(new PropertyValueFactory<DataModel.Part, String>("name"));
        selectedInvLevelColumn.setCellValueFactory(new PropertyValueFactory<DataModel.Part, String>("stock"));
        selectedPriceColumn.setCellValueFactory(new PropertyValueFactory<DataModel.Part, String>("price"));

        //Load Parts from inventory into the products selected view
        partTable.setItems(C482Final.inventory.getAllParts()); 
    }

}
