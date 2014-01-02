/**
 *
 *
 * @author gboullanger
 */
package org.categories4junit;

import java.util.List;

import org.categories4junit.Categories;
import org.categories4junit.data.suite.MaSuiteMonAnnotationNonUtilisee;
import org.categories4junit.data.suite.MaSuiteMonAnnotationOuLevelHigh;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runner.Runner;
import org.junit.runners.model.InitializationError;



/**
 * Classe de tests pour la classe utilitaire {@link Categories}.
 *
 * @author ebesson
 */
public class CategoriesTest {

    /**
     * Test le cas ou il n'y a aucun tests à lancer.
     *
     * @throws RuntimeException
     */
    @Test(expected = RuntimeException.class)
    public void doitRenvoyerRuntimeExceptionSiAucunTests() {

        // Etant donnée une suite de tests avec une annotation non utilisée
        Class<MaSuiteMonAnnotationNonUtilisee> suite = MaSuiteMonAnnotationNonUtilisee.class;

        try {
            // Si j'essaye de lancer cette suite de tests
            new Categories(suite, new AllDefaultPossibilitiesBuilder(true));

            // Alors une exception du type RuntimeException est levée
        } catch (InitializationError e) {
            Assert.fail("Erreur : " + e.getMessage());
        }
        Assert.fail("Une exception du type RuntimeException doit être levée ");
    }

    /**
     * Test le cas ou il y a des tests à lancer.
     */
    @Test
    public void doitRenvoyerUneListeDeRunnersSiTestsTrouves() {

        // Etant donnée une suite de tests avec une annotation utilisée
        Class<MaSuiteMonAnnotationOuLevelHigh> suite = MaSuiteMonAnnotationOuLevelHigh.class;

        try {
            // Si j'essaye de lancer cette suite de tests
            Categories pJCategories = new Categories(suite, new AllDefaultPossibilitiesBuilder(true));

            // Alors la liste des tests à lancer n'est pas vide
            List<Runner> runners = pJCategories.getRunners();
            Assert.assertFalse("La liste doit contenir au moins un runner", runners.isEmpty());
        } catch (InitializationError e) {
            Assert.fail("Erreur : " + e.getMessage());
        }
    }

}
