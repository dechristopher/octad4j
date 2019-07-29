package net.octad.octad4j;

import java.util.Arrays;

/**
 * Data conversion utilities
 */
class Conversions {

    private static final String ofenChars = "pPnNbBrRqQkK1234/";

    /**
     * Get the raw position string for generation of the actual OFEN and for printing
     *
     * @return String: raw position
     */
    static char[] bitboardsToRaw(char[] whiteBoards, char[] blackBoards) {
        StringBuilder position = new StringBuilder();

        for (short i = 16; i > 0; i -= 4) {
            for (short j = 4; j > 0; j--) {
                if (((whiteBoards[0] >> ((i - j))) & 1) == 1) {
                    position.append("K");
                } else if (((whiteBoards[1] >> ((i - j))) & 1) == 1) {
                    position.append("N");
                } else if (((whiteBoards[2] >> ((i - j))) & 1) == 1) {
                    position.append("P");
                } else if (((blackBoards[0] >> ((i - j))) & 1) == 1) {
                    position.append("k");
                } else if (((blackBoards[1] >> ((i - j))) & 1) == 1) {
                    position.append("n");
                } else if (((blackBoards[2] >> ((i - j))) & 1) == 1) {
                    position.append("p");
                } else {
                    position.append(".");
                }
            }
        }

        return position.toString().toCharArray();
    }

    /**
     * Convert raw position string to an OFEN formatted position
     * <p>
     * generate OFEN position string
     * from: ppkn........NKPP
     * to: ppkn/4/4/NKPP
     * <p>
     * after 1. c2
     * from: ppkn......P.NK.P
     * to: ppkn/4/2P1/NK1P
     *
     * @param game - Octad game object
     * @return String: OFEN formatted position
     */
    static String genOFENLayout(Octad game) {
        char[] rawPosition = bitboardsToRaw(
                game.getWhite().getPositionBitboards(), game.getBlack().getPositionBitboards()
        );
        StringBuilder position = new StringBuilder();

        int chunk = 4; // chunk size to divide
        for (int i = 0; i < rawPosition.length; i += chunk) {
            char[] temp = Arrays.copyOfRange(rawPosition, i, Math.min(rawPosition.length, i + chunk));

            short emptyCount = 0;
            for (char c : temp) {
                if (c == '.') {
                    emptyCount++;
                } else {
                    if (emptyCount > 0) {
                        position.append(emptyCount);
                        emptyCount = 0;
                    }
                    position.append(c);
                }
            }

            if (emptyCount != 0) {
                position.append(emptyCount);
            }

            if (i != (rawPosition.length - 4)) {
                position.append("/");
            }
        }

        return position.toString();
    }

    /**
     * Convert an ofen board representation to a raw position string
     *
     * @param ofen - ofen board string
     * @return String: raw position string
     * @throws IllegalArgumentException if invalid OFEN character
     */
    static String rawFromOFEN(String ofen) throws IllegalArgumentException {
        StringBuilder raw = new StringBuilder();
        for (char token : ofen.toCharArray()) {
            if (!ofenChars.contains(String.valueOf(token))) {
                throw new IllegalArgumentException("Invalid OFEN character: `" + token + "`");
            }
            if (token != '/') {
                try {
                    int count = Integer.parseInt(String.valueOf(token));
                    raw.append(".".repeat(count));
                } catch (NumberFormatException nfe) {
                    raw.append(token);
                }
            }
        }

        return raw.toString();
    }

    /**
     * Converts a raw position string to an array of position bitboards for each team
     * <p>
     * index 0-2: white piece positions (K, N, P)
     * index 305: black piece positions (k, n, p)
     *
     * @param rawPosition - raw position string
     * @return char[]: piece position bitboards
     */
    static char[] rawToBitboards(String rawPosition) {


        return null;
    }

    /**
     * Generates bitboards directly from an OFEN board representation
     *
     * @param ofenBoard - OFEN board string
     * @return char[] generated position bitboards
     */
    static char[] genBoards(String ofenBoard) {
        return rawToBitboards(rawFromOFEN(ofenBoard));
    }
}
