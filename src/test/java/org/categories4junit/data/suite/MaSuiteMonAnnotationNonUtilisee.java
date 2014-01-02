package org.categories4junit.data.suite;

import org.categories4junit.Categories;
import org.categories4junit.annotation.IncludeCategory;
import org.categories4junit.annotation.TestPackageToScan;
import org.categories4junit.data.annotation.MonAnnnotationNonUtilisee;
import org.junit.Ignore;
import org.junit.runner.RunWith;

/**
 * @author ebesson
 */
@Ignore("ajouté seulement pour que IDE ne le lance pas en tant que test")
@RunWith(Categories.class)
@IncludeCategory(categories = { MonAnnnotationNonUtilisee.class })
@TestPackageToScan(basePackage = "org.categories4junit.data")
public class MaSuiteMonAnnotationNonUtilisee {

}
