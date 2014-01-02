package org.categories4junit;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.experimental.categories.Category;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

/**
 * Implémentation d'un filtre permettant l'inclusion ou l'exclusion des classes.
 *
 * @author ebesson
 */
public final class CategoryFilter extends Filter {

    private final Set<Class<?>> fIncluded;

    private final Set<Class<?>> fExcluded;

    private final boolean fIncludedAny;

    private final boolean fExcludedAny;

    /**
     * Constructeur privé
     *
     * @param matchAnyIncludes si true
     * @param includes classes à inclure
     * @param matchAnyExcludes si true au moins une des catégories est exclue
     * @param excludes classes à exclure
     */
    private CategoryFilter(final boolean matchAnyIncludes, final Set<Class<?>> includes,
            final boolean matchAnyExcludes, final Set<Class<?>> excludes) {
        fIncludedAny = matchAnyIncludes;
        fExcludedAny = matchAnyExcludes;
        fIncluded = copyAndRefine(includes);
        fExcluded = copyAndRefine(excludes);
    }

    /**
     * Permet de filtrer les classes à inclure dans le filtre en définissant l'opérateur d'exclusion.
     *
     * @param matchAny si <tt>true</tt>, opérateur logique ou entre les classes, <tt>false</tt> opérateur logique ou entre les classes.
     * @param classes classes à inclure
     * @return une instance de l'objet avec les classes à inclure
     */
    public static CategoryFilter include(final boolean matchAny, final Class<?>... classes) {
        if (hasNull(classes)) {
            throw new RuntimeException("has null category");
        }
        return categoryFilter(matchAny, createSet(classes), true, null);
    }

    /**
     * Permet d'inclure une classe dans le filtre.
     *
     * @param classe classe à inclure
     * @return une instance du filtre avec la classe à inclure
     */
    public static CategoryFilter include(final Class<?> classe) {
        return include(true, classe);
    }

    /**
     * Permet d'inclure une classe dans le filtre.
     *
     * @param classes classes à inclure
     * @return une instance du filte avec les classes à inclure
     */
    public static CategoryFilter include(final Class<?>... classes) {
        return include(true, classes);
    }

    /**
     * Permet d'exclure des classes dans le filtre en définissant l'opérateur d'exclusion.
     *
     * @param matchAny si <tt>true</tt>, opérateur logique ou entre les classes, <tt>false</tt> opérateur logique ou entre les classes.
     * @param classes classes à exclure
     * @return une instance du filte avec les classes à exclure
     */
    public static CategoryFilter exclude(final boolean matchAny, final Class<?>... classes) {
        if (hasNull(classes)) {
            throw new RuntimeException("has null category");
        }
        return categoryFilter(true, null, matchAny, createSet(classes));
    }

    /**
     *  Permet d'exclure une classe dans le filtre.
     *
     * @param classe classe à exclure
     * @return une instance du filte avec la classe à exclure
     */
    public static CategoryFilter exclude(final Class<?> classe) {
        return exclude(true, classe);
    }

    /**
     *  Permet d'exclure des classes dans le filtre.
     *
     * @param classes classes à exclure
     * @return une instance du filte avec les classes à exclure
     */
    public static CategoryFilter exclude(final Class<?>... classes) {
        return exclude(true, classes);
    }

    /**
     * Permet définir une filtre en définissant les éléments à inclure et à exclure ainsi que les opérateurs logiques associés
     *
     * @param operatorOrInclusions si <tt>true</tt>, opérateur logique ou entre les élements à inclure, <tt>false</tt> opérateur logique ou entre les élements à inclure.
     * @param inclusions éléments à inclure
     * @param operatorOrExclusions si <tt>true</tt>, opérateur logique ou entre les élements à exclure, <tt>false</tt> opérateur logique ou entre les élements à exclure.
     * @param exclusions éléments à exclure
     * @return
     */
    public static CategoryFilter categoryFilter(final boolean operatorOrInclusions, final Set<Class<?>> inclusions,
            final boolean operatorOrExclusions, final Set<Class<?>> exclusions) {
        return new CategoryFilter(operatorOrInclusions, inclusions, operatorOrExclusions, exclusions);
    }

    /**
     * @see #toString()
     */
    @Override
    public String describe() {
        return toString();
    }

    /**
     * Returns string in the form <tt>"[included categories] - [excluded categories]"</tt>, where both
     * sets have comma separated names of categories.
     *
     * @return string representation for the relative complement of excluded categories set
     * in the set of included categories. Examples:
     * <ul>
     *  <li> <tt>"categories [all]"</tt> for all included categories and no excluded ones;
     *  <li> <tt>"categories [all] - [A, B]"</tt> for all included categories and given excluded ones;
     *  <li> <tt>"categories [A, B] - [C, D]"</tt> for given included categories and given excluded ones.
     * </ul>
     * @see Class#toString() name of category
     */
    @Override
    public String toString() {
        StringBuilder description = new StringBuilder("categories ")
                .append(fIncluded.isEmpty() ? "[all]" : fIncluded);
        if (!fExcluded.isEmpty()) {
            description.append(" - ").append(fExcluded);
        }
        return description.toString();
    }

    @Override
    public boolean shouldRun(final Description description) {
        if (hasCorrectCategoryAnnotation(description)) {
            return true;
        }

        for (Description each : description.getChildren()) {
            if (shouldRun(each)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param description
     * @return
     */
    private boolean hasCorrectCategoryAnnotation(final Description description) {
        final Set<Class<?>> childCategories = categories(description);

        // If a child has no categories, immediately return.
        if (childCategories.isEmpty()) {
            return fIncluded.isEmpty();
        }

        if (!fExcluded.isEmpty()) {
            if (fExcludedAny) {
                if (matchesAnyParentCategories(childCategories, fExcluded)) {
                    return false;
                }
            } else {
                if (matchesAllParentCategories(childCategories, fExcluded)) {
                    return false;
                }
            }
        }

        if (fIncluded.isEmpty()) {
            // Couldn't be excluded, and with no suite's included categories treated as should run.
            return true;
        } else {
            if (fIncludedAny) {
                return matchesAnyParentCategories(childCategories, fIncluded);
            } else {
                return matchesAllParentCategories(childCategories, fIncluded);
            }
        }
    }

    /**
     * @return <tt>true</tt> if at least one (any) parent category match a child, otherwise <tt>false</tt>.
     * If empty <tt>parentCategories</tt>, returns <tt>false</tt>.
     */
    private boolean matchesAnyParentCategories(final Set<Class<?>> childCategories, final Set<Class<?>> parentCategories) {
        for (Class<?> parentCategory : parentCategories) {
            if (hasAssignableTo(childCategories, parentCategory)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return <tt>false</tt> if at least one parent category does not match children, otherwise <tt>true</tt>.
     * If empty <tt>parentCategories</tt>, returns <tt>true</tt>.
     */
    private boolean matchesAllParentCategories(final Set<Class<?>> childCategories, final Set<Class<?>> parentCategories) {
        for (Class<?> parentCategory : parentCategories) {
            if (!hasAssignableTo(childCategories, parentCategory)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retourne la liste des {@link Category} pour un test
     *
     * @param description test
     * @return la liste des catégories pour ce test.
     */
    private static Set<Class<?>> categories(final Description description) {
        Set<Class<?>> categories = new HashSet<Class<?>>();
        Collections.addAll(categories, directCategories(description));
        Collections.addAll(categories, directCategories(parentDescription(description)));
        return categories;
    }

    /**
     * Création de la description d'un parent d'un test
     *
     * @param description
     * @return la description d'un test parent
     */
    private static Description parentDescription(final Description description) {
        Class<?> testClass = description.getTestClass();
        return testClass == null ? null : Description.createSuiteDescription(testClass);
    }

    /**
     * Méthode permettant déterminer si un test est annoté avec l'annotation {@link Category}
     *
     * @param description description d'un test
     * @return true si le test est annoté avec l'annotation {@link Category}, false sinon
     */
    private static Class<?>[] directCategories(final Description description) {
        if (description == null) {
            return new Class<?>[0];
        }

        Category annotation = description.getAnnotation(Category.class);
        return annotation == null ? new Class<?>[0] : annotation.value();
    }

    /**
     * @param classes
     * @return
     */
    private static Set<Class<?>> copyAndRefine(final Set<Class<?>> classes) {
        HashSet<Class<?>> c = new HashSet<Class<?>>();
        if (classes != null) {
            c.addAll(classes);
        }
        c.remove(null);
        return c;
    }

    /**
     * Permet de déterminer si une classe est null dans un varargs de classes
     *
     * @param classes : varargs
     * @return true si un des éléments est null
     */
    private static boolean hasNull(final Class<?>... classes) {
        if (classes == null) {
            return false;
        }
        for (Class<?> clazz : classes) {
            if (clazz == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Permet de déterminer si un classe est assignable à au moins un élément d'une collection
     *
     * @param assigns collection de classes
     * @param to classe à assigner
     * @return true si la la classe to est assignable à au moins un élement de la collection, false sinon
     */
    private static boolean hasAssignableTo(final Set<Class<?>> assigns, final Class<?> to) {
        for (final Class<?> from : assigns) {
            if (to.isAssignableFrom(from)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne un Set de classes à partir d'un vararg
     *
     * @param classes : classe(s) à inclure dans le set
     * @return un nouveau set de classes
     */
    private static Set<Class<?>> createSet(final Class<?>... classes) {
        final Set<Class<?>> set = new HashSet<Class<?>>();
        if (classes != null) {
            Collections.addAll(set, classes);
        }
        return set;
    }
}
