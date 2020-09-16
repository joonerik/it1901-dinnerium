package dinnerium.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import dinnerium.core.Ingredient;
import dinnerium.core.Quantity;
import dinnerium.json.HandlePersistency;

public class AppController {

    @FXML
    void initialize() throws Exception {
        Quantity quantity = new Quantity(3.0, "stk");
        Ingredient ingredient = new Ingredient(quantity, "Egg");
        HandlePersistency.writeJsonToFile(ingredient);

        Ingredient x = HandlePersistency.loadDataFromFile();
    }


    TextField nameInput;
    @FXML
    TextField quantityInput;
    @FXML
    ComboBox<String> unitComboBox;
    @FXML
    Button addButton;
    @FXML
    TableView<String> ingredientTableView;
    @FXML
    TableColumn<String, String> quantityColumn;
    @FXML
    TableColumn<String, String> itemColumn;


    @FXML
    private void addIngredient() {

    }
}
