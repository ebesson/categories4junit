package org.categories4junit.data.suite;

import org.categories4junit.Categories;
import org.categories4junit.annotation.IncludeCategory;
import org.categories4junit.annotation.TestPackageToScan;
import org.categories4junit.data.annotation.LevelHigh;
import org.categories4junit.data.annotation.MonAnnnotation;
import org.junit.Ignore;
import org.junit.runner.RunWith;


/**
 * @author ebesson
 */
@Ignore("ajouté seulement pour que l IDE ne le lance pas en tant que test")
@RunWith(Categories.class)
@IncludeCategory(categories = { MonAnnnotation.class, LevelHigh.class }, operator = IncludeCategory.AND)
@TestPackageToScan(basePackage = "org.categories4junit.data")
public class MaSuiteMonAnnotationEtLevelHigh {

}
