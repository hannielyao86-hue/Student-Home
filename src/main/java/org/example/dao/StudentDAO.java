package org.example.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.config.DatabaseConnection;
import org.example.model.Student;

public class StudentDAO {

    private final Connection cnx =
            DatabaseConnection.getInstance().getConnection();

    // ================= CREATE =================
    public void createStudent(Student s) {

        String sql = """
            INSERT INTO students
            (numero_etudiant, ecole, telephone, date_entree, date_sortie, id_users)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, s.getNumeroEtudiant());
            ps.setString(2, s.getEcole());
            ps.setString(3, s.getTelephone());
            ps.setString(4, s.getDateEntree());
            ps.setString(5, s.getDateSortie());

            // IMPORTANT: éviter null FK
            ps.setInt(6, s.getIdUsers() > 0 ? s.getIdUsers() : 1);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("createStudent error: " + e.getMessage());
        }
    }

    // ================= READ =================
    public List<Student> getAllStudents() {

        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id_student";

        try (PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                list.add(new Student(
                        rs.getInt("id_student"),
                        rs.getString("numero_etudiant"),
                        rs.getString("ecole"),
                        rs.getString("telephone"),
                        rs.getString("date_entree"),
                        rs.getString("date_sortie"),
                        rs.getInt("id_users")
                ));
            }

        } catch (Exception e) {
            System.out.println("getAllStudents error: " + e.getMessage());
        }

        return list;
    }

    // ================= UPDATE (IMPORTANT MANQUANT CHEZ TOI) =================
    public void updateStudent(Student s) {

        String sql = """
            UPDATE students
            SET numero_etudiant=?, ecole=?, telephone=?,
                date_entree=?, date_sortie=?, id_users=?
            WHERE id_student=?
        """;

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, s.getNumeroEtudiant());
            ps.setString(2, s.getEcole());
            ps.setString(3, s.getTelephone());
            ps.setString(4, s.getDateEntree());
            ps.setString(5, s.getDateSortie());
            ps.setInt(6, s.getIdUsers() > 0 ? s.getIdUsers() : 1);
            ps.setInt(7, s.getIdStudent());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("updateStudent error: " + e.getMessage());
        }
    }

    // ================= DELETE =================
    public void deleteStudent(int id) {

        String sql = "DELETE FROM students WHERE id_student=?";

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("deleteStudent error: " + e.getMessage());
        }
    }

    // ================= COUNT =================
    public int countStudents() {

        String sql = "SELECT COUNT(*) FROM students";

        try (PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            System.out.println("countStudents error: " + e.getMessage());
        }

        return 0;
    }
}