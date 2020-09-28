package dinnerium.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import dinnerium.core.Ingredient;
import java.io.IOException;

public class IngredientDeserializer extends JsonDeserializer<Ingredient> {

    @Override
    public Ingredient deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }

    // converts the Ingredient from string in json file to an object
    // checks if the nodes are of correct type
    // finally we have converted the strings from json into actual objects
    /**
     * Deserializer for Ingredient
     *
     * @param jsonNode
     *
     */
    public Ingredient deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Ingredient ingredient = new Ingredient();

            JsonNode quantityNode = objectNode.get("quantity");
            QuantityDeserializer qd = new QuantityDeserializer();
            if (quantityNode instanceof ObjectNode) {
                ingredient.setQuantity(qd.deserialize(quantityNode));
            }

            JsonNode nameNode = objectNode.get("name");
            if (nameNode instanceof TextNode) {
                ingredient.setName(nameNode.asText());
            }
            return ingredient;
        }
        return null;
    }
}