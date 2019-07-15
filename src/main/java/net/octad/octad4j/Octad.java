package net.octad.octad4j;

/**
 * Class representing a game of Octad
 */
public class Octad {

    private static final String[] SQUARES = {
            "a1", "b1", "c1", "d1",
            "a2", "b2", "c2", "d2",
            "a3", "b3", "c3", "d3",
            "a4", "b4", "c4", "d4"
    };

    // numeric values of each square on the board from a1 - d4
    private static final int[] SQUAREVALS = {
            1, 2, 4, 8,
            16, 32, 64, 128,
            256, 512, 1024, 2048,
            4096, 8192, 16384, 32768
    };

    private Team white, black;

    public Octad() {
        this.white = new Team(Color.WHITE);
        this.black = new Team(Color.BLACK);
    }

    /**
     * Checks to see if a color is currently in check
     *
     * @param color - color to check
     * @return boolean: is color in check
     */
    public boolean isInCheck(Color color) {
        Team team = teamFromColor(color);
        Team attackingTeam = (team.equals(this.white) ? this.black : this.white);
        int kingPos = team.get(BitBoard.KING_POSITION);

        // only pawns and knights can put a king in check
        int[] attackingBoards = new int[]{
                attackingTeam.get(BitBoard.PAWN_ATTACK),
                attackingTeam.get(BitBoard.KNIGHT_ATTACK)
        };

        // cycle through attacking boards parsing check status
        for (int board : attackingBoards) {
            if (isInCheck(kingPos, board)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Performs a bitwise AND to check if a king is being threatened by an attacking piece
     *
     * @param kingPosition - king position bitboard
     * @param pieceAttack  - piece attack bitboard
     * @return boolean: is king currently in check
     */
    private static boolean isInCheck(int kingPosition, int pieceAttack) {
        return (kingPosition & pieceAttack) != 0;
    }

    /**
     * Returns the square the king of a given color is on
     *
     * @param color - color of king to get position of
     * @return String: position of king
     */
    public String kingPosition(Color color) {
        return kingPosition(teamFromColor(color).get(BitBoard.KING_POSITION));
    }

    /**
     * Gets the position of the king based on the king position bitboard
     *
     * @param kingPosition - king position bitboard
     * @return String: algebraic notation of king position
     */
    private static String kingPosition(int kingPosition) {
        for (int i = 0; i < 16; i++) {
            if (((kingPosition >> i) & 1) == 1) {
                return SQUARES[i];
            }
        }
        throw new IllegalStateException("No king on board!");
    }

    /**
     * Returns the team of the given color
     *
     * @param color -  color to get team of
     * @return Team: team of given color
     */
    public Team teamFromColor(Color color) {
        return (color.equals(Color.WHITE) ? this.white : this.black);
    }
}
