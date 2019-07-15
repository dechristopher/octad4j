package net.octad.octad4j;

/**
 * Class defining one team, its color, and its pieces
 */
public class Team {
    private Color color;
    private int[] bitboards;

    public Team(Color color) {
        this.color = color;
        this.bitboards = defaultBoards(color);
    }

    /**
     * Return the color of this team
     * @return Color: this team's color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Return all 9 bitboards representing the position of this team
     * @return int[]: bitboards
     */
    public int[] getBitboards() {
        return this.bitboards;
    }

    /**
     * Get the team's corresponding bitboard
     * @param board - board type
     * @return short: bitboard
     */
    public int get(BitBoard board) {
        return bitboards[board.ordinal()];
    }

    private static int[] defaultBoards(Color color) {
        if (color.equals(Color.WHITE)) {
            // Return White default bitboards
            return new int[]{
                    2, 117, 53,
                    1, 576, 576,
                    12, 224, 3264
            };
        } else {
            // Return Black default bitboards
            return new int[]{
                    16384, 44544, 44032,
                    32768, 576, 576,
                    12288, 1792, 816
            };
        }
    }
}
