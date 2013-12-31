package org.junitcustom.data.suite;

import org.junit.Ignore;
import org.junit.runner.RunWith;

import org.junitcustom.Categories;
import org.junitcustom.IncludeCategory;
import org.junitcustom.data.annotation.LevelHigh;
import org.junitcustom.data.annotation.MonAnnnotation;

/**
 * @author ebesson
 */
@Ignore("ajouté seulement pour que l IDE ne le lance pas en tant que test")
@RunWith(Categories.class)
@IncludeCategory(categories = { MonAnnnotation.class, LevelHigh.class }, operator = IncludeCategory.AND)
public class MaSuiteMonAnnotationEtLevelHigh {

}
