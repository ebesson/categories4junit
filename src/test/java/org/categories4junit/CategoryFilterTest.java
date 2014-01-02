package org.categories4junit;

import java.util.HashSet;
import java.util.Set;

import org.categories4junit.CategoryFilter;
import org.categories4junit.data.ClasseA;
import org.categories4junit.data.annotation.MonAnnnotation;
import org.categories4junit.data.annotation.MonAnnnotationNonUtilisee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Description;



/**
 * Classe de tests pour la classe utilitaire {@link CategoryFilter}.
 *
 * @author ebesson
 */
public class CategoryFilterTest {

    /**
     * Test le filtre sur l'inclusion avec l'opérateur OU.
     */
    @Test
    public void doitFilterCategoriesAvecOperateurOu() {

        // Etant donnée une classe de test avec l'annotation MonAnnnotation
        Description descriptionClasseA = Description.createSuiteDescription(ClasseA.class);

        // Quand je filtre avec avec les annotations MonAnnnotation et MonAnnnotationNonUtilisee
        Set<Class<?>> inclusions = new HashSet<Class<?>>();
        inclusions.add(MonAnnnotation.class);
        inclusions.add(MonAnnnotationNonUtilisee.class);
        // Et que j'utilise l'opérateur ou
        boolean opertorOr = true;

        // Alors mon filtre contient ma classe de tests
        CategoryFilter catergoryFilter = CategoryFilter.categoryFilter(opertorOr, inclusions, false, null);
        Assert.assertTrue(catergoryFilter.shouldRun(descriptionClasseA));
    }

    /**
     * Test le filtre sur l'inclusion avec l'opérateur ET.
     */
    @Test
    public void doitFilterCategoriesAvecOperateurEt() {

        // Etant donnée une classe de test avec l'annotation MonAnnnotation
        Description descriptionClasseA = Description.createSuiteDescription(ClasseA.class);

        // Quand je filtre avec avec les annotations MonAnnnotation et MonAnnnotationNonUtilisee
        Set<Class<?>> inclusions = new HashSet<Class<?>>();
        inclusions.add(MonAnnnotation.class);
        inclusions.add(MonAnnnotationNonUtilisee.class);
        // Et que j'utilise l'opérateur et
        boolean opertorOr = false;

        // Alors mon filtre contient ma classe de tests
        CategoryFilter catergoryFilter = CategoryFilter.categoryFilter(opertorOr, inclusions, false, null);
        Assert.assertFalse(catergoryFilter.shouldRun(descriptionClasseA));
    }
}
