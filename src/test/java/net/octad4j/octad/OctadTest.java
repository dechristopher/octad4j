package net.octad4j.octad;

import static org.junit.jupiter.api.Assertions.*;

import net.octad.octad4j.Color;
import net.octad.octad4j.Octad;
import org.junit.jupiter.api.Test;

class OctadTest {

    @Test
    void testNeitherSideIsInCheckAtGameStart() {
        Octad o = new Octad();
        assertFalse(o.isInCheck(Color.WHITE));
        assertFalse(o.isInCheck(Color.BLACK));
    }

    @Test
    void testBothKingsAreOnProperSquares() {
        Octad o = new Octad();
        assertEquals("b1", o.kingPosition(Color.WHITE));
        assertEquals("c4", o.kingPosition(Color.BLACK));
    }

}
