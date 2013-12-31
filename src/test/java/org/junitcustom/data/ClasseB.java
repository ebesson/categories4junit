package org.junitcustom.data;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import org.junitcustom.data.annotation.LevelHigh;
import org.junitcustom.data.annotation.MonAnnnotation;

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
