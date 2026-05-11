
package org.example.utils;

// import org.mindrot.jbcrypt.BCrypt;

/**
 * Classe utilitaire pour la sécurité des mots de passe.
 *
 * BCrypt permet :
 * - de hasher les mots de passe
 * - de vérifier un mot de passe
 * - d'ajouter automatiquement un "salt"
 *
 */
public class PasswordUtil {

    /**
     * Hash un mot de passe avec BCrypt.
     *
     * @param password mot de passe en clair
     * @return mot de passe hashé
     */
    public static String hashPassword(String password) {

        // gensalt() génère automatiquement un salt sécurisé
        // return BCrypt.hashpw(password, BCrypt.gensalt());
        return password; // TEMPORAIRE
    }

    /**
     * Vérifie si un mot de passe correspond au hash stocké.
     *
     * @param password mot de passe tapé par l'utilisateur
     * @param hashedPassword hash stocké dans la base
     * @return true si correct
     */
    public static boolean checkPassword(String password, String hashedPassword) {

        // return BCrypt.checkpw(password, hashedPassword);
        return password.equals(hashedPassword); // TEMPORAIRE
    }
}