package dinnerium.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import dinnerium.core.User;
import java.io.IOException;

public class UserSerializer extends JsonSerializer<User> {

    // converts the IngredientContainer object into a string in json format
    @Override
    public void serialize(User user,
                          JsonGenerator jsonGen,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGen.writeStartObject();
        jsonGen.writeObjectField("ingredientContainer", user.getIngredientContainer());
        jsonGen.writeObjectField("recipeContainer", user.getRecipeContainer());
        jsonGen.writeStringField("username", user.getUsername());
        jsonGen.writeEndObject();
    }
}
