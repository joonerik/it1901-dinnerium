package dinnerium.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import dinnerium.core.RecipeInstructions;
import java.io.IOException;

class RecipeInstructionsSerializer extends JsonSerializer<RecipeInstructions> {


    // converts the Quantity object to a string in json format

    /**
     * Converts the recipeInstructions object to a string in json format.
     *
     * @param recipeInstructions the recipeInstructions to be serialized to a json string.
     * @param jsonGen the jsonGen used to generate the json objects.
     * @param serializerProvider the serializerProvider.
     * @throws IOException if it is not possible to format the recipeInstructions to a json string.
     */
    @Override
    public void serialize(
            RecipeInstructions recipeInstructions,
            JsonGenerator jsonGen,
            SerializerProvider serializerProvider) throws IOException {

        jsonGen.writeStartArray();
        for (String instruction : recipeInstructions) {
            jsonGen.writeString(instruction);
        }
        jsonGen.writeEndArray();
    }
}
