package org.junitcustom;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation permettant de spécifier le package à scanner pour créer dynamiquement une suite de test JUnit. Cette annotation est utilisée par le runner @{link {@link PJCategories}}
 *
 * @author ebesson
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestPackageToScan {

    /**
     * Valeur defaut du package à scanner.
     */
    String DEFAULT_PACKAGE_TO_SCAN = "";

    /**
     * Permet de définir le package à scanner. Par défaut le package de base est fr.pagjaunes.
     */
    String basePackage() default DEFAULT_PACKAGE_TO_SCAN;
}
