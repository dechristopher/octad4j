package net.octad.octad4j;

import java.util.Arrays;

import static net.octad.octad4j.BitBoard.*;

/**
 * Class representing a game of Octad
 */
public class Octad {

    public static final String startingOFEN = "ppkn/4/4/NKPP";

    private Team WHITE, BLACK;

    private char activeColor = 'w';
    private String castling = "NCFncf";
    private String enPassant = "-";
    private short halfMoves = 0;
    private short fullMoves = 1;

    /**
     * Default Octad game position
     */
    public Octad() {
        this.WHITE = new Team(Color.WHITE);
        this.BLACK = new Team(Color.BLACK);
    }

    public Octad(String ofen) {
        this();
        String[] ofenParts = ofen.split(" ");
        this.activeColor = ofenParts[1].charAt(0);
        this.castling = ofenParts[2];
        this.enPassant = ofenParts[3];
        this.halfMoves = Short.parseShort(ofenParts[4]);
        this.fullMoves = Short.parseShort(ofenParts[5]);
        char[] boards = Conversions.genBoards(ofenParts[0]);
        WHITE.setBitboards(Arrays.copyOfRange(boards, 0, 2));
        BLACK.setBitboards(Arrays.copyOfRange(boards, 3, 5));
        //TODO - regenerate movement and attack bitboards
        //TODO - verify legal board state
    }

    /**
     * Returns the white team
     *
     * @return Team: white
     */
    public Team getWhite() {
        return this.WHITE;
    }

    /**
     * Returns the black team
     *
     * @return Team: black
     */
    public Team getBlack() {
        return this.BLACK;
    }

    /**
     * Returns the OFEN formatted position string for the current position
     *
     * @return String: OFEN formatted position
     */
    public String getOFENLayout() {
        return Conversions.genOFENLayout(this);
    }

    /**
     * Gets the color of the active player to make a move
     *
     * @return char: 'w' or 'b'
     */
    public char getActiveColor() {
        return this.activeColor;
    }

    /**
     * Gets the castling ability for both sides represented in OFEN notation
     *
     * @return String: castling ability
     */
    public String getCastling() {
        return this.castling;
    }

    /**
     * Returns true if white can castle with their knight
     *
     * @return boolean: white can castle with knight
     */
    public boolean canWhiteCastleKnight() {
        return this.getCastling().contains("N");
    }

    /**
     * Returns true if black can castle with their knight
     *
     * @return boolean: black can castle with knight
     */
    public boolean canBlackCastleKnight() {
        return this.getCastling().contains("n");
    }

    /**
     * Returns true if white can castle with their close pawn
     *
     * @return boolean: white can castle with their close pawn
     */
    public boolean canWhiteCastleClosePawn() {
        return this.getCastling().contains("C");
    }

    /**
     * Returns true if black can castle with their close pawn
     *
     * @return boolean: black can castle with their close pawn
     */
    public boolean canBlackCastleClosePawn() {
        return this.getCastling().contains("c");
    }

    /**
     * Returns true if white can castle with their far pawn
     *
     * @return boolean: white can castle with their far pawn
     */
    public boolean canWhiteCastleFarPawn() {
        return this.getCastling().contains("F");
    }

    /**
     * Returns true if black can castle with their far pawn
     *
     * @return boolean: black can castle with their far pawn
     */
    public boolean canBlackCastleFarPawn() {
        return this.getCastling().contains("f");
    }

    /**
     * Returns the active en passant square after a pawn move or "-" if none
     *
     * @return String: en passant square
     */
    public String getEnPassant() {
        return this.enPassant;
    }

    /**
     * Returns the number of half moves to have occurred since the last capture or pawn move
     *
     * @return short: half move number
     */
    public short getHalfMoves() {
        return this.halfMoves;
    }

    /**
     * Returns the current turn number (the number of full moves)
     *
     * @return short: full move number
     */
    public short getFullMoves() {
        return this.fullMoves;
    }

    /**
     * Checks to see if a color is currently in check
     *
     * @param color - color to check
     * @return boolean: is color in check
     */
    public boolean isInCheck(Color color) {
        Team team = teamFromColor(color);
        Team attackingTeam = (team.equals(this.WHITE) ? this.BLACK : this.WHITE);
        char kingPos = team.get(KING_POSITION);

        // only pawns and knights can put a king in check
        char[] attackingBoards = new char[]{
                attackingTeam.get(PAWN_ATTACK),
                attackingTeam.get(KNIGHT_ATTACK)
        };

        // cycle through attacking boards parsing check status
        for (char board : attackingBoards) {
            if (isAttacked(kingPos, board)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Performs a bitwise AND to check if a piece is being threatened by an attacking piece
     *
     * @param piecePosition - piece position bitboard
     * @param pieceAttack   - piece attack bitboard
     * @return boolean: is piece currently being threatened
     */
    private static boolean isAttacked(char piecePosition, char pieceAttack) {
        return (piecePosition & pieceAttack) == 1;
    }

    /**
     * Returns the name of the square the king of a given color is on
     *
     * @param color - color of king to get position of
     * @return String: position of king
     */
    public String kingPosition(Color color) {
        return kingPosition(teamFromColor(color).get(KING_POSITION));
    }

    /**
     * Gets the position of the king based on the king position bitboard
     *
     * @param kingPosition - king position bitboard
     * @return String: algebraic notation of king position
     */
    private static String kingPosition(char kingPosition) {
        /*
            iterates through all possible king positions using
            bit shifting to find a bit value corresponding with
            the position of the king on the board
         */
        for (short i = 0; i < 16; i++) {
            if (((kingPosition >> i) & 1) == 1) {
                return BitBoard.SQUARES[i];
            }
        }
        // impossible unless a game is imported from an OFEN without a king
        throw new IllegalStateException("No king on board!");
    }

    /**
     * Returns the team of the given color
     *
     * @param color -  color to get team of
     * @return Team: team of given color
     */
    public Team teamFromColor(Color color) {
        return (color.equals(Color.WHITE) ? this.WHITE : this.BLACK);
    }

    /**
     * Check to see if the current state of the board is valid
     * <p>
     * - two pieces do not occupy the same square
     * - pieces are not in impossible positions (pawns on knight starting square)
     *
     * @return boolean: board in valid position
     * <p>
     * TODO
     */
    public boolean validState() {
        return true;
    }

    /**
     * Get the bitboard for all piece positions
     *
     * @return char: piece location bitboard
     */
    public char getStackedPositionBitBoard() {
        return (char) (WHITE.get(KING_POSITION) | WHITE.get(KNIGHT_POSITION) | WHITE.get(PAWN_POSITION)
                | BLACK.get(KING_POSITION) | BLACK.get(KNIGHT_POSITION) | BLACK.get(PAWN_POSITION));
    }

    /**
     * Returns the OFEN representation of the current game state
     * <p>
     * Read doc/OFEN.md for more information
     *
     * @return String: OFEN string
     */
    public String getOFEN() {
        return String.format("%s %s %s %s %d %d",
                this.getOFENLayout(),
                this.getActiveColor(),
                this.getCastling(),
                this.getEnPassant(),
                this.getHalfMoves(),
                this.getFullMoves()
        );
    }

    /**
     * Prints the position as a string in standard notation shaped as a board
     */
    public void printPosition() {
        int div = 1;
        for (char c : Conversions.bitboardsToRaw(WHITE.getPositionBitboards(), BLACK.getPositionBitboards())) {
            System.out.print(c + " ");
            if (div % 4 == 0) {
                System.out.println();
            }
            div++;
        }
    }

    public static void main(String... args) {
        Octad o = new Octad();

        System.out.println(o.getOFEN() + "\n");

        o.printPosition();

        System.out.println("\n" + Conversions.genOFENLayout(o));

        String ofen = "ppk1/1n2/2P1/NK1P";

        System.out.println("\n" + Conversions.rawFromOFEN(ofen));
        System.out.println();
    }
}
