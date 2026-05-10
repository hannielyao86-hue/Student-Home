package org.example;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.text.html.ImageView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * Contrôleur principal pour la gestion des chambres d'hôtel
 * Gère l'affichage des chambres, les interactions utilisateur et la mise à jour de l'interface
 */
public class RoomsController implements Initializable {


    @FXML
    private FlowPane roomsContainer;          // Conteneur affichant les cartes des chambres

    @FXML
    private TextField searchField;            // Champ de recherche pour filtrer les chambres

    @FXML
    private Label roomNumberLabel;            // Affiche le numéro de la chambre sélectionnée
    @FXML
    private Label roomStatusLabel;            // Affiche l'état de la chambre
    @FXML
    private Label roomTypeLabel;              // Affiche le type de chambre
    @FXML
    private Label clientNameLabel;            // Affiche le nom du client
    @FXML
    private Label contactLabel;               // Affiche le contact du client
    @FXML
    private Label startDateLabel;             // Affiche la date de début
    @FXML
    private Label endDateLabel;               // Affiche la date de fin
    @FXML
    private Label leaseAmountLabel;           // Affiche le montant du loyer
    @FXML
    private Label occupantsLabel;             // Affiche le nombre d'occupants

    @FXML
    private Button modifyButton;              // Bouton pour modifier une chambre
    @FXML
    private Button generateContractButton;    // Bouton pour générer un contrat

    @FXML
    private ImageView roomImageView;          // Affichage d'une image de la chambre

    // ============= VARIABLES D'INSTANCE =============

    // Liste observable de toutes les chambres (mise à jour automatique de l'interface)
    private ObservableList<Room> rooms;

    // Chambre actuellement sélectionnée
    private Room selectedRoom;

    /**
     * Méthode d'initialisation appelée automatiquement après le chargement du FXML
     * @param url URL de la ressource FXML
     * @param resourceBundle Bundle de ressources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser la liste des chambres
        rooms = FXCollections.observableArrayList();

        // Charger les données de démonstration
        loadSampleData();

        // Afficher les cartes des chambres
        displayRooms();

        // Configurer l'écouteur de recherche
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filterRooms(newVal);
        });

        // Configurer les écouteurs pour les boutons
        modifyButton.setOnAction(event -> modifyRoom());
        generateContractButton.setOnAction(event -> generateContract());
    }

    /**
     * Charge les données d'exemple des chambres
     * Ces données sont fictives et serviront pour la démonstration
     */
    private void loadSampleData() {
        // Chambre A001 - Occupée
        Room room1 = new Room("A001", RoomStatus.FREE, "Single", 1);
        room1.setClientName("Marciel Rennemama");
        room1.setContact("(019) 904 3776");
        room1.setStartDate("19/02/2024");
        room1.setEndDate("03/12/2024");
        room1.setLeaseAmount(3000);
        rooms.add(room1);

        // Chambre A002 - Libre
        Room room2 = new Room("A002", RoomStatus.FREE, "Single", 0);
        rooms.add(room2);

        // Chambre A003 - Occupée
        Room room3 = new Room("A003", RoomStatus.FREE, "Double", 2);
        room3.setClientName("Jean Dupont");
        room3.setContact("(019) 904 1234");
        room3.setStartDate("01/03/2024");
        room3.setEndDate("15/06/2024");
        room3.setLeaseAmount(4500);
        rooms.add(room3);

        // Chambre A004 - Libre
        Room room4 = new Room("A004", RoomStatus.FREE, "Single", 0);
        rooms.add(room4);

        // Chambre A005 - Réservée
        Room room5 = new Room("A005", RoomStatus.RESERVED, "Double", 0);
        room5.setClientName("Marie Nohe");
        room5.setContact("(019) 904 5678");
        room5.setStartDate("10/05/2024");
        room5.setEndDate("25/05/2024");
        room5.setLeaseAmount(3500);
        rooms.add(room5);

        // Chambre A006 - Libre
        Room room6 = new Room("A006", RoomStatus.FREE, "Single", 0);
        rooms.add(room6);

        // Chambre A007 - Occupée
        Room room7 = new Room("A007", RoomStatus.OCCUPIED, "Single", 1);
        room7.setClientName("Pierre Martin");
        room7.setContact("(019) 904 9012");
        room7.setStartDate("15/01/2024");
        room7.setEndDate("20/08/2024");
        room7.setLeaseAmount(2800);
        rooms.add(room7);

        // Chambre A008 - Libre
        Room room8 = new Room("A008", RoomStatus.FREE, "Double", 0);
        rooms.add(room8);

        // Chambre A009 - Libre
        Room room9 = new Room("A009", RoomStatus.FREE, "Double", 0);
        rooms.add(room9);

        // Chambre A010 - Libre
        Room room10 = new Room("A010", RoomStatus.FREE, "Double", 0);
        rooms.add(room10);

        // Chambre A205 - Libre
        Room room11 = new Room("A205", RoomStatus.FREE, "Single", 0);
        rooms.add(room11);

        // Chambre A209 - Réservée
        Room room12 = new Room("A209", RoomStatus.RESERVED, "Double", 0);
        room12.setClientName("Sophie Bernard");
        room12.setContact("(019) 904 3456");
        room12.setStartDate("22/05/2024");
        room12.setEndDate("29/05/2024");
        room12.setLeaseAmount(4200);
        rooms.add(room12);

        // Chambre A201 - Réservée
        Room room13 = new Room("A201", RoomStatus.RESERVED, "Double", 0);
        room13.setClientName("Luc Petit");
        room13.setContact("(019) 904 7890");
        room13.setStartDate("05/04/2024");
        room13.setEndDate("10/04/2024");
        room13.setLeaseAmount(3800);
        rooms.add(room13);

        // Chambre A202 - Réservée
        Room room14 = new Room("A202", RoomStatus.RESERVED, "Double", 0);
        room14.setClientName("Anne Leclerc");
        room14.setContact("(019) 904 2468");
        room14.setStartDate("12/06/2024");
        room14.setEndDate("19/06/2024");
        room14.setLeaseAmount(4100);
        rooms.add(room14);

        // Chambre A203 - Réservée
        Room room15 = new Room("A203", RoomStatus.RESERVED, "Double", 0);
        room15.setClientName("Nathalie Blanc");
        room15.setContact("(019) 904 1357");
        room15.setStartDate("01/07/2024");
        room15.setEndDate("08/07/2024");
        room15.setLeaseAmount(3900);
        rooms.add(room15);

        // Chambre B201 - Libre
        Room room16 = new Room("B201", RoomStatus.FREE, "Double", 0);
        rooms.add(room16);

        // chambre 
      
    }

    /**
     * Affiche toutes les cartes des chambres dans le conteneur
     */
    private void displayRooms() {
        // Effacer les anciennes cartes
        roomsContainer.getChildren().clear();

        // Pour chaque chambre, créer et ajouter une carte
        for (Room room : rooms) {
            roomsContainer.getChildren().add(createRoomCard(room));
        }
    }

    /**
     * Crée une carte visuelle pour une chambre
     * @param room La chambre pour laquelle créer la carte
     * @return VBox contenant la représentation graphique de la chambre
     */
    private VBox createRoomCard(Room room) {
        // Conteneur principal de la carte
        VBox card = new VBox();
        card.setStyle("-fx-border-radius: 10; -fx-spacing: 10; -fx-padding: 15;" +
                "-fx-background-color: " + room.getStatus().getColor() +
                "; -fx-border-color: #cccccc; -fx-cursor: hand;");
        card.setPrefWidth(150);
        card.setPrefHeight(150);

        // Label pour l'état de la chambre
        Label statusLabel = new Label(room.getStatus().getDescription());
        statusLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12;");

        // Label pour le numéro de la chambre
        Label roomNumberLabel = new Label(room.getRoomNumber());
        roomNumberLabel.setStyle("-fx-text-fill: white; -fx-font-size: 32; -fx-font-weight: bold;");

        // Label pour le type de chambre
        Label typeLabel = new Label(room.getType());
        typeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 11;");

        // Ajouter les éléments à la carte
        card.getChildren().addAll(statusLabel, roomNumberLabel, typeLabel);

        // Configurer l'interaction au clic
        card.setOnMouseClicked(event -> {
            selectedRoom = room;
            displayRoomDetails(room);
            // Mettre à jour la sélection visuelle
            updateCardSelection();
        });

        return card;
    }

    /**
     * Affiche les détails de la chambre sélectionnée dans le panneau de droite
     * @param room La chambre dont afficher les détails
     */
    private void displayRoomDetails(Room room) {
        if (room != null) {
            // Remplir les labels avec les informations de la chambre
            roomNumberLabel.setText(room.getRoomNumber());
            roomStatusLabel.setText(room.getStatus().getDescription());
            roomTypeLabel.setText(room.getType());
            occupantsLabel.setText(String.valueOf(room.getOccupants()));
            clientNameLabel.setText(room.getClientName() != null ? room.getClientName() : "-");
            contactLabel.setText(room.getContact() != null ? room.getContact() : "-");
            startDateLabel.setText(room.getStartDate() != null ? room.getStartDate() : "-");
            endDateLabel.setText(room.getEndDate() != null ? room.getEndDate() : "-");
            leaseAmountLabel.setText(room.getLeaseAmount() > 0 ? room.getLeaseAmount() + " €" : "-");
        }
    }

    /**
     * Met à jour la sélection visuelle des cartes (opaque pour sélectionnée, transparent pour les autres)
     */
    private void updateCardSelection() {
        for (javafx.scene.Node node : roomsContainer.getChildren()) {
            if (node instanceof VBox) {
                // Si cette carte représente la chambre sélectionnée
                if (node.equals(roomsContainer.getChildren().stream()
                        .filter(n -> {
                            // Vérifier si c'est la bonne carte
                            return true; // Logique simplifiée pour cet exemple
                        }).findFirst().orElse(null))) {
                    node.setStyle(node.getStyle() + "; -fx-opacity: 1;");
                }
            }
        }
    }

    /**
     * Filtre les chambres selon le texte de recherche
     * @param searchText Le texte de recherche entré par l'utilisateur
     */
    private void filterRooms(String searchText) {
        roomsContainer.getChildren().clear();

        if (searchText == null || searchText.trim().isEmpty()) {
            // Si le champ est vide, afficher toutes les chambres
            for (Room room : rooms) {
                roomsContainer.getChildren().add(createRoomCard(room));
            }
        } else {
            // Sinon, afficher uniquement les chambres correspondant au texte
            String lowerSearchText = searchText.toLowerCase();
            for (Room room : rooms) {
                if (room.getRoomNumber().toLowerCase().contains(lowerSearchText) ||
                    (room.getClientName() != null && room.getClientName().toLowerCase().contains(lowerSearchText))) {
                    roomsContainer.getChildren().add(createRoomCard(room));
                }
            }
        }
    }

    /**
     * Gère la modification d'une chambre (appelée au clic sur le bouton Modifier)
     */
    private void modifyRoom() {
        if (selectedRoom != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification");
            alert.setHeaderText("Modifier la chambre");
            alert.setContentText("Modification de la chambre " + selectedRoom.getRoomNumber() +
                    "\n(Fonctionnalité à développer)");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Aucune sélection");
            alert.setContentText("Veuillez sélectionner une chambre d'abord.");
            alert.showAndWait();
        }
    }

    /**
     * Gère la génération de contrat pour une chambre (appelée au clic sur le bouton)
     */
    private void generateContract() {
        if (selectedRoom != null && selectedRoom.getStatus() != RoomStatus.FREE) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Contrat");
            alert.setHeaderText("Générer le contrat");
            alert.setContentText("Génération du contrat pour " + selectedRoom.getRoomNumber() +
                    " - Client: " + selectedRoom.getClientName() +
                    "\n(Fonctionnalité à développer)");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Chambre non disponible");
            alert.setContentText("Impossible de générer un contrat pour une chambre libre.");
            alert.showAndWait();
        }
    }
}
