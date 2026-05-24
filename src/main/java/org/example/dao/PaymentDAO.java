package org.example.dao;

// ── Imports Java ──────────────────────────────────────────────────────────────
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// ── Imports projet ────────────────────────────────────────────────────────────
import org.example.config.DatabaseConnection;
import org.example.model.Payment;

/**
 * ════════════════════════════════════════════════════════════
 * PaymentDAO — Data Access Object pour les paiements
 * ════════════════════════════════════════════════════════════
 *
 * Contient TOUTES les requêtes SQL liées aux paiements.
 *
 * Table MySQL : payments
 * Colonnes réelles (vérifiées avec DESCRIBE) :
 *   id_payment       VARCHAR(50)   PK  — identifiant manuel
 *   montant          DECIMAL(15,2)     — montant loyer
 *   date_paiement    DATE              — date paiement
 *   statut_paiement  VARCHAR(250)      — PAYE/EN_ATTENTE/RETARD
 *   penaliter        DECIMAL(15,2)     — pénalité retard
 *
 * @author Smart Student Residence Team
 * @version 1.0
 * ════════════════════════════════════════════════════════════
 */
public class PaymentDAO {

    // ── Connexion MySQL (Singleton) ───────────────────────────────────────────
    private final Connection cnx =
            DatabaseConnection.getInstance().getConnection();

    // ════════════════════════════════════════════════════════════════════════
    // CREATE — createPayment()
    //
    // Insère un nouveau paiement en base.
    // id_payment est un VARCHAR → on le génère manuellement (ex: PAY001)
    //
    // @param p le paiement à insérer
    // @return true si succès
    // ════════════════════════════════════════════════════════════════════════
    public boolean createPayment(Payment p) {

        // Toutes les colonnes réelles de la table payments
        String sql =
                "INSERT INTO payments " +
                        "(id_payment, montant, date_paiement, statut_paiement, penaliter) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, p.getIdPayment());      // id_payment (VARCHAR)
            ps.setDouble(2, p.getMontant());        // montant
            ps.setString(3, p.getDatePaiement());   // date_paiement
            ps.setString(4, p.getStatutPaiement()); // statut_paiement
            ps.setDouble(5, p.getPenaliter());      // penaliter

            ps.executeUpdate();

            System.out.println("[PaymentDAO] ✅ Paiement ajouté : " +
                    p.getIdPayment() + " | " + p.getMontant() + " €");
            return true;

        } catch (Exception e) {
            System.out.println("[PaymentDAO] ❌ Erreur ajout : " + e.getMessage());
            return false;
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // READ ALL — getAllPayments()
    //
    // Récupère tous les paiements triés par date décroissante.
    //
    // @return liste de tous les paiements
    // ════════════════════════════════════════════════════════════════════════
    public List<Payment> getAllPayments() {

        List<Payment> list = new ArrayList<>();

        String sql = "SELECT * FROM payments ORDER BY date_paiement DESC";

        try (PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            System.out.println("[PaymentDAO] ❌ Erreur lecture : " + e.getMessage());
        }

        return list;
    }

    // ════════════════════════════════════════════════════════════════════════
    // READ RETARDS — getPaymentsEnRetard()
    //
    // Récupère uniquement les paiements en retard.
    // Utilisé par le dashboard pour afficher les alertes.
    //
    // @return liste des paiements avec statut = 'RETARD'
    // ════════════════════════════════════════════════════════════════════════
    public List<Payment> getPaymentsEnRetard() {

        List<Payment> list = new ArrayList<>();

        String sql =
                "SELECT * FROM payments " +
                        "WHERE statut_paiement = 'RETARD' " +
                        "ORDER BY date_paiement ASC";

        try (PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            System.out.println("[PaymentDAO] ❌ Erreur retards : " + e.getMessage());
        }

        return list;
    }

    // ════════════════════════════════════════════════════════════════════════
    // READ BY ID — findById()
    //
    // Recherche un paiement par son identifiant.
    //
    // @param idPayment identifiant du paiement (ex: PAY001)
    // @return Payment trouvé ou null
    // ════════════════════════════════════════════════════════════════════════
    public Payment findById(String idPayment) {

        String sql = "SELECT * FROM payments WHERE id_payment = ?";

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, idPayment);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (Exception e) {
            System.out.println("[PaymentDAO] ❌ Erreur findById : " + e.getMessage());
        }

        return null;
    }

    // ════════════════════════════════════════════════════════════════════════
    // UPDATE STATUT — updateStatut()
    //
    // Met à jour le statut d'un paiement.
    // Ex: passer de EN_ATTENTE à PAYE quand l'étudiant paie.
    //
    // @param idPayment identifiant du paiement
    // @param statut    nouveau statut
    // @return true si succès
    // ════════════════════════════════════════════════════════════════════════
    public boolean updateStatut(String idPayment, String statut) {

        String sql =
                "UPDATE payments " +
                        "SET statut_paiement = ? " +
                        "WHERE id_payment = ?";

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, statut);
            ps.setString(2, idPayment);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("[PaymentDAO] ✅ Statut mis à jour → " +
                        statut + " (ID: " + idPayment + ")");
                return true;
            }

            System.out.println("[PaymentDAO] ⚠️ Paiement introuvable : " + idPayment);
            return false;

        } catch (Exception e) {
            System.out.println("[PaymentDAO] ❌ Erreur update statut : " + e.getMessage());
            return false;
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // UPDATE PENALITE — updatePenalite()
    //
    // Met à jour la pénalité d'un paiement en retard.
    //
    // @param idPayment identifiant du paiement
    // @param penalite  nouveau montant de pénalité
    // @return true si succès
    // ════════════════════════════════════════════════════════════════════════
    public boolean updatePenalite(String idPayment, double penalite) {

        String sql =
                "UPDATE payments " +
                        "SET penaliter = ? " +
                        "WHERE id_payment = ?";

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setDouble(1, penalite);
            ps.setString(2, idPayment);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("[PaymentDAO] ✅ Pénalité mise à jour : " +
                        penalite + " € (ID: " + idPayment + ")");
                return true;
            }
            return false;

        } catch (Exception e) {
            System.out.println("[PaymentDAO] ❌ Erreur pénalité : " + e.getMessage());
            return false;
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // DELETE — deletePayment()
    //
    // Supprime un paiement par son ID.
    //
    // @param idPayment identifiant du paiement à supprimer
    // @return true si succès
    // ════════════════════════════════════════════════════════════════════════
    public boolean deletePayment(String idPayment) {

        String sql = "DELETE FROM payments WHERE id_payment = ?";

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, idPayment);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("[PaymentDAO] ✅ Paiement supprimé (ID: " + idPayment + ")");
                return true;
            }
            return false;

        } catch (Exception e) {
            System.out.println("[PaymentDAO] ❌ Erreur suppression : " + e.getMessage());
            return false;
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // COUNT — countRetards()
    //
    // Compte le nombre de paiements en retard.
    // Utilisé par le dashboard pour la statistique.
    //
    // @return nombre de paiements en retard
    // ════════════════════════════════════════════════════════════════════════
    public int countRetards() {

        String sql =
                "SELECT COUNT(*) FROM payments WHERE statut_paiement = 'RETARD'";

        try (PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            System.out.println("[PaymentDAO] ❌ Erreur comptage : " + e.getMessage());
        }

        return 0;
    }

    // ════════════════════════════════════════════════════════════════════════
    // GENERATE ID — generateId()
    //
    // Génère automatiquement un nouvel identifiant de paiement.
    // Format : PAY001, PAY002, PAY003...
    //
    // Compte le nombre de paiements existants et incrémente de 1.
    //
    // @return nouvel identifiant unique (ex: PAY004)
    // ════════════════════════════════════════════════════════════════════════
    public String generateId() {

        String sql = "SELECT COUNT(*) FROM payments";

        try (PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                // On prend le nombre actuel + 1 et on formate sur 3 chiffres
                int next = rs.getInt(1) + 1;
                return String.format("PAY%03d", next); // PAY001, PAY002...
            }

        } catch (Exception e) {
            System.out.println("[PaymentDAO] ❌ Erreur generateId : " + e.getMessage());
        }

        return "PAY001"; // valeur par défaut si erreur
    }

    // ════════════════════════════════════════════════════════════════════════
    // MAPPING — mapResultSet()
    //
    // Convertit une ligne SQL en objet Payment Java.
    // Méthode privée centralisée pour éviter les répétitions.
    // ════════════════════════════════════════════════════════════════════════
    private Payment mapResultSet(ResultSet rs) throws SQLException {
        return new Payment(
                rs.getString("id_payment"),        // VARCHAR
                rs.getDouble("montant"),           // DECIMAL
                rs.getString("date_paiement"),     // DATE
                rs.getString("statut_paiement"),   // VARCHAR
                rs.getDouble("penaliter")          // DECIMAL
        );
    }
}