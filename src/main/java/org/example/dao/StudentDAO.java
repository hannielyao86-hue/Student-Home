package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.example.config.DatabaseConnection;
import org.example.model.Student;

public class StudentDAO {

    // ================= CONNEXION =================

    private final Connection cnx =
            DatabaseConnection.getInstance().getConnection();

    // ================= CREATE =================

    public void createStudent(Student s) {

        String sql = """
            INSERT INTO students
            (nom, prenom, email, ecole, telephone,
             date_entree, date_sortie, id_users)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, s.getNom());
            ps.setString(2, s.getPrenom());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getEcole());
            ps.setString(5, s.getTelephone());
            ps.setString(6, s.getDateEntree());
            ps.setString(7, s.getDateSortie());
            ps.setInt(8, s.getIdUsers());

            ps.executeUpdate();

            System.out.println("✅ Étudiant ajouté");

        } catch (Exception e) {

            System.out.println("❌ Erreur createStudent : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ================= READ ALL =================

    public List<Student> getAllStudents() {

        List<Student> list = new ArrayList<>();

        String sql = """
            SELECT * FROM students
            ORDER BY id_student
        """;

        try (
                PreparedStatement ps = cnx.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Student s = new Student(

                        rs.getInt("id_student"),

                        rs.getString("nom"),

                        rs.getString("prenom"),

                        rs.getString("email"),

                        rs.getString("ecole"),

                        rs.getString("telephone"),

                        rs.getString("date_entree"),

                        rs.getString("date_sortie"),

                        rs.getInt("id_users")
                );

                list.add(s);
            }

        } catch (Exception e) {

            System.out.println("❌ Erreur getAllStudents : " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    // ================= UPDATE =================

    public void updateStudent(Student s) {

        String sql = """
            UPDATE students
            SET
                nom = ?,
                prenom = ?,
                email = ?,
                ecole = ?,
                telephone = ?,
                date_entree = ?,
                date_sortie = ?,
                id_users = ?
            WHERE id_student = ?
        """;

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, s.getNom());
            ps.setString(2, s.getPrenom());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getEcole());
            ps.setString(5, s.getTelephone());
            ps.setString(6, s.getDateEntree());
            ps.setString(7, s.getDateSortie());
            ps.setInt(8, s.getIdUsers());

            ps.setInt(9, s.getIdStudent());

            ps.executeUpdate();

            System.out.println("✅ Étudiant modifié");

        } catch (Exception e) {

            System.out.println("❌ Erreur updateStudent : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ================= DELETE =================

    public void deleteStudent(int id) {

        String sql = """
            DELETE FROM students
            WHERE id_student = ?
        """;

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                System.out.println("✅ Étudiant supprimé");

            } else {

                System.out.println("⚠ Aucun étudiant trouvé");
            }

        } catch (Exception e) {

            System.out.println("❌ Erreur deleteStudent : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ================= COUNT =================

    public int countStudents() {

        String sql = """
            SELECT COUNT(*) FROM students
        """;

        try (
                PreparedStatement ps = cnx.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {

            System.out.println("❌ Erreur countStudents : " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    // ================= FIND BY ID =================

    public Student getStudentById(int id) {

        String sql = """
            SELECT * FROM students
            WHERE id_student = ?
        """;

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Student(

                        rs.getInt("id_student"),

                        rs.getString("nom"),

                        rs.getString("prenom"),

                        rs.getString("email"),

                        rs.getString("ecole"),

                        rs.getString("telephone"),

                        rs.getString("date_entree"),

                        rs.getString("date_sortie"),

                        rs.getInt("id_users")
                );
            }

        } catch (Exception e) {

            System.out.println("❌ Erreur getStudentById : " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public Student getStudentByUserId(int idUsers) {
        String sql = """
            SELECT * FROM students
            WHERE id_users = ?
            LIMIT 1
        """;

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, idUsers);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Student(
                        rs.getInt("id_student"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("ecole"),
                        rs.getString("telephone"),
                        rs.getString("date_entree"),
                        rs.getString("date_sortie"),
                        rs.getInt("id_users")
                );
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur getStudentByUserId : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}