package org.example.controller;

// ───────────────── IMPORTS JAVA FX ─────────────────
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

// ───────────────── IMPORTS SQL ─────────────────
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// ───────────────── IMPORTS PROJET ─────────────────
import org.example.config.DatabaseConnection;

/**
 * =========================================================
 * DashboardController
 * =========================================================
 *
 * Contrôleur principal du dashboard.
 *
 * Responsabilités :
 * ✔ Charger les statistiques
 * ✔ Naviguer entre les modules
 * ✔ Déconnexion
 *
 * =========================================================
 */
public class DashboardController {

    // =====================================================
    // CONNEXION MYSQL
    // =====================================================

    private final Connection cnx =
            DatabaseConnection
                    .getInstance()
                    .getConnection();

    // =====================================================
    // LABELS DASHBOARD
    // =====================================================

    @FXML
    private Label nomUserLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label nbEtudiantsLabel;

    @FXML
    private Label nbLogementsLabel;

    @FXML
    private Label nbIncidentsLabel;

    // =====================================================
    // INITIALISATION
    // =====================================================

    @FXML
    public void initialize() {

        System.out.println("→ Initialisation DashboardController");

        // Charger statistiques
        loadStats();

        // Données utilisateur temporaires
        nomUserLabel.setText("Admin");
        roleLabel.setText("ADMIN");

        System.out.println("✅ Dashboard chargé");
    }

    // =====================================================
    // CHARGER LES STATISTIQUES
    // =====================================================

    private void loadStats() {

        try {

            // =================================================
            // NOMBRE ÉTUDIANTS
            // =================================================

            String sqlStudents =
                    "SELECT COUNT(*) FROM students";

            PreparedStatement stmtStudents =
                    cnx.prepareStatement(sqlStudents);

            ResultSet rsStudents =
                    stmtStudents.executeQuery();

            if (rsStudents.next()) {

                nbEtudiantsLabel.setText(
                        String.valueOf(
                                rsStudents.getInt(1)
                        )
                );
            }

            // =================================================
            // NOMBRE LOGEMENTS LIBRES
            // =================================================

            String sqlRooms =
                    "SELECT COUNT(*) FROM rooms " +
                            "WHERE statut_room='LIBRE'";

            PreparedStatement stmtRooms =
                    cnx.prepareStatement(sqlRooms);

            ResultSet rsRooms =
                    stmtRooms.executeQuery();

            if (rsRooms.next()) {

                nbLogementsLabel.setText(
                        String.valueOf(
                                rsRooms.getInt(1)
                        )
                );
            }

            // =================================================
            // NOMBRE INCIDENTS
            // =================================================

            String sqlIncidents =
                    "SELECT COUNT(*) FROM incidents";

            PreparedStatement stmtIncidents =
                    cnx.prepareStatement(sqlIncidents);

            ResultSet rsIncidents =
                    stmtIncidents.executeQuery();

            if (rsIncidents.next()) {

                nbIncidentsLabel.setText(
                        String.valueOf(
                                rsIncidents.getInt(1)
                        )
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "❌ Erreur chargement statistiques"
            );

            e.printStackTrace();
        }
    }

    // =====================================================
    // NAVIGATION → ÉTUDIANTS
    // =====================================================

    @FXML
    public void handleEtudiants() {

        System.out.println(
                "→ Chargement module étudiants..."
        );

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/view/students.fxml"
                            )
                    );

            Scene scene =
                    new Scene(loader.load());

            Stage stage =
                    (Stage) nbEtudiantsLabel
                            .getScene()
                            .getWindow();

            stage.setScene(scene);

            stage.setTitle(
                    "Gestion des Étudiants"
            );

            stage.setMaximized(true);

            System.out.println(
                    "✅ Module étudiants chargé"
            );

        } catch (Exception e) {

            System.out.println(
                    "❌ Erreur ouverture étudiants"
            );

            e.printStackTrace();
        }
    }

    // =====================================================
    // NAVIGATION → LOGEMENTS
    // =====================================================

    @FXML
    public void handleLogements() {

        System.out.println(
                "→ Chargement logements..."
        );

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/view/logements.fxml"
                            )
                    );

            Scene scene =
                    new Scene(loader.load());

            Stage stage =
                    (Stage) nbEtudiantsLabel
                            .getScene()
                            .getWindow();

            stage.setScene(scene);

            stage.setTitle(
                    "Gestion des Logements"
            );

            stage.setMaximized(true);

            System.out.println(
                    "✅ Logements chargés"
            );

        } catch (Exception e) {

            System.out.println(
                    "❌ Erreur logements"
            );

            e.printStackTrace();
        }
    }

    // =====================================================
    // NAVIGATION → PAIEMENTS
    // =====================================================
    @FXML
    public void handlePaiements() {

        System.out.println("→ Chargement paiements...");

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/view/paiements.fxml"
                            )
                    );

            Scene scene =
                    new Scene(loader.load());

            Stage stage =
                    (Stage) nbEtudiantsLabel
                            .getScene()
                            .getWindow();

            stage.setScene(scene);

            stage.setTitle(
                    "Gestion des Paiements"
            );

            stage.setMaximized(true);

            System.out.println("✅ Paiements chargés");

        } catch (Exception e) {

            System.out.println("❌ Erreur paiements");

            e.printStackTrace();
        }
    }

    // =====================================================
    // NAVIGATION → INCIDENTS
    // =====================================================

    @FXML
    public void handleIncidents() {

        System.out.println(
                "→ Module Incidents"
        );
    }

    // =====================================================
    // DÉCONNEXION
    // =====================================================

    @FXML
    public void handleLogout() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/view/login.fxml"
                            )
                    );

            Scene scene =
                    new Scene(loader.load());

            Stage stage =
                    (Stage) nbEtudiantsLabel
                            .getScene()
                            .getWindow();

            stage.setScene(scene);

            stage.setTitle(
                    "Connexion"
            );

            stage.setMaximized(true);

            System.out.println(
                    "✅ Déconnexion réussie"
            );

        } catch (Exception e) {

            System.out.println(
                    "❌ Erreur déconnexion"
            );

            e.printStackTrace();
        }
    }
}