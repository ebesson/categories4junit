package org.junitcustom.data.suite;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junitcustom.Categories;

import org.junitcustom.Categories;
import org.junitcustom.IncludeCategory;
import org.junitcustom.data.annotation.LevelHigh;
import org.junitcustom.data.annotation.MonAnnnotationNonUtilisee;

/**
 * @author ebesson
 */
@Ignore("ajouté seulement pour que IDE ne le lance pas en tant que test")
@RunWith(Categories.class)
@IncludeCategory(categories = { MonAnnnotationNonUtilisee.class })
public class MaSuiteMonAnnotationNonUtilisee {

}
