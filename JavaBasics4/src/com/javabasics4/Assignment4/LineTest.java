package com.javabasics4.Assignment4;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LineTest {
    Line l = new Line(1,1,3,3);
    Line line2 = new Line(0,0,2,4);

    @Test
    public void testGetSlope(){
        assertEquals(1, l.getSlope(), .001);

        assertEquals(2, line2.getSlope(), .001);
    }

    @Test
    public void testGetDistance(){
        assertEquals(Math.sqrt(8),l.getDistance(),.001);
    }
    @Test
    public void testParallelTo(){

        assertEquals(false,l.parallelTo(line2));

        Line tempLine = new Line(0,3,3,6);
        assertEquals(true,l.parallelTo(tempLine));
    }

}
