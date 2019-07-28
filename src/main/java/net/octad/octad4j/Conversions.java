package net.octad.octad4j;

import java.util.Arrays;

/**
 * Data conversion utilities
 */
class Conversions {

    /**
     * Get the raw position string for generation of the actual OFEN and for printing
     *
     * @return String: raw position
     */
    static char[] getRawPositionString(char[] whiteBoards, char[] blackBoards) {
        StringBuilder position = new StringBuilder("");

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
     *
     * generate OFEN position string
     * from: ppkn........NKPP
     * to: ppkn/4/4/NKPP
     *
     * after 1. c2
     * from: ppkn......P.NK.P
     * to: ppkn/4/2P1/NK1P
     *
     * @param game - Octad game object
     * @return String: OFEN formatted position
     */
    static String genOFENLayout(Octad game) {
        char[] rawPosition = getRawPositionString(
                game.WHITE.getPositionBitboards(), game.BLACK.getPositionBitboards()
        );
        StringBuilder position = new StringBuilder("");

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

            if(emptyCount != 0) {
                position.append(emptyCount);
            }

            if (i != (rawPosition.length - 4)) {
                position.append("/");
            }
        }

        return position.toString();
    }

    static String rawFromOFEN(String ofen) {
        //TODO - convert ofen string to raw position string
        return "";
    }

    static char[] rawToBitboards(String rawPosition) {
        //TODO - convert raw position string to bitboards
        return null;
    }

    /**
     * Generates bitboards directly from an OFEN board representation
     * @param ofenBoard - OFEN board string
     * @return char[] generated position bitboards
     */
    static char[] genBoards(String ofenBoard) {
        return rawToBitboards(rawFromOFEN(ofenBoard));
    }
}
