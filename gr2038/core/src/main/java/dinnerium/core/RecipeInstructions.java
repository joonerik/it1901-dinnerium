package dinnerium.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Class for storing instructions for a recipe.
 */
public class RecipeInstructions implements Iterable<String> {

    private final Collection<String> instructions;

    public RecipeInstructions(Collection<String> instructions) {
        this.instructions = instructions;
    }

    /**
     * Sets an instruction.
     *
     * @param instruction description of a step
     *
     * @throws IllegalArgumentException if the instruction is empty
     */
    public void addInstruction(String instruction) {
        if (instruction != null && !instruction.isBlank()) {
            instructions.add(instruction);
        } else {
            throw new IllegalArgumentException("String is null");
        }
    }

    /**
     * Returns a copy of the collection containing the instructions.
     *
     * @return a copy of the recipe instructions.
     *
     */
    public Collection<String> getInstructions() {
        return new ArrayList<>(this.instructions);
    }

    @Override
    public Iterator<String> iterator() {
        return this.instructions.iterator();
    }
}
