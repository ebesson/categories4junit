package org.categories4junit.data;

import org.categories4junit.data.annotation.LevelHigh;
import org.categories4junit.data.annotation.MonAnnnotation;
import org.junit.Test;
import org.junit.experimental.categories.Category;


/**
 * @author ebesson
 */
@Category(value = MonAnnnotation.class)
public class ClasseB {

    /**
     *
     */
    @Category(value = LevelHigh.class)
    @Test
    public void testHigh() {

    }
}
