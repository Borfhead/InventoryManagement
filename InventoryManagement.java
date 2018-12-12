
package inventorymanagement;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author Dylan
 */
public class InventoryManagement extends Application {
    
    private Scene scene;
    private BorderPane partsPane;
    private ToolBar partsTopTools;
    private Inventory inventory;
    private Button searchPartsBtn;
    private TextField searchPartsField;
    private Button searchProdBtn;
    private TextField searchProdField;
    private ToolBar partsBottomTools;
    private Button addPartsBtn;
    private Button modifyPartsBtn;
    private Button deletePartsBtn;
    private TableView<Part> partsTable;
    private ObservableList<Part> partsList;
    private HBox mainPane;
    
    @Override
    public void start(Stage primaryStage) {
        
        //Set up inventory and add a default test part
        inventory = new Inventory();
        inventory.addPart(new Inhouse("Test Part", 1.25, 10,
            5, 15, 65412));
        inventory.addProduct(new Product("Test Product", 500.00, 10, 5, 15));
        
        //Initiate parts list to populate the TableView with
        partsList = FXCollections.observableArrayList(inventory.getAllParts());
        
        //Set up top toolbar header, search button, and search field
        partsTopTools = new ToolBar();
        Label partsLabel = new Label("Parts");
        partsLabel.getStyleClass().add("header");
        searchPartsBtn = new Button("Search");
        searchPartsField = new TextField();
        searchPartsBtn.setOnAction(e -> {
            searchParts();
        });
        addSearchResetListener(searchPartsField);
        addIntListener(searchPartsField);
        partsTopTools.getItems().addAll(partsLabel, new Separator(), searchPartsBtn, searchPartsField);
        partsTopTools.getStyleClass().add("toolbar");
        
        //Set up and populate TableView.
        partsTable = new TableView<>();
        TableColumn partsCol = new TableColumn("PartsID");
        partsCol.setPrefWidth(150);
        partsCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        TableColumn partsNameCol = new TableColumn("Part Name");
        partsNameCol.setPrefWidth(150);
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn partsInvLvlCol = new TableColumn("Inventory Level");
        partsInvLvlCol.setPrefWidth(150);
        partsInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        TableColumn partsPriceCol = new TableColumn("Price/Cost per Unit");
        partsPriceCol.setPrefWidth(150);
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        partsTable.getColumns().addAll(partsCol, partsNameCol, partsInvLvlCol,
                partsPriceCol);
        partsTable.setEditable(false);
        partsTable.setItems(partsList);
                
        //Create bottom toolbar with Add, Modify, and Delete buttons
        //and respective action handlers.
        partsBottomTools = new ToolBar();
        addPartsBtn = new Button("Add");
        addPartsBtn.setOnAction(e -> {
           openAddPartMenu();
        });
        
        modifyPartsBtn = new Button("Modify");
        modifyPartsBtn.setOnAction(e -> {
           if(partsTable.getSelectionModel().getSelectedItem() == null){
               Alert alert = new Alert(AlertType.WARNING);
               alert.setHeaderText("Please select a part to modify");
               alert.showAndWait();
           }
           else{
               openModifyPartMenu(partsTable.getSelectionModel().getSelectedItem());
           }
        });
        
        deletePartsBtn = new Button("Delete");
        deletePartsBtn.setOnAction(e -> {
            inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
            partsList.setAll(inventory.getAllParts());
        });
        partsBottomTools.getItems().addAll(addPartsBtn, modifyPartsBtn, deletePartsBtn);
        partsBottomTools.getStyleClass().add("toolbar");
        
        
        //Set up partsPane BorderPane and populate top, center, and bottom nodes.
        partsPane = new BorderPane();
        partsPane.setTop(partsTopTools);
        partsPane.setCenter(partsTable);
        partsPane.setBottom(partsBottomTools);
        partsPane.prefWidthProperty().bind(primaryStage.widthProperty());
        partsPane.prefHeightProperty().bind(primaryStage.heightProperty());
        
        
        
        //Set up and populate main HBox Pane that contains Part and Product
        //menus.
        mainPane = new HBox();
        mainPane.getChildren().add(partsPane);
        
        //Set up scene and primaryStage.
        scene = new Scene(mainPane);
        scene.getStylesheets().add("/inventorymanagement/style.css");
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.setWidth(750);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void openAddPartMenu(){
        
        //Initialize all Labels for the add part menu.
        Label header = new Label("Add Part");
        header.getStyleClass().add("header");
        Label idLabel = new Label("ID:");
        Label nameLabel = new Label("Name:");
        Label invLabel = new Label("In Stock:");
        Label priceLabel = new Label("Price:");
        Label maxLabel = new Label("Max:");
        Label minLabel = new Label("Min:");
        Label machineIDLabel = new Label ("Machine ID");
        Label companyNameLabel = new Label("Company Name:");
        companyNameLabel.setDisable(true);
        
        //Create text fields for the add part menu.  Company name is disabled
        //by default.
        TextField idField = new TextField();
        idField.setPromptText("Auto Gen - Disabled");
        idField.setDisable(true);
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField invField = new TextField();
        invField.setPromptText("In Stock");
        addIntListener(invField);
        TextField priceField = new TextField();
        priceField.setPromptText("Price/Cost");
        addDoubleListener(priceField);
        TextField maxField = new TextField();
        maxField.setPromptText("Max");
        maxField.setMaxWidth(60);
        addIntListener(maxField);
        TextField minField = new TextField();
        minField.setPromptText("Min");
        minField.setMaxWidth(60);
        addIntListener(minField);
        TextField machineIDField = new TextField();
        machineIDField.setPromptText("Machine ID");
        addIntListener(machineIDField);
        TextField companyNameField = new TextField();
        companyNameField.setPromptText("Company Name");
        companyNameField.setDisable(true);
        
        //Create radio buttons for selection of part type.  In house
        //is selected by default on all new parts. Machine ID labels and text
        //fields are disabled if the "In House" radio button is selected.
        //Machine ID label and text fields disabled when "Outsourced" radio
        //button is selected.
        RadioButton inHouseBtn = new RadioButton("In-house");
        inHouseBtn.setSelected(true);
        RadioButton outSourcedBtn = new RadioButton("Outsourced");
        ToggleGroup btnGroup = new ToggleGroup();
        inHouseBtn.setToggleGroup(btnGroup);
        outSourcedBtn.setToggleGroup(btnGroup);
        btnGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> obs, Toggle oldT, Toggle newT) -> {
            if(newT == inHouseBtn){
                machineIDLabel.setDisable(false);
                machineIDField.setDisable(false);
                companyNameLabel.setDisable(true);
                companyNameField.setDisable(true);
                companyNameField.clear();
            }
            else if(newT == outSourcedBtn){
                companyNameLabel.setDisable(false);
                companyNameField.setDisable(false);
                machineIDLabel.setDisable(true);
                machineIDField.setDisable(true);
                machineIDField.clear();
            }
        });
        
        
        //Add toolbar that mimics the looks of the main screen toolbar.
        ToolBar top = new ToolBar();
        top.getItems().addAll(header, new Separator());
        
        //Create GridPane and populates rows with Labels and Text Fields
        GridPane center = new GridPane();
        center.getStyleClass().add("gridpane");
        center.add(inHouseBtn, 1, 0);
        center.add(outSourcedBtn, 2, 0);
        center.add(idLabel, 1, 1);
        center.add(idField, 2, 1);
        center.add(nameLabel, 1, 2);
        center.add(nameField, 2, 2);
        center.add(invLabel, 1, 3);
        center.add(invField, 2, 3);
        center.add(priceLabel, 1, 4);
        center.add(priceField, 2, 4);
        center.add(minLabel, 1, 5);
        center.add(minField, 2, 5);
        center.add(maxLabel, 1, 6);
        center.add(maxField, 2, 6);
        center.add(machineIDLabel, 1, 7);
        center.add(machineIDField, 2, 7);
        center.add(companyNameLabel, 1, 8);
        center.add(companyNameField, 2, 8);
        center.setVgap(10);
        
        //Create bottom toolbar with Save and Cancel buttons and their
        //respective action handlers.
        ToolBar bottom = new ToolBar();
        bottom.getStyleClass().add("toolbar");
        Button saveBtn = new Button ("Save");
        saveBtn.setOnAction(e->{
            if(inHouseBtn.isSelected()){
                try{
                    String name = nameField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int inStock = Integer.parseInt(invField.getText());
                    int min = Integer.parseInt(minField.getText());
                    int max = Integer.parseInt(maxField.getText());
                    int machineID = Integer.parseInt(machineIDField.getText());
                    Inhouse newPart = new Inhouse(name, price, inStock, min, max, machineID);
                    inventory.addPart(newPart);
                    partsList.setAll(inventory.getAllParts());
                    scene.setRoot(mainPane);
                }
                catch(NumberFormatException err){
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setHeaderText("Please fill in every field.");
                    alert.showAndWait();
                }
            }
            else if(outSourcedBtn.isSelected()){
                try{
                    String name = nameField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int inStock = Integer.parseInt(invField.getText());
                    int min = Integer.parseInt(minField.getText());
                    int max = Integer.parseInt(maxField.getText());
                    String compName = companyNameField.getText();
                    Outsourced newPart = new Outsourced(name, price, inStock, min, max, compName);
                    inventory.addPart(newPart);
                    partsList.setAll(inventory.getAllParts());
                    scene.setRoot(mainPane);
                }
                catch(NumberFormatException err){
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setHeaderText("Please fill in every field.");
                    alert.showAndWait();
                }
            }
        });
        Button cancelBtn = new Button ("Cancel");
        cancelBtn.setOnAction(e->{
           scene.setRoot(mainPane); 
        });
        bottom.getItems().addAll(saveBtn, cancelBtn);
        
        //Create BorderPane that acts as the main screen for this menu.
        BorderPane addPane = new BorderPane();
        addPane.setTop(top);
        addPane.setCenter(center);
        addPane.setBottom(bottom);
        scene.setRoot(addPane);
    }
    
    
    public void openModifyPartMenu(Part toModify){
        
        //Create all Labels for the add part menu.
        Label header = new Label("Modify Part");
        header.getStyleClass().add("header");
        Label idLabel = new Label("ID:");
        Label nameLabel = new Label("Name:");
        Label invLabel = new Label("In Stock:");
        Label priceLabel = new Label("Price:");
        Label maxLabel = new Label("Max:");
        Label minLabel = new Label("Min:");
        Label machineIDLabel = new Label ("Machine ID");
        Label companyNameLabel = new Label("Company Name:");
        companyNameLabel.setDisable(true);
        
        //Text fields set up the same as the add part menu
        TextField idField = new TextField();
        idField.setText(Integer.toString(toModify.getPartID()));
        idField.setDisable(true);
        TextField nameField = new TextField();
        nameField.setText(toModify.getName());
        TextField invField = new TextField();
        invField.setText(Integer.toString(toModify.getInStock()));
        addIntListener(invField);
        TextField priceField = new TextField();
        priceField.setText(Double.toString(toModify.getPrice()));
        addDoubleListener(priceField);
        TextField maxField = new TextField();
        maxField.setText(Integer.toString(toModify.getMax()));
        maxField.setMaxWidth(60);
        addIntListener(maxField);
        TextField minField = new TextField();
        minField.setText(Integer.toString(toModify.getMin()));
        minField.setMaxWidth(60);
        addIntListener(minField);
        TextField machineIDField = new TextField();
        addIntListener(machineIDField);
        TextField companyNameField = new TextField();
        companyNameField.setPromptText("Company Name");
        companyNameField.setDisable(true);
        
        //RadioButtons created to ensure that irrelevant fields are disabled
        RadioButton inHouseBtn = new RadioButton("In-house");
        RadioButton outSourcedBtn = new RadioButton("Outsourced");
        ToggleGroup btnGroup = new ToggleGroup();
        inHouseBtn.setToggleGroup(btnGroup);
        outSourcedBtn.setToggleGroup(btnGroup);
        btnGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> obs, Toggle oldT, Toggle newT) -> {
            if(newT == inHouseBtn){
                machineIDLabel.setDisable(false);
                machineIDField.setDisable(false);
                companyNameLabel.setDisable(true);
                companyNameField.setDisable(true);
                companyNameField.clear();
            }
            else if(newT == outSourcedBtn){
                companyNameLabel.setDisable(false);
                companyNameField.setDisable(false);
                machineIDLabel.setDisable(true);
                machineIDField.setDisable(true);
                machineIDField.clear();
            }
        });
        
        //Checks the selected Part to see if it's Inhouse or Outsourced, and
        //selects the proper radio button choice as needed.
        if(toModify instanceof Inhouse){
            inHouseBtn.setSelected(true);
            machineIDField.setText(Integer.toString(((Inhouse) toModify).getMachineID()));
        }
        else if(toModify instanceof Outsourced){
            outSourcedBtn.setSelected(true);
            companyNameField.setText(((Outsourced) toModify).getCompanyName());
        }
        
        
        //Set up toolbar looking the same as add menu and the main menu
        ToolBar top = new ToolBar();
        top.getItems().addAll(header, new Separator());
        
        //Create GridPane that acts as the main screen for this menu.
        GridPane center = new GridPane();
        center.getStyleClass().add("gridpane");
        center.add(inHouseBtn, 1, 0);
        center.add(outSourcedBtn, 2, 0);
        center.add(idLabel, 1, 1);
        center.add(idField, 2, 1);
        center.add(nameLabel, 1, 2);
        center.add(nameField, 2, 2);
        center.add(invLabel, 1, 3);
        center.add(invField, 2, 3);
        center.add(priceLabel, 1, 4);
        center.add(priceField, 2, 4);
        center.add(minLabel, 1, 5);
        center.add(minField, 2, 5);
        center.add(maxLabel, 1, 6);
        center.add(maxField, 2, 6);
        center.add(machineIDLabel, 1, 7);
        center.add(machineIDField, 2, 7);
        center.add(companyNameLabel, 1, 8);
        center.add(companyNameField, 2, 8);
        center.setVgap(10);
        
        //Creates bottom toolbar with save and cancel buttons and their
        //respective action handlers.
        ToolBar bottom = new ToolBar();
        bottom.getStyleClass().add("toolbar");
        Button saveBtn = new Button ("Save");
        saveBtn.setOnAction(e->{
            if(inHouseBtn.isSelected()){
                try{
                    int partID = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int inStock = Integer.parseInt(invField.getText());
                    int min = Integer.parseInt(minField.getText());
                    int max = Integer.parseInt(maxField.getText());
                    int machineID = Integer.parseInt(machineIDField.getText());
                    Inhouse newPart = new Inhouse(partID, name, price, inStock, min, max, machineID);
                    inventory.updatePart(partID, newPart);
                    partsList.setAll(inventory.getAllParts());
                    scene.setRoot(mainPane);
                }
                catch(NumberFormatException err){
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setHeaderText("Please fill in every field.");
                    alert.showAndWait();
                }
            }
            else if(outSourcedBtn.isSelected()){
                try{
                    int partID = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int inStock = Integer.parseInt(invField.getText());
                    int min = Integer.parseInt(minField.getText());
                    int max = Integer.parseInt(maxField.getText());
                    String compName = companyNameField.getText();
                    Outsourced newPart = new Outsourced(partID, name, price, inStock, min, max, compName);
                    inventory.updatePart(partID, newPart);
                    partsList.setAll(inventory.getAllParts());
                    scene.setRoot(mainPane);
                }
                catch(NumberFormatException err){
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setHeaderText("Please fill in every field.");
                    alert.showAndWait();
                }
            }
        });
        Button cancelBtn = new Button ("Cancel");
        cancelBtn.setOnAction(e->{
           scene.setRoot(mainPane); 
        });
        bottom.getItems().addAll(saveBtn, cancelBtn);
        
        BorderPane modifyPane = new BorderPane();
        modifyPane.setTop(top);
        modifyPane.setCenter(center);
        modifyPane.setBottom(bottom);
        scene.setRoot(modifyPane);
    }
    
    public void searchParts(){
        int lookupID;
        try{
            lookupID = Integer.parseInt(searchPartsField.getText());
            ArrayList<Part> searchList = new ArrayList<>();
            searchList.add(inventory.lookupPart(lookupID));
            partsList.setAll(searchList);
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(AlertType.WARNING);
               alert.setHeaderText("Please enter a part ID");
               alert.showAndWait();
        }
        
    }
    
    
    /*
    Method added to ignore any non-digit characters added to a text field.
    */
    public void addIntListener(TextField field){
        field.textProperty().addListener((ObservableValue<? extends String> obs, String oldString, String newString) ->{
        if(!newString.matches("\\d{0,9}")){
            field.setText(oldString);
        }
    });
    }
    
    /*
    Method added to ignore any non digit characters added to a text field, as
    well as allow 1 decimal point with a maximum of two digits after the 
    decimal.
    */
    public void addDoubleListener(TextField field){
        field.textProperty().addListener((ObservableValue<? extends String> obs, String oldString, String newString) ->{
        if(!newString.matches("\\d{0,9}([\\.]\\d{0,2})?")){
            field.setText(oldString);
        }
    });
    }
    
    /*
    Resets the TableView if the search field is empty
    */
    public void addSearchResetListener(TextField field){
        field.textProperty().addListener((ObservableValue<? extends String> obs, String oldString, String newString) ->{
        if(newString.matches("")){
            partsList.setAll(inventory.getAllParts());
        }
    });
    }
}
