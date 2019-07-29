package net.octad.octad4j;

import static net.octad.octad4j.BitBoard.*;
import static net.octad.octad4j.Color.WHITE;

/**
 * Class defining one team, its color, and its pieces
 */
public class Team {
    private Color color;
    private char[] bitboards;

    public Team(Color color) {
        this.color = color;
        this.bitboards = defaultBoards(color);
    }

    /**
     * Return the color of this team
     *
     * @return Color: this team's color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Return all 9 bitboards representing the position of this team
     *
     * @return char[]: bitboards
     */
    public char[] getBitboards() {
        return this.bitboards;
    }

    /**
     * Set
     *
     * @param boards =
     */
    public void setBitboards(char[] boards) {
        this.bitboards = boards;
    }

    /**
     * Return the three bitboards representing this team's piece positions
     *
     * @return char[]: piece position boards
     */
    public char[] getPositionBitboards() {
        return new char[]{this.bitboards[0], this.bitboards[3], this.bitboards[6]};
    }

    /**
     * Get the stacked bitboard for all piece positions
     *
     * @return char: piece location bitboard
     */
    public char getStackedPositionBitBoard() {
        return (char) (get(KING_POSITION) | get(KNIGHT_POSITION) | get(PAWN_POSITION));
    }

    /**
     * Return the three bitboards representing this team's piece attack squares
     *
     * @return char[]: piece attack boards
     */
    public char[] getAttackBitboards() {
        return new char[]{this.bitboards[1], this.bitboards[4], this.bitboards[7]};
    }

    /**
     * Return the three bitboards representing this team's piece move squares
     *
     * @return char[]: piece move boards
     */
    public char[] getMoveBitboards() {
        return new char[]{this.bitboards[2], this.bitboards[5], this.bitboards[8]};
    }

    /**
     * Get the team's corresponding bitboard
     *
     * @param board - board type
     * @return char: bitboard
     */
    public char get(BitBoard board) {
        return bitboards[board.ordinal()];
    }

    /**
     * Returns the position of the king as a bitboard
     *
     * @return char: king position bitboard
     */
    public char getKingPositionBitBoard() {
        return this.get(KING_POSITION);
    }

    /**
     * Returns the king attack squares bitboard
     *
     * @return char: king attack bitboard
     */
    public char getKingAttackBitBoard() {
        return this.get(KING_ATTACK);
    }

    /**
     * Returns the king move squares bitboard
     *
     * @return char: king move bitboard
     */
    public char getKingMoveBitBoard() {
        return this.get(KING_MOVE);
    }

    private static char[] defaultBoards(Color color) {
        if (color.equals(WHITE)) {
            // Return White default bitboards
            return new char[]{
                    2, 117, 53,
                    1, 576, 576,
                    12, 224, 3264
            };
        } else {
            // Return Black default bitboards
            return new char[]{
                    16384, 44544, 44032,
                    32768, 576, 576,
                    12288, 1792, 816
            };
        }
    }

    public static void main(String... args) {
        Team t = new Team(Color.WHITE);
        char[] bitboards = t.getBitboards();

        char boardChar = 576;

        BitBoard.print(boardChar);

        for (char board : bitboards) {
            BitBoard.print(board);
        }
    }
}
