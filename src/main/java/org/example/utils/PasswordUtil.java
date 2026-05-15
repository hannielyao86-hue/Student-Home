package org.example.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Classe utilitaire pour la sécurité des mots de passe.
 *
 * BCrypt permet :
 * - de hasher les mots de passe avant stockage en BDD
 * - de vérifier un mot de passe saisi contre le hash stocké
 * - d'ajouter automatiquement un "salt" (sel aléatoire)
 *
 * On ne stocke JAMAIS un mot de passe en clair en base.
 */
public class PasswordUtil {

    /**
     * Hash un mot de passe avec BCrypt.
     * À utiliser lors de la création d'un compte.
     *
     * @param password mot de passe en clair
     * @return mot de passe hashé (commence par $2a$)
     */
    public static String hashPassword(String password) {
        // gensalt(12) = niveau de sécurité (12 est standard)
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * Vérifie si un mot de passe correspond au hash stocké en BDD.
     * À utiliser lors de la connexion.
     *
     * @param password       mot de passe tapé par l'utilisateur
     * @param hashedPassword hash stocké dans la base (colonne mot_passe_hash)
     * @return true si le mot de passe est correct
     */
    public static boolean checkPassword(String password, String hashedPassword) {
        // BCrypt.checkpw compare automatiquement avec le salt intégré dans le hash
        return BCrypt.checkpw(password, hashedPassword);
    }
}