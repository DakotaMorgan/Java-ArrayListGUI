/**
(1) Name: Dakota Morgan  
(2) Date: 11/4/2019 
(3) Instructor: Ms Tucker 
(4) Class: CIT249 Java II  
(5) Purpose: Display a GUI for editing an ArrayList and seeing the results displayed. 
    Will display confirmation before removing any record, and before saving changes.
    Writes to new Volunteers2.csv file.
**/

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.File;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import java.util.ArrayList;

public class VolunteerListPane extends HBox
{
    /**
     * Declare and initialize label objects
     */
    private Label firstNameLabel = new Label("First Name: ");
    private Label lastNameLabel = new Label("Last Name: ");
    private Label emailLabel = new Label("Email: ");
    private Label indexLabel = new Label("Index: ");
    
    private Label listLabel = new Label("");
    
    /**
     * Declare input objects
     */
    private TextField firstNameTextField;
    private TextField lastNameTextField;
    private TextField emailTextField;
    private TextField indexTextField;
   
    /**
     * Declare button objects
     */
    private Button addRecordButton;
    private Button removeRecordButton;
    private Button exitButton;
    
    /**
     * Declare alert objects
     */
    private Alert removalConfirmationDialog;
    private Alert exitConfirmationDialog;
    
    /**
     * Declare and initialize temp string for listLabel
     */
    private String tempListLabel = "";
        
    
    VolunteerListPane()
    {
    /**
     * Initialize button objects
     */
    addRecordButton = new Button("Add Record");
    removeRecordButton = new Button("Remove Record");
    exitButton = new Button("Exit");
         
    /**
     * Set actions on buttons using method reference
     */
    addRecordButton.setOnAction(this::processButtonClick);
    removeRecordButton.setOnAction(this::processButtonClick);
    exitButton.setOnAction(this::processButtonClick);
        
    /**
     * Initialize new text field objects
     */
    firstNameTextField = new TextField();
    lastNameTextField = new TextField();
    emailTextField = new TextField();
    indexTextField = new TextField();

    /**
     * Create HBox containers
     */
    HBox addFirstNameHBox = new HBox(5, firstNameLabel, firstNameTextField);
    HBox addLastNameHBox = new HBox(5, lastNameLabel, lastNameTextField);
    HBox addEmailHBox = new HBox(5, emailLabel, emailTextField);
    HBox addRecordButtonHBox = new HBox(12, addRecordButton);
    
    HBox removeIndexHBox = new HBox(5, indexLabel, indexTextField);
    HBox removeRecordButtonHBox = new HBox(12, removeRecordButton);
    HBox exitButtonHBox = new HBox(12, exitButton);
    
    /**
     * Create VBox containers
     */
    VBox editRecordVBox = new VBox(5, addFirstNameHBox, addLastNameHBox, addEmailHBox
    , addRecordButtonHBox, removeIndexHBox, removeRecordButtonHBox, exitButtonHBox);
    
    VBox volunteerListVBox = new VBox(5);
    
    /**
     * Create listLabel and add to VBox
     */
    setListLabel();
    volunteerListVBox.getChildren().addAll(listLabel);

    /**
     * Set Pane formatting
     */
    setPadding(new Insets(10, 10, 10, 10));
    setMinSize(440, 600);
    setSpacing(10);
    
    /**
     * Add nodes to pane
     */
    getChildren().addAll(editRecordVBox, volunteerListVBox);
    }

    /**
     * Button methods: will be executed when the buttons are clicked
     */
    private void processButtonClick(ActionEvent event)
    {
        if (event.getSource() == addRecordButton){
            /**
            * Confirm fields are not blank before proceeding
            */
            if (firstNameTextField.getText().length() > 0 && lastNameTextField.getText().length() > 0
            && emailTextField.getText().length() > 0){
                /**
                * Add record to ArrayList
                */
                Driver.addVolunteerListItem(lastNameTextField.getText() + "," + firstNameTextField.getText()
                + "," + emailTextField.getText());
                
                setListLabel();
            }
        }
        else if (event.getSource() == removeRecordButton){
            if(Integer.parseInt(indexTextField.getText()) <= Driver.getVolunteerListSize()
            && Integer.parseInt(indexTextField.getText()) > 0){
                /**
                * Create confirmation alert confirming removal
                */
                removalConfirmationDialog = new Alert(AlertType.CONFIRMATION);
                removalConfirmationDialog.setHeaderText(null);
                removalConfirmationDialog.setContentText("The following record will be removed: \n"  
                + Driver.getVolunteerListItem(Integer.parseInt(indexTextField.getText()) - 1));
                
                Optional<ButtonType> another = removalConfirmationDialog.showAndWait();
                
                /**
                * Remove record if confirmed
                */
                if(another.get() == ButtonType.OK){
                    Driver.removeVolunteerListItem(Integer.parseInt(indexTextField.getText()) - 1);
                }
                
                setListLabel();
            }
        }
        else{
            /**
            * Create confirmation alert to save changes
            */
            exitConfirmationDialog = new Alert(AlertType.CONFIRMATION);
            exitConfirmationDialog.setHeaderText(null);
            exitConfirmationDialog.setContentText("Save changes before exiting?");
            
            Optional<ButtonType> another = exitConfirmationDialog.showAndWait();
            
            if(another.get() == ButtonType.OK){   
                /**
                * Call function in driver to write new Volunteers2.csv file
                */
                try{
                    Driver.createOutputFile();
                }
                catch(Exception e){
                    
                }
            }
            /**
            * Close app
            */
            System.exit(0);
        }
    }
    
    /**
     * Set list label text
     */
    public void setListLabel()
    {
        tempListLabel = "";
        
        for(int i = 0; i < Driver.getVolunteerListSize(); i++){
            tempListLabel += (i + 1) + ") " + Driver.getVolunteerListItem(i) + "\n";
        }
    
        listLabel.setText(tempListLabel);
    }
}