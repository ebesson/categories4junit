package org.junitcustom;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation permettant d'inclure des catégories et de spécifier l'opérateur logique entre les catégories pour le lancement de tests JUnit.
 *
 * @author ebesson
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IncludeCategory {

    /** Opéateur logique OU. */
    boolean OR = true;

    /** Opéateur logique ET. */
    boolean AND = false;

    /**
     * Détermine les catégories {@link org.junit.experimental.categories.Category.Category} qui seront utilisées dans les tests à lancer.
     */
    Class<?>[] categories() default {

    };

    /**
     * Détermine le lien logique utilisé entre les catégories pour lancer les tests.
     * Deux operateurs sont défini :
     * <ul>
     * <li>ou : {@link org.junitcustom.IncludeCategory.OR}</li>
     * <li>et : {@link org.junitcustom.IncludeCategory.AND}</li>
     * </ul>
     */
    boolean operator() default OR;
}
