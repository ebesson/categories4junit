package org.junitcustom;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.runners.Suite;
import org.junit.experimental.categories.Category;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.NoTestsRemainException;

import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * Implémentation d'un runner permettant de lancer des tests catégorisés avec l'annotation {@link Category}. Ce runner utilise l'annotation {@link IncludeCategory} pour déterminer
 * les tests à lancer. L'annotation {@link TestPackageToScan} peut être utilisée pour redéfinir le package à scanner
 *
 * @author ebesson
 */
public class Categories extends Suite {

    public Categories(final RunnerBuilder builder, final Class<?> clazz, final Class<?>[] suiteClasses) throws InitializationError {
        super(builder, clazz, suiteClasses);
    }

    /**
     * @param klass : suite de testes à lancer
     * @param builder : builder utilisé pour lancer la suite de testsHi
     * @throws InitializationError si il y a une erreur d'initialisation
     */
    public Categories(final Class<?> klass, final RunnerBuilder builder) throws InitializationError {
        super(builder, klass, ClasspathClassesFinder.getSuiteClasses(getPackageToScann(klass), Category.class));
        try {
            Set<Class<?>> included = getIncludedCategory(klass);
            boolean isAnyIncluded = isAnyIncluded(klass);
            filter(CategoryFilter.categoryFilter(isAnyIncluded, included, false, new HashSet<Class<?>>()));

        } catch (NoTestsRemainException e) {
            throw new RuntimeException("Aucun tests à lancer", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertNoCategorizedDescendentsOfUncategorizeableParents(getDescription());

    }

    /**
     * Méthode permetttant de récupérer le package à scanner pour créer dynamiquement la suite de tests
     *
     * @param klass
     * @return le package à scanner pour créer dynamiquement la suite de tests
     * @throws InitializationError si il y a une erreur.
     */
    private static String getPackageToScann(final Class<?> klass) throws InitializationError {
        TestPackageToScan annotation = klass.getAnnotation(TestPackageToScan.class);
        if (annotation != null && !"".equals(annotation.basePackage())) {
            return annotation.basePackage();
        }
        throw new InitializationError("Le che;");
    }

    /**
     * Retourne la collection des catégories à inclure dans la suite de tests
     *
     * @param suiteklass suite de tests
     * @return la collection des catégories à inclure
     * @throws ClassNotFoundException
     */
    private static Set<Class<?>> getIncludedCategory(final Class<?> suiteklass) throws ClassNotFoundException {
        IncludeCategory annotation = suiteklass.getAnnotation(IncludeCategory.class);
        return createSet(annotation == null ? null : annotation.categories());
    }

    /**
     * Retourne l'opérateur logique utilisé pour la relation entre les catégories
     *
     * @param suiteklass suiteklass suite de tests
     * @return  l'opérateur logique utilisé
     */
    private static boolean isAnyIncluded(final Class<?> suiteklass) {
        IncludeCategory annotation = suiteklass.getAnnotation(IncludeCategory.class);
        return annotation == null || annotation.operator();
    }

    /**
     * @param description
     * @throws RuntimeException
     */
    private static void assertNoCategorizedDescendentsOfUncategorizeableParents(final Description description) {
        if (!canHaveCategorizedChildren(description)) {
            assertNoDescendantsHaveCategoryAnnotations(description);
        }
        for (Description each : description.getChildren()) {
            assertNoCategorizedDescendentsOfUncategorizeableParents(each);
        }
    }

    /**
     * @param description
     * @throws RuntimeException
     */
    private static void assertNoDescendantsHaveCategoryAnnotations(final Description description) {
        for (Description each : description.getChildren()) {
            if (each.getAnnotation(Category.class) != null) {
                throw new RuntimeException("Category annotations on Parameterized classes are not supported on individual methods.");
            }
            assertNoDescendantsHaveCategoryAnnotations(each);
        }
    }

    /**
     * @param description
     * @return
     */
    private static boolean canHaveCategorizedChildren(final Description description) {
        for (Description each : description.getChildren()) {
            if (each.getTestClass() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retourne un Set de classes
     *
     * @param t : classe(s) à inclure dans le set
     * @return un nouveau set de classes
     */
    private static Set<Class<?>> createSet(final Class<?>... t) {
        final Set<Class<?>> set = new HashSet<Class<?>>();
        if (t != null) {
            Collections.addAll(set, t);
        }
        return set;
    }

    /**
     * @return la liste des instances {@link Runner} contenant les tests à lancer
     */
    public List<Runner> getRunners() {
        return getChildren();
    }
}
