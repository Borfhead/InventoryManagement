
package inventorymanagement;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private Inventory inventory;
    private HBox mainPane;
    
    private BorderPane partsPane;
    private ToolBar partsTopTools;
    private Button searchPartsBtn;
    private TextField searchPartsField;
    private ToolBar partsBottomTools;
    private Button addPartsBtn;
    private Button modifyPartsBtn;
    private Button deletePartsBtn;
    private TableView<Part> partsTable;
    private ObservableList<Part> partsList;
    
    private BorderPane productsPane;
    private ToolBar productsTopTools;
    private Button searchProductsBtn;
    private TextField searchProductsField;
    private ToolBar productsBottomTools;
    private Button addProductsBtn;
    private Button modifyProductsBtn;
    private Button deleteProductsBtn;
    private TableView<Product> productsTable;
    private ObservableList<Product> productList;
    
    @Override
    public void start(Stage primaryStage) {
        
        //Set up inventory and add a default test part
        inventory = new Inventory();
        populateInventory();
        
        
        //Initiate parts and product lists to populate the TableViews with
        partsList = FXCollections.observableArrayList(inventory.getAllParts());
        productList = FXCollections.observableArrayList(inventory.getProducts());
        
        //Set up top parts toolbar header, search button, and search field
        partsTopTools = new ToolBar();
        Label partsLabel = new Label("Parts");
        partsLabel.getStyleClass().add("header");
        searchPartsBtn = new Button("Search");
        searchPartsField = new TextField();
        searchPartsBtn.setOnAction(e -> {
            searchParts(searchPartsField, partsList);
        });
        addSearchResetListener(searchPartsField, partsList, inventory.getAllParts());
        addIntListener(searchPartsField);
        partsTopTools.getItems().addAll(partsLabel, new Separator(), searchPartsBtn, searchPartsField);
        partsTopTools.getStyleClass().add("toolbar");
        
        
        //Set up top products toolbar header, search button, and search field
        productsTopTools = new ToolBar();
        Label productsLabel = new Label("Products");
        productsLabel.getStyleClass().add("header");
        searchProductsBtn = new Button("Search");
        searchProductsField = new TextField();
        addIntListener(searchProductsField);
        addSearchResetListener(searchProductsField, productList, inventory.getProducts());
        searchProductsBtn.setOnAction(e -> {
            searchProducts();
        });
        productsTopTools.getItems().addAll(productsLabel, new Separator(), searchProductsBtn, searchProductsField);
        productsTopTools.getStyleClass().add("toolbar");
        
        
        //Set up and populate parts TableView.
        partsTable = new TableView<>();
        TableColumn partsCol = new TableColumn("Part ID");
        partsCol.setPrefWidth(150);
        partsCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        TableColumn partsNameCol = new TableColumn("Part Name");
        partsNameCol.setPrefWidth(150);
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn partsInvLvlCol = new TableColumn("Inventory Level");
        partsInvLvlCol.setPrefWidth(150);
        partsInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        TableColumn partsPriceCol = new TableColumn("Price/Cost Per Unit");
        partsPriceCol.setPrefWidth(150);
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.getColumns().addAll(partsCol, partsNameCol, partsInvLvlCol,
                partsPriceCol);
        partsTable.setEditable(false);
        partsTable.setItems(partsList);
        
        //Set up and populate products Tableview.
        productsTable = new TableView<>();
        TableColumn productsCol = new TableColumn("Product ID");
        productsCol.setPrefWidth(150);
        productsCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        TableColumn productsNameCol = new TableColumn("Product Name");
        productsNameCol.setPrefWidth(150);
        productsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn productsInvLvlCol = new TableColumn("Inventory Level");
        productsInvLvlCol.setPrefWidth(150);
        productsInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        TableColumn productsPriceCol = new TableColumn("Price Per Unit");
        productsPriceCol.setPrefWidth(150);
        productsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsTable.getColumns().addAll(productsCol, productsNameCol,
                productsInvLvlCol, productsPriceCol);
        productsTable.setEditable(false);
        productsTable.setItems(productList);
        
        
        //Create Parts bottom toolbar with Add, Modify, and Delete buttons
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
        
        
        //Create bottom Products toolbar with add, mofidy and delete buttons
        //and respective action handlers.
        productsBottomTools = new ToolBar();
        addProductsBtn = new Button("Add");
        addProductsBtn.setOnAction(e -> {
            openAddProductMenu();
        });
        modifyProductsBtn = new Button("Modify");
        modifyProductsBtn.setOnAction(e -> {
            
        });
        deleteProductsBtn = new Button("Delete");
        deleteProductsBtn.setOnAction(e -> {
            inventory.deleteProduct(productsTable.getSelectionModel().getSelectedItem());
            productList.setAll(inventory.getProducts());
        });
        productsBottomTools.getItems().addAll(addProductsBtn, modifyProductsBtn, deleteProductsBtn);
        productsBottomTools.getStyleClass().add("toolbar");
        
        
        //Set up partsPane BorderPane and populate top, center, and bottom nodes.
        partsPane = new BorderPane();
        partsPane.setTop(partsTopTools);
        partsPane.setCenter(partsTable);
        partsPane.setBottom(partsBottomTools);
        partsPane.prefWidthProperty().bind(primaryStage.widthProperty());
        partsPane.prefHeightProperty().bind(primaryStage.heightProperty());
        
        
        //Set up productsPane BorderPane and populate top, center, and bottom nodes.
        productsPane = new BorderPane();
        productsPane.setTop(productsTopTools);
        productsPane.setCenter(productsTable);
        productsPane.setBottom(productsBottomTools);
        productsPane.prefWidthProperty().bind(primaryStage.widthProperty());
        productsPane.prefHeightProperty().bind(primaryStage.heightProperty());
        
        
        //Set up and populate main HBox Pane that contains Part and Product
        //menus.
        mainPane = new HBox();
        mainPane.prefWidthProperty().bind(primaryStage.widthProperty());
        mainPane.prefHeightProperty().bind(primaryStage.heightProperty());
        mainPane.getChildren().addAll(partsPane, productsPane);
        mainPane.setSpacing(20);
        
        //Set up scene and primaryStage.
        scene = new Scene(mainPane);
        scene.getStylesheets().add("/inventorymanagement/style.css");
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.setWidth(1250);
        primaryStage.setHeight(600);
        primaryStage.centerOnScreen();
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
                    resetScene();
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
                    resetScene();
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
           resetScene();
        });
        bottom.getItems().addAll(saveBtn, cancelBtn);
        
        //Create BorderPane that acts as the main screen for this menu.
        BorderPane addPane = new BorderPane();
        addPane.setTop(top);
        addPane.setCenter(center);
        addPane.setBottom(bottom);
        scene.setRoot(addPane);
        scene.getWindow().setWidth(400);
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
                    resetScene();
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
                    resetScene();
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
           resetScene(); 
        });
        bottom.getItems().addAll(saveBtn, cancelBtn);
        
        BorderPane modifyPane = new BorderPane();
        modifyPane.setTop(top);
        modifyPane.setCenter(center);
        modifyPane.setBottom(bottom);
        scene.setRoot(modifyPane);
        scene.getWindow().setWidth(400);
    }
    
    
    public void openAddProductMenu(){
        Product newProd = new Product();
        
        Label header = new Label("Add Product");
        header.getStyleClass().add("header");
        Label idLabel = new Label("ID:");
        Label nameLabel = new Label("Name:");
        Label invLabel = new Label("In Stock:");
        Label priceLabel = new Label("Price:");
        Label maxLabel = new Label("Max:");
        Label minLabel = new Label("Min:");
        
        TextField idField = new TextField();
        idField.setPromptText("Auto Gen - Disabled");
        idField.setDisable(true);
        TextField nameField = new TextField();
        nameField.setPromptText("Product Name");
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
        
        ToolBar top = new ToolBar();
        top.getItems().addAll(header, new Separator());
        
        GridPane left = new GridPane();
        left.setPadding(new Insets(0, 0, 0, 40));
        left.getStyleClass().add("gridpane");
        left.add(idLabel, 1, 0);
        left.add(idField, 2, 0);
        left.add(nameLabel, 1, 1);
        left.add(nameField, 2, 1);
        left.add(invLabel, 1, 2);
        left.add(invField, 2, 2);
        left.add(priceLabel, 1, 3);
        left.add(priceField, 2, 3);
        left.add(minLabel, 1, 4);
        left.add(minField, 2, 4);
        left.add(maxLabel, 1, 5);
        left.add(maxField, 2, 5);
        left.setVgap(10);
        left.setHgap(10);
        
        Label availablePartsLabel = new Label("Available Parts");
        TableView<Part> availablePartsTable = new TableView<>();
        availablePartsTable.setPrefHeight(150);
        TableColumn partsCol = new TableColumn("Part ID");
        partsCol.setPrefWidth(150);
        partsCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        TableColumn partsNameCol = new TableColumn("Part Name");
        partsNameCol.setPrefWidth(150);
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn partsInvLvlCol = new TableColumn("Inventory Level");
        partsInvLvlCol.setPrefWidth(150);
        partsInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        TableColumn partsPriceCol = new TableColumn("Price/Cost Per Unit");
        partsPriceCol.setPrefWidth(150);
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        availablePartsTable.getColumns().addAll(partsCol, partsNameCol, partsInvLvlCol,
                partsPriceCol);
        availablePartsTable.setEditable(false);
        ObservableList<Part> availableParts = FXCollections.observableArrayList(inventory.getAllParts());
        availablePartsTable.setItems(availableParts);
        
        
        Label associatedPartsLabel = new Label("Associated Parts");
        TableView<Part> associatedPartsTable = new TableView<>();
        associatedPartsTable.setPrefHeight(150);
        TableColumn partsCol2 = new TableColumn("Part ID");
        partsCol2.setPrefWidth(150);
        partsCol2.setCellValueFactory(new PropertyValueFactory<>("partID"));
        TableColumn partsNameCol2 = new TableColumn("Part Name");
        partsNameCol2.setPrefWidth(150);
        partsNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn partsInvLvlCol2 = new TableColumn("Inventory Level");
        partsInvLvlCol2.setPrefWidth(150);
        partsInvLvlCol2.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        TableColumn partsPriceCol2 = new TableColumn("Price/Cost Per Unit");
        partsPriceCol2.setPrefWidth(150);
        partsPriceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartsTable.getColumns().addAll(partsCol2, partsNameCol2, partsInvLvlCol2,
                partsPriceCol2);
        associatedPartsTable.setEditable(false);
        ObservableList<Part> associatedParts = FXCollections.observableArrayList();
        associatedPartsTable.setItems(associatedParts);
        
        Button searchAvlPartsBtn = new Button ("Search");
        TextField searchAvlPartsField = new TextField();
        addIntListener(searchAvlPartsField);
        addSearchResetListener(searchAvlPartsField, availableParts, inventory.getAllParts());
        searchAvlPartsBtn.setOnAction(e -> {
            searchParts(searchAvlPartsField, availableParts);
        });
        
        Button addToPartsBtn = new Button("Add");
        addToPartsBtn.setOnAction(e -> {
            if(availablePartsTable.getSelectionModel().getSelectedItem() == null){
               Alert alert = new Alert(AlertType.WARNING);
               alert.setHeaderText("Please select a part to add");
               alert.showAndWait();
           }
           else{
               newProd.addAssociatedPart(availablePartsTable.getSelectionModel().getSelectedItem());
               associatedParts.setAll(newProd.getAssociatedParts());
           }
        });
        
        Button removeAssociatedPartBtn = new Button("Delete");
        removeAssociatedPartBtn.setOnAction(e -> {
            if(associatedPartsTable.getSelectionModel().getSelectedItem() == null){
               Alert alert = new Alert(AlertType.WARNING);
               alert.setHeaderText("Please select a part to remove");
               alert.showAndWait();
           }
           else{
               newProd.removeAssociatedPart(associatedPartsTable.getSelectionModel().getSelectedItem());
               associatedParts.setAll(newProd.getAssociatedParts());
           }
        });
        
        GridPane right = new GridPane();
        HBox box = new HBox();
        box.setSpacing(10);
        box.getChildren().addAll(availablePartsLabel, searchAvlPartsBtn, searchAvlPartsField);
        right.setVgap(10);
        right.add(box, 0, 0);
        right.add(availablePartsTable, 0, 1);
        right.add(addToPartsBtn, 0, 2);
        right.add(associatedPartsLabel, 0, 3);
        right.add(associatedPartsTable, 0, 4);
        right.add(removeAssociatedPartBtn, 0, 5);
        
        ToolBar bottom = new ToolBar();
        bottom.getStyleClass().add("toolbar");
        Button saveBtn = new Button ("Save");
        saveBtn.setOnAction(e->{
            if(newProd.getAssociatedParts().size() >= 2){
                try{
                    String name = nameField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int inStock = Integer.parseInt(invField.getText());
                    int min = Integer.parseInt(minField.getText());
                    int max = Integer.parseInt(maxField.getText());
                    Product prod = new Product(name, price, inStock, min, max);
                    inventory.addProduct(prod);
                    productList.setAll(inventory.getProducts());
                    resetScene();
                }
                catch(NumberFormatException err){
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setHeaderText("Please fill in every field.");
                    alert.showAndWait();
                }
            }
            else{
                Alert alert = new Alert(AlertType.WARNING);
                    alert.setHeaderText("Products must have at least two parts.");
                    alert.showAndWait();
            }
            
        });
        Button cancelBtn = new Button ("Cancel");
        cancelBtn.setOnAction(e->{
           resetScene();
        });
        bottom.getItems().addAll(saveBtn, cancelBtn);
        
        
        BorderPane addProductPane = new BorderPane();
        addProductPane.setTop(top);
        addProductPane.setLeft(left);
        addProductPane.setRight(right);
        addProductPane.setBottom(bottom);
        scene.getWindow().setWidth(1000);
        scene.setRoot(addProductPane);
    }
    
    
    public void searchParts(TextField searchField, ObservableList<Part> pList){
        int lookupID;
        try{
            lookupID = Integer.parseInt(searchField.getText());
            ArrayList<Part> searchList = new ArrayList<>();
            searchList.add(inventory.lookupPart(lookupID));
            pList.setAll(searchList);
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(AlertType.WARNING);
               alert.setHeaderText("Please enter a part ID");
               alert.showAndWait();
        }
        
    }
    
    
    public void searchProducts(){
        int lookupID;
        try{
            lookupID = Integer.parseInt(searchProductsField.getText());
            ArrayList<Product> searchList = new ArrayList<>();
            searchList.add(inventory.lookupProduct(lookupID));
            productList.setAll(searchList);
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(AlertType.WARNING);
               alert.setHeaderText("Please enter a Product ID");
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
    public void addSearchResetListener(TextField field, ObservableList toReset, ArrayList list){
        field.textProperty().addListener((ObservableValue<? extends String> obs, String oldString, String newString) ->{
        if(newString.matches("")){
            toReset.setAll(list);
        }
    });
    }
    
    public void resetScene(){
        scene.setRoot(mainPane);
        scene.getWindow().setWidth(1250);
        scene.getWindow().centerOnScreen();
    }
    
    public void populateInventory(){
        Part p1 = new Inhouse("Pedals", 50.00, 50, 25, 100, 65412);
        Part p2 = new Outsourced("Handlebars", 75.99, 10, 5, 15, "Trek Co.");
        Part p3 = new Inhouse("Seat", 25.99, 10, 1, 20, 13546);
        Part p4 = new Outsourced("Grips", 10.00, 2, 10, 20, "Cool Bike Co.");
        Part p5 = new Outsourced("Road Bars", 75.00, 10, 5, 15, "Trek Co.");
        
        inventory.addPart(p1);
        inventory.addPart(p2);
        inventory.addPart(p3);
        inventory.addPart(p4);
        inventory.addPart(p5);
        
        Product pr = new Product ("Mountain Bike", 500.00, 20, 10, 30);
        pr.addAssociatedPart(p1);
        pr.addAssociatedPart(p2);
        pr.addAssociatedPart(p3);
        pr.addAssociatedPart(p4);
        inventory.addProduct(pr);
    }
}
