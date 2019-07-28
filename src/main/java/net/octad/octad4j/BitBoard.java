package net.octad.octad4j;

public enum BitBoard {
    KING_POSITION, KING_ATTACK, KING_MOVE,
    KNIGHT_POSITION, KNIGHT_ATTACK, KNIGHT_MOVE,
    PAWN_POSITION, PAWN_ATTACK, PAWN_MOVE;

    public static String[] boardNames = {
            "King Position", "King Attack", "King Move",
            "Knight Position", "Knight Attack", "Knight Move",
            "Pawn Position", "Pawn Attack", "Pawn Move",
    };

    public static char FILE_A = 4369;
    public static char FILE_H = 34952;
    public static char NOT_FILE_A = 61166;
    public static char NOT_FILE_H = 30583;

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
        for (int i = 16; i > 0; i-=4) {
            System.out.print((board >> (i-4) & 1) + " ");
            System.out.print((board >> (i-3) & 1) + " ");
            System.out.print((board >> (i-2) & 1) + " ");
            System.out.print((board >> (i-1) & 1) + "\n");
        }
    }
}
