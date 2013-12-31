package org.junitcustom.data;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import org.junitcustom.data.annotation.LevelHigh;
import org.junitcustom.data.annotation.LevelLow;
import org.junitcustom.data.annotation.MonAnnnotation;

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
