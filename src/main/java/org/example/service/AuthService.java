package org.example.service;

import org.example.dao.UserDAO;
import org.example.model.User;
import org.example.utils.PasswordUtil;

/**
 * Service d'authentification
 *
 * Responsable de la logique de connexion :
 * - vérification email
 * - vérification mot de passe (BCrypt)
 * - vérification statut compte
 */
public class AuthService {

    private UserDAO userDAO;

    /**
     * Utilisateur connecté (SESSION simple)
     */
    private static User currentUser;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    /**
     *  LOGIN UTILISATEUR
     *
     * @param email email saisi
     * @param password mot de passe saisi
     * @return true si connexion OK
     */
    public boolean login(String email, String password) {

        // 1. Vérifier si l'utilisateur existe
        User user = userDAO.findByEmail(email);

        if (user == null) {
            System.out.println("❌ Email introuvable");
            return false;
        }

        // 2. Vérifier statut du compte
        if (!"ACTIF".equalsIgnoreCase(user.getStatut())) {
            System.out.println("❌ Compte désactivé");
            return false;
        }

        // 3. Vérifier mot de passe avec BCrypt si le hash le supporte,
        //    sinon accepter le mot de passe en clair pour les comptes plus anciens.
        boolean passwordValid = false;
        String storedHash = user.getMotPasseHash();

        if (storedHash != null && (storedHash.startsWith("$2a$") || storedHash.startsWith("$2b$") || storedHash.startsWith("$2y$"))) {
            passwordValid = PasswordUtil.checkPassword(password, storedHash);
        } else {
            passwordValid = storedHash != null && storedHash.equals(password);
        }

        if (!passwordValid) {
            System.out.println("❌ Mot de passe incorrect");
            return false;
        }

        // 4. Connexion réussie → session utilisateur
        currentUser = user;

        System.out.println("✅ Connexion réussie : " +
                user.getPrenomUser() + " " + user.getNomUsers());

        return true;
    }

    /**
     *  Récupérer utilisateur connecté
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     *  Déconnexion
     */
    public void logout() {
        currentUser = null;
        System.out.println("🚪 Déconnexion réussie");
    }
}