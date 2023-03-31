package ch.makery.address;

 import java.util.*;
import java.sql.*;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Optional;

import ch.makery.address.MainApp;
import ch.makery.address.Etudiant;

/**
 * Controller pour g�rer la visualisation des �tudiants
 *
 * @author 
 */

public class EtudiantListe {
    @FXML
    private TableView<Etudiant> etudiantTable;
    @FXML
    private TableColumn<Etudiant, String> prenomColumn;
    @FXML
    private TableColumn<Etudiant, String> nomColumn;
    @FXML
    private TableColumn<Etudiant, Integer> anneeDeNaissanceColumn;
    @FXML
    private TableColumn<Etudiant, String> promotionColumn;
      @FXML
    private TableColumn<Etudiant, String> parcoursColumn;

    // Reference � la main application.
    private MainApp mainApp;

    /**
     * Le constructeur
     * Il est appel� avant la m�thode initialize().
     */
    public EtudiantListe() {
    }

    /**
     * Initialise la classe Etudiantliste
     * Cette m�thode est appel� automatiquement apr�s que le fichier fxml ait �t� charg�
     */
    @FXML
    private void initialize() {
        // Initialise la table �tudiant contenant 5 colonnes.
        prenomColumn.setCellValueFactory(
                cellData -> cellData.getValue().prenomProperty());
        nomColumn.setCellValueFactory(
                cellData -> cellData.getValue().nomProperty());
        anneeDeNaissanceColumn.setCellValueFactory(
                cellData -> cellData.getValue().anneeDeNaissanceProperty().asObject());
        promotionColumn.setCellValueFactory(
                cellData -> cellData.getValue().promotionProperty());
        parcoursColumn.setCellValueFactory(
                cellData -> cellData.getValue().parcoursProperty());
    }
    /**
     * Methode handle appel�e lorsque l'utilisateur appuie sur l'item ajouter .
     * Elle ouvre une fen�tre de dialogue pour ajouter de nouvelles donn�es d'un �tudiant.
     */
    @FXML
    private void handleNewEtudiant() {
        Etudiant tempEtudiant = new Etudiant();
        boolean okClicked = mainApp.showAjouterDialog(tempEtudiant);
        System.out.println("/");
        if (okClicked) {
            mainApp.getEtudiantData().add(tempEtudiant);
        }
    }

  
    /**
     * Methode appelant la main App pour y ajouter la table �tudiante avec les donn�es
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        etudiantTable.setItems(mainApp.getEtudiantData());
    }

    /**
     * Methode handle appel�e lorsque l'utilisateur appuie sur le bouton effacer.
     */
    @FXML
 private void handleDeleteEtudiant() {
    int selectedIndex = etudiantTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {
        //Confirmation de la suppression
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Suppression");
        alert.setHeaderText("Confirmation de suppression");
        alert.setContentText("Etes-vous sûr de vouloir supprimer cet étudiant ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Etudiant selectedEtudiant = etudiantTable.getItems().get(selectedIndex);
            //supprimer l'étudiant de la base de données
            String sql = "DELETE FROM Etudiant WHERE nom = ? AND prenom = ?";
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/Users/PascalineCoiffure/projetIHM/sqlite/db/chinook.db");
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, selectedEtudiant.getNom());
                pstmt.setString(2, selectedEtudiant.getPrenom());
                pstmt.executeUpdate();
                System.out.println(selectedEtudiant.getPrenom());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            //supprimer l'étudiant de la table view
            etudiantTable.getItems().remove(selectedIndex);
        }
    } else {
        // Si rien n'est sélectionné
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No Etudiant Selected");
        alert.setContentText("Please select a etudiant in the table.");

        alert.showAndWait();
    }
}


  
    /**
     * Methode handle appel�e lorsque l'utilisateur appuie sur le bouton modifier.
     * Elle ouvre une fen�tre de dialogue pour modifier les donn�es d'un �tudiant s�lectionn�.
     */
    @FXML
    private void handleEditEtudiant() {
    int selectedIndex = etudiantTable.getSelectionModel().getSelectedIndex();
    Etudiant selectedEtudiant = etudiantTable.getItems().get(selectedIndex);
    if (selectedEtudiant != null) {
       boolean okClicked = mainApp.showAjouterDialogModif(selectedEtudiant);
        if (okClicked) {
            
            // Open a connection to the SQLite database
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db")) {
                // Prepare an SQL UPDATE statement to update the selected student
                PreparedStatement stmt = conn.prepareStatement("UPDATE etudiant SET nom=?, prenom=?, anneeDeNaissance=?, promotion=?, parcours=? WHERE nom = ?, prenom = ?");
                stmt.setString(1, selectedEtudiant.getNom());
                stmt.setString(2, selectedEtudiant.getPrenom());
                stmt.setInt(3, selectedEtudiant.getAnneeDeNaissance());
                stmt.setString(4, selectedEtudiant.getPromotion());
                stmt.setString(5, selectedEtudiant.getParcours());
                stmt.setString(6, selectedEtudiant.getNom());
                stmt.setString(7, selectedEtudiant.getPrenom());
                System.out.println(selectedEtudiant.getNom());
                System.out.println(selectedEtudiant.getPrenom());
                System.out.println("ok");
                // Execute the UPDATE statement
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("non ok");
            }

            // Refresh the table view with the updated data
            etudiantTable.refresh();
        }
    } else {
        // Nothing selected.
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No Etudiant Selected");
        alert.setContentText("Please select a etudiant in the table.");

        alert.showAndWait();
    }
    
}


    /**
     * Methode permettant de changer les donn�es du tableau par une liste donn�e
     * @param newList
     */
    public void changeData(ObservableList<Etudiant> newList){
        etudiantTable.setItems(newList);
    }
    
    public void setEtudiantData(ObservableList<Etudiant> etudiantData) {
    etudiantTable.setItems(etudiantData);
}

}