/**
 *
 *
 * @author gboullanger
 */
package org.categories4junit.data;

import org.categories4junit.data.annotation.LevelLow;
import org.categories4junit.data.annotation.MonAnnnotation;
import org.junit.Test;
import org.junit.experimental.categories.Category;


/**
 * @author ebesson
 */
@Category(value = MonAnnnotation.class)
public class ClasseC {

    /**
     *
     */
    @Category(value = LevelLow.class)
    @Test
    public void testLow() {

    }
}
