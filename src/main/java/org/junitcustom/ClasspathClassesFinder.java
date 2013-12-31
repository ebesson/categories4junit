package org.junitcustom;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


/**
 * Utilitaire permettant retourner une liste de classes qui sont annotées avec une certaine annotation.
 *
 *
 * @author ebesson
 */
public final class ClasspathClassesFinder {

    private static final String EXTENSION_CLASS = ".class";

    private ClasspathClassesFinder() {

    }

    /**
     *  Retourne la liste des classes d'un package qui sont annotées avec une certaine annotation.
     *
     * @param packageName  le nom du package à scanner
     * @param testAnnotation  l'annotation à rechercher.
     * @return la liste des classes qui correspondent à la demande.
     */
    public static Class<?>[] getSuiteClasses(final String packageName, final Class<? extends Annotation> testAnnotation) {
        try {
            return getClasses(packageName, testAnnotation);
        } catch (Exception e) {
            new RuntimeException(e);
        }
        return null;
    }

    /**
     * Retourne la liste des classes d'un package qui sont annotées avec une certaine annotation.
     *
     * @param packageName le nom du package à scanner
     * @param annotation l'annotation à rechercher.
     * @return la liste des classes qui correspondent à la demande.
     * @throws ClassNotFoundException
     * @throws java.io.IOException
     */
    private static Class<?>[] getClasses(final String packageName, final Class<? extends Annotation> annotation) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        // Get classpath
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        // For each classpath, get the classes.
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName, annotation));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Find classes, in a given directory (recurively), for a given package name, that are annotated by a given annotation.
     *
     * @param directory The directory where to look for.
     * @param packageName The package name of the classes.
     * @param annotation The annotation the class should be annotated with.
     * @return The List of classes that matches the requirements.
     * @throws ClassNotFoundException If something goes wrong...
     */
    private static List<Class<?>> findClasses(final File directory, final String packageName, final Class<? extends Annotation> annotation)
            throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName(), annotation));
            } else if (file.getName().endsWith(EXTENSION_CLASS)) {
                // We remove the .class at the end of the filename to get the class name...
                Class<?> clazz = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - EXTENSION_CLASS.length()));
                // Does the file is annotated with the given annotation?
                if (clazz.getAnnotation(annotation) != null) {
                    classes.add(clazz);
                }
            }
        }
        return classes;
    }

}
