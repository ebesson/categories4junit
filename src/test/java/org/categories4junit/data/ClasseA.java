package org.categories4junit.data;

import org.categories4junit.data.annotation.LevelHigh;
import org.categories4junit.data.annotation.LevelLow;
import org.categories4junit.data.annotation.MonAnnnotation;
import org.junit.Test;
import org.junit.experimental.categories.Category;


/**
 * @author ebesson
 */
@Category(value = MonAnnnotation.class)
public class ClasseA {

    /**
     *
     */
    @Category(value = { LevelLow.class, LevelHigh.class })
    @Test
    public void testLow() {

    }
}
