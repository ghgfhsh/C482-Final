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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jeremiah McElroy
 */
public class AddPartScreenController implements Initializable {

    //get refrences to the text fields
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
    @FXML
    private TextField changingTextField; //field will change depending on radio button selection

    //Get refrences to needed labels
    @FXML
    private Label ChangingLabel;
    @FXML
    Label errorLabel;

    //This creates and instance for the Radio Buttons and a toggle group for them
    @FXML
    private RadioButton inHouse;
    @FXML
    private RadioButton outSourched;
    private final ToggleGroup radiotogglegroup = new ToggleGroup();

    public void savedButtonPressed(ActionEvent event) {
        //catches exceptions in user entry
        try {
            //throws exception if the name entry is invalid so it is caught
            if (nameField.getText().isEmpty()) {
                throw new Exception();
            }
            //verifies that the inventory is less than min and more than max and that min is less than max
            if (Integer.parseInt(minField.getText()) <= Integer.parseInt(invField.getText()) && Integer.parseInt(invField.getText()) <= Integer.parseInt(maxField.getText())) {
                int partIndex;
                int max = 0;
                DataModel.Part inhousetemp = new DataModel.InHouse();
                DataModel.Part outsourcedtemp = new DataModel.Outsourced();

                //Agorithm to find the lowest available index
                for (DataModel.Part part : C482Final.inventory.getAllParts()) {
                    if (max < part.getId()) {
                        max = part.getId();
                    }
                }
                max++; // this puts max as the next highest number

                //checks if inhouse or outsourced, then makes an instance of inhouse or outsourced to update the inventory with the new information
                if (radiotogglegroup.getSelectedToggle().equals(inHouse)) {
                    inhousetemp.setId(max);
                    inhousetemp.setName(nameField.getText());
                    inhousetemp.setStock(Integer.parseInt(invField.getText()));
                    inhousetemp.setPrice(Double.parseDouble(priceField.getText()));
                    inhousetemp.setMax(Integer.parseInt(maxField.getText()));
                    inhousetemp.setMin(Integer.parseInt(minField.getText()));
                    ((DataModel.InHouse) inhousetemp).setMachineId(Integer.parseInt(changingTextField.getText()));
                    C482Final.inventory.AddPart(inhousetemp);
                } else if (radiotogglegroup.getSelectedToggle().equals(outSourched)) {
                    outsourcedtemp.setId(max);
                    outsourcedtemp.setName(nameField.getText());
                    outsourcedtemp.setStock(Integer.parseInt(invField.getText()));
                    outsourcedtemp.setPrice(Double.parseDouble(priceField.getText()));
                    outsourcedtemp.setMax(Integer.parseInt(maxField.getText()));
                    outsourcedtemp.setMin(Integer.parseInt(minField.getText()));
                    ((DataModel.Outsourced) outsourcedtemp).setCompanyName(changingTextField.getText());
                    C482Final.inventory.AddPart(inhousetemp);
                }

                //closes window after hitting save
                //Create a Scene for the Main Window
                Parent MainParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene MainScene = new Scene(MainParent);

                //Get the Stage Info
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(MainScene);
                window.show();
            } else {
                errorLabel.setText("Min must be less than max and inventory must be inbetween");
            }
        } catch (Exception e) {
            errorLabel.setText("Invalid Entry");
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

    //Will trigger update when radio buttons are selected
    public void RadioButtonChanged(ActionEvent event) {
        if (this.radiotogglegroup.getSelectedToggle().equals(this.outSourched)) {
            ChangingLabel.setText("Company Name");
            changingTextField.setPromptText("Comp Nm");
        }
        if (this.radiotogglegroup.getSelectedToggle().equals(this.inHouse)) {
            ChangingLabel.setText("Machine ID");
            changingTextField.setPromptText("Mach ID");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Radio Button Configuration
        this.inHouse.setToggleGroup(radiotogglegroup);
        this.outSourched.setToggleGroup(radiotogglegroup);
    }

}
