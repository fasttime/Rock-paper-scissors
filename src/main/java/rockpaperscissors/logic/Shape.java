package rockpaperscissors.logic;

/**
 * A shape in the game rock-paper-scissors.
 */
public enum Shape {

    /**
     * A rock, a.k.a. stone. This shape defeats scissors and is defeated by paper.
     */
    ROCK,

    /**
     * Paper. This shape defeats a rock and is defeated by scissors.
     */
    PAPER,

    /**
     * Scissors. This shape defeats paper and is defeated by a rock.
     */
    SCISSORS;

    static {
        ROCK.defeatedBy = PAPER;
        PAPER.defeatedBy = SCISSORS;
        SCISSORS.defeatedBy = ROCK;
    }

    private Shape defeatedBy;

    /**
     * Retrieves the shape that defeats this shape.
     *
     * @return The shape that defeats this shape.
     */
    public Shape getDefeatedBy() {
        return defeatedBy;
    }
}
