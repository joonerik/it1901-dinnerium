package dinnerium.ui;

import dinnerium.core.Ingredient;
import dinnerium.core.IngredientContainer;
import dinnerium.core.Metadata;
import dinnerium.core.Quantity;
import dinnerium.core.Recipe;
import dinnerium.core.RecipeContainer;
import dinnerium.core.RecipeInstructions;
import dinnerium.core.User;
import dinnerium.json.HandlePersistency;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class AppController {

    // contains our current stock of ingredients/groceries
    private final ObservableList<Ingredient> newRecipeIngredients =
        FXCollections.observableArrayList();
    private final ObservableList<String> newRecipeInstructions =
        FXCollections.observableArrayList();
    private User user = null;

    @FXML
    TextField usernameInput;
    @FXML
    Button loginButton;
    @FXML
    TextField nameInput;
    @FXML
    TextField amountInput;
    @FXML
    Text errorOutput;
    @FXML
    ComboBox<String> unitComboBox;
    @FXML
    Button addButton;
    @FXML
    TableView<Ingredient> ingredientTableView;
    @FXML
    TableColumn<Ingredient, Quantity> quantityColumn;
    @FXML
    TableColumn<Ingredient, String> itemColumn;
    @FXML
    Text settingsText;
    @FXML
    Text fridgeText;
    @FXML
    Text yourRecipesText;
    @FXML
    ScrollPane recipesScrollPane;
    @FXML
    Pane fridgePane;
    @FXML
    Pane settingsPane;
    @FXML
    Pane recipesPane;
    @FXML
    Pane msgPane;
    @FXML
    ListView<Ingredient> recipesListView;
    @FXML
    TextField newRecipeNameIngredientInput;
    @FXML
    TextField newRecipeAmountInput;
    @FXML
    TextField newRecipeRecipeName;
    @FXML
    TextArea newRecipeRecipeDescription;
    @FXML
    TextField newRecipeMinutes;
    @FXML
    ComboBox<String> newRecipeUnitComboBox;
    @FXML
    TextArea instructionTextArea;
    @FXML
    ListView<String> instructionsListView;
    @FXML
    TextField newRecipePortions;
    @FXML
    Text recipesSubMenuText;
    @FXML
    Text newRecipeSubMenuText;
    @FXML
    Pane newRecipePane;
    @FXML
    AnchorPane recipesAnchorPane;
    @FXML
    Pane navigationBarPane;


    @FXML
    void initialize() {
        // sets up our tableview with correct rows and columns
        recipesListView.setItems(newRecipeIngredients);
        instructionsListView.setItems(newRecipeInstructions);
        unitComboBox.getItems().setAll(Quantity.units);
        newRecipeUnitComboBox.getItems().setAll(Quantity.units);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void setup(String username) {

        try {
            String path = "../core/src/main/resources/dinnerium/storage/" + username + ".json";
            FileReader reader = new FileReader(Paths.get(path).toFile(), StandardCharsets.UTF_8);
            this.user = HandlePersistency.readUserFromReader(reader);
        } catch (IOException e) {
            try {
                this.user = new User(new IngredientContainer(), new RecipeContainer(), username);
            } catch (IllegalArgumentException iae) {
                FeedbackHandler.showMessage(msgPane, iae.getMessage(), FeedbackHandler.ERROR);
            }
        }
    }

    @FXML
    private void handleLogin() {
        setup(usernameInput.getText().toLowerCase());
        if (user != null) {
            updateTableView();
            showUserRecipes();
            usernameInput.setVisible(false);
            loginButton.setVisible(false);
            navigationBarPane.setVisible(true);
            changeScene("fridge");
        }
    }

    //Adds the ingredient to the Users ingredientContainer first and then updates the tableview.
    //Then saves the new User object to the file, if an error occurs it throws an IAE
    @FXML
    private void handleAddIngredient() {
        addIngredient(false);
        updateTableView();
        // writes our new ingredient to the users file
        try {
            Path path = Paths.get(
                "../core/src/main/resources/dinnerium/storage/" + user.getUsername() + ".json");
            HandlePersistency.writeUser(user,
                new FileWriter(path.toFile(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not write userdata to file");
        }
    }

    @FXML
    private void handleNewRecipeAddIngredient() {
        addIngredient(true);
        newRecipeNameIngredientInput.clear();
        newRecipeAmountInput.clear();
    }

    //Adds the new ingredient to either the newRecipeIngredients list (if newRecipe is true)
    //Or adds the new ingredient to the ingredientContainer otherwise.
    //where it gets the values from is decided by if newRecipe is true or not.
    //If an IAE is thrown due to an error in the input values it writes the error message caught
    //to the errorOutput textField.
    private void addIngredient(boolean newRecipe) {
        try {
            String amountText = (newRecipe ? newRecipeAmountInput : amountInput).getText();
            String unit = (newRecipe ? newRecipeUnitComboBox : unitComboBox)
                .getSelectionModel()
                .getSelectedItem();
            String name = (newRecipe ? newRecipeNameIngredientInput : nameInput).getText();
            Ingredient i = new Ingredient(new Quantity(Double.parseDouble(amountText), unit), name);

            if (newRecipe) {
                newRecipeIngredients.add(i);
            } else {
                this.user.getIngredientContainer().addItem(i);
            }
        } catch (IllegalArgumentException e) {
            FeedbackHandler.showMessage(msgPane, e.getMessage(), 'E');
        }

    }

    @FXML
    private void handleAddInstruction() {
        if (!instructionTextArea.getText().isEmpty()) {
            this.newRecipeInstructions.add(instructionTextArea.getText());
            instructionTextArea.clear();
        } else {
            FeedbackHandler.showMessage(msgPane, "Instruction empty", 'E');
        }
    }

    //Adds a new recipe to the users recipe container, and then updates the recipes Anchor pane
    // with the new recipe.
    @FXML
    private void handleAddRecipe() {
        double portions = 0.0;
        int minutes = 0;
        try {
            portions = Double.parseDouble(newRecipePortions.getText());
            minutes = Integer.parseInt(newRecipeMinutes.getText());
        } catch (NumberFormatException e) {
            FeedbackHandler.showMessage(msgPane, "Invalid number", FeedbackHandler.ERROR);
        }

        try {
            //Currently the image is not used due to delays in the FX application when
            //retrieving images from the web.
            Metadata md = new Metadata(user.getUsername(),
                portions,
                "http://folk.ntnu.no/anderobs/images/tikkaMasala.png",
                newRecipeRecipeName.getText(),
                newRecipeRecipeDescription.getText(),
                minutes);

            IngredientContainer ic =
                new IngredientContainer(new ArrayList<>(this.newRecipeIngredients));
            RecipeInstructions rc =
                new RecipeInstructions(new ArrayList<>(this.newRecipeInstructions));
            Recipe recipe = new Recipe(ic, rc, md);

            this.user.getRecipeContainer().addItem(recipe);
            updateRecipeAnchorPane(recipe);
            clearRecipeFields();
            try {
                Path path = Paths.get(
                    "../core/src/main/resources/dinnerium/storage/" + user.getUsername() + ".json");
                HandlePersistency.writeUser(user,
                    new FileWriter(path.toFile(), StandardCharsets.UTF_8));

            } catch (IOException e) {
                FeedbackHandler.showMessage(msgPane, e.getMessage(), FeedbackHandler.ERROR);
            }
        } catch (IllegalArgumentException e) {
            FeedbackHandler.showMessage(msgPane, e.getMessage(), FeedbackHandler.ERROR);
        }
    }

    private void clearRecipeFields() {
        newRecipeRecipeName.clear();
        newRecipeNameIngredientInput.clear();
        newRecipeAmountInput.clear();
        newRecipeMinutes.clear();
        newRecipePortions.clear();
        newRecipeRecipeDescription.clear();
        instructionTextArea.clear();
        newRecipeIngredients.clear();
        newRecipeInstructions.clear();
    }

    private void showUserRecipes() {
        for (Recipe recipe : user.getRecipeContainer()) {
            updateRecipeAnchorPane(recipe);
        }
    }

    //Updates the recipeAnchorPane with one the new recipe at the bottom
    //Extends the scroll-pane if the new recipe is put outside it's current height.
    private void updateRecipeAnchorPane(Recipe recipe) {
        Pane pane = new Pane();
        pane.setPrefWidth(522);
        pane.setPrefHeight(167);

        Text recipeName = new Text(recipe.getMetadata().getRecipeName());
        recipeName.getStyleClass().add("recipe-name");
        recipeName.setLayoutX(1);
        recipeName.setLayoutY(25);

        Pane childPane = new Pane();
        childPane.setPrefHeight(135);
        childPane.setPrefWidth(522);
        childPane.setLayoutY(30);
        childPane.getStyleClass().add("child-pane");

        //Commented out due to issues with delay in the application.
        /*Image image = new Image(recipe.getMetadata().getImage());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(105);
        imageView.setFitWidth(105);
        imageView.setLayoutX(10);
        imageView.setLayoutY(10);*/

        Text recipeInfo = new Text(recipe.getIngredientContainer().getContainerSize()
            + " ingredients required  | " + recipe.getMetadata().getMinutes() + " minutes");
        recipeInfo.setLayoutY(30);
        recipeInfo.setLayoutX(127);
        recipeInfo.getStyleClass().add("recipe-info");
        recipeInfo.setWrappingWidth(390);

        Text recipeDescription = new Text(recipe.getMetadata().getRecipeDescription());
        recipeDescription.setWrappingWidth(370);
        recipeDescription.getStyleClass().add("recipe-description");
        recipeDescription.setLayoutX(132);
        recipeDescription.setLayoutY(52);

        childPane.getChildren().addAll(/*imageView, */recipeInfo, recipeDescription);
        pane.getChildren().addAll(recipeName, childPane);
        pane.setLayoutX(54);
        pane.setCursor(Cursor.HAND);
        pane.setOnMouseClicked(click -> showRecipeInformation(recipe));
        recipesAnchorPane.getChildren().add(pane);
        pane.setLayoutY(13 + 180 * (recipesAnchorPane.getChildren().size() - 1));
        if (recipesAnchorPane.getChildren().size() > 3) {
            recipesAnchorPane
                .setPrefHeight(13 + 180 * (recipesAnchorPane.getChildren().size()));
        }
    }

    private void showRecipeInformation(Recipe recipe) {
        recipesAnchorPane.getChildren().clear();
        Pane recipeInfoPane = new Pane();
        recipeInfoPane.getStyleClass().add("recipes-info-pane");
        recipeInfoPane.setLayoutY(10);
        recipeInfoPane.setLayoutX(64);

        Text recipeName = new Text(recipe.getMetadata().getRecipeName());
        recipeName.getStyleClass().add("recipe-info-name");
        recipeName.setLayoutY(26);
        recipeName.setWrappingWidth(522);

        Text recipeDescription = new Text(recipe.getMetadata().getRecipeDescription());
        recipeDescription.getStyleClass().add("recipe-info-description");
        recipeDescription.setLayoutY(80);
        recipeDescription.setLayoutX(20);
        recipeDescription.setWrappingWidth(482);

        Text metadata = new Text("Author: " + recipe.getMetadata().getAuthor() + "\n"
            + "Portions: " + recipe.getMetadata().getPortion() + "\n"
            + "Time: " + recipe.getMetadata().getMinutes() + " minutes");
        metadata.getStyleClass().add("recipe-info-metadata");
        metadata.setLayoutY(15);
        metadata.setLayoutX(10);

        Text ingredientsHeader = new Text("Ingredients");
        ingredientsHeader.getStyleClass().add("textview-header");
        ingredientsHeader.setLayoutY(105);
        ingredientsHeader.setLayoutX(40);

        Iterator<Ingredient> ingredientsIt = recipe.getIngredientContainer().iterator();
        Text ingredients = new Text(ingredientsIt.hasNext() ? "1. " + ingredientsIt.next() : "");
        int i = 2;
        while (ingredientsIt.hasNext()) {
            ingredients.setText(ingredients.getText() + "\n" + i + ". " + ingredientsIt.next());
            i++;
        }
        ingredients.setWrappingWidth(200);
        ingredients.setLayoutY(122);
        ingredients.setLayoutX(20);
        ingredients.getStyleClass().add("list-style");

        Text instructionsHeader = new Text("Instructions");
        instructionsHeader.getStyleClass().add("textview-header");
        instructionsHeader.setLayoutY(105);
        instructionsHeader.setLayoutX(270);

        Iterator<String> instructionIt = recipe.getRecipeInstructions().iterator();
        Text instructions = new Text(instructionIt.hasNext() ? "1. " + instructionIt.next() : "");
        int j = 2;
        while (instructionIt.hasNext()) {
            instructions.setText(instructions.getText() + "\n" + j + ". " + instructionIt.next());
            j++;
        }
        instructions.getStyleClass().add("list-style");
        instructions.setWrappingWidth(200);
        instructions.setLayoutY(122);
        instructions.setLayoutX(250);

        Button hideRecipeInformation = new Button();
        hideRecipeInformation.getStyleClass().add("button-style");
        hideRecipeInformation.setId("hideRecipeInformationButton");
        hideRecipeInformation.setText("Hide recipe");
        hideRecipeInformation.setLayoutY(20);
        hideRecipeInformation.setLayoutX(402);
        hideRecipeInformation.setPrefWidth(100);
        hideRecipeInformation.setCursor(Cursor.HAND);
        hideRecipeInformation.setOnAction(click -> hideRecipeInformation());

        recipeInfoPane.getChildren()
            .addAll(recipeName, recipeDescription, hideRecipeInformation, ingredients, instructions,
                metadata, ingredientsHeader, instructionsHeader);
        recipesAnchorPane.getChildren().add(recipeInfoPane);
    }

    private void hideRecipeInformation() {
        recipesAnchorPane.getChildren().clear();
        showUserRecipes();
    }

    @FXML
    private void handleChangeToFridge() {
        changeScene("fridge");
    }

    @FXML
    private void handleChangeToYourRecipes() {
        newRecipeIngredients.clear();
        newRecipeInstructions.clear();
        changeScene("recipes");
    }

    @FXML
    private void handleChangeToSettings() {
        changeScene("settings");
    }

    //Changes between the scenes of the application, and sets the color of the header text
    private void changeScene(String newScene) {
        settingsText.setFill(Color.valueOf(newScene.equals("settings") ? "#f4c20d" : "#ebe8bf"));
        fridgeText.setFill(Color.valueOf(newScene.equals("fridge") ? "#f4c20d" : "#ebe8bf"));
        yourRecipesText.setFill(Color.valueOf(newScene.equals("recipes") ? "#f4c20d" : "#ebe8bf"));

        settingsPane.setVisible(newScene.equals("settings"));
        fridgePane.setVisible(newScene.equals("fridge"));
        recipesPane.setVisible(newScene.equals("recipes"));
    }

    @FXML
    private void handleChangeToRecipes() {
        hideRecipeInformation();
        changeSubScene("recipes");
    }

    @FXML
    private void handleChangeToNewRecipe() {
        changeSubScene("newRecipe");
    }

    //Changes between sub-scenes in the recipe scene
    private void changeSubScene(String newSubScene) {
        newRecipeSubMenuText.setFill(
            Color.valueOf(newSubScene.equals("newRecipe") ? "#f4c20d" : "#ebe8bf"));
        recipesSubMenuText.setFill(
            Color.valueOf(newSubScene.equals("recipes") ? "#f4c20d" : "#ebe8bf"));

        newRecipePane.setVisible(newSubScene.equals("newRecipe"));
        recipesScrollPane.setVisible(newSubScene.equals("recipes"));
    }

    // updates our tableView with an observable list
    private void updateTableView() {
        ObservableList<Ingredient> observableList =
            FXCollections.observableArrayList(user.getIngredientContainer().getContainer());
        ingredientTableView.setItems(observableList);
    }


    //Methods for testing the app controller.
    User getUser() {
        return this.user;
    }

    List<Ingredient> getNewRecipeIngredients() {
        return new ArrayList<>(newRecipeIngredients);
    }

    List<String> getNewRecipeInstructions() {
        return new ArrayList<>(newRecipeInstructions);
    }
}
