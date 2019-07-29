package net.octad.octad4j;

public enum BitBoard {
    KING_POSITION, KING_ATTACK, KING_MOVE,
    KNIGHT_POSITION, KNIGHT_ATTACK, KNIGHT_MOVE,
    PAWN_POSITION, PAWN_ATTACK, PAWN_MOVE;

    public static final String[] boardNames = {
            "King Position", "King Attack", "King Move",
            "Knight Position", "Knight Attack", "Knight Move",
            "Pawn Position", "Pawn Attack", "Pawn Move",
    };

    public static final String[] SQUARES = {
            "a1", "b1", "c1", "d1",
            "a2", "b2", "c2", "d2",
            "a3", "b3", "c3", "d3",
            "a4", "b4", "c4", "d4"
    };

    // numeric values of each square on the board from a1 - d4
    public static final char[] SQUAREVALS = {
            1, 2, 4, 8,
            16, 32, 64, 128,
            256, 512, 1024, 2048,
            4096, 8192, 16384, 32768
    };

    public static final char FILE_A = 4369;
    public static final char FILE_H = 34952;
    public static final char NOT_FILE_A = 61166;
    public static final char NOT_FILE_H = 30583;

    public static String name(BitBoard board) {
        return boardNames[board.ordinal()];
    }

    // TODO - bitboard updates (position, attack, and movement)

    public static void print(char board) {
        printBoard(board);
    }

    public static void print(char board, BitBoard boardType) {
        System.out.println(name(boardType));
        printBoard(board);
    }

    private static void printBoard(char board) {
        for (int i = 16; i > 0; i -= 4) {
            System.out.print((board >> (i - 4) & 1) + " ");
            System.out.print((board >> (i - 3) & 1) + " ");
            System.out.print((board >> (i - 2) & 1) + " ");
            System.out.print((board >> (i - 1) & 1) + "\n");
        }
    }
}
