package ch.makery.address;
import java.util.*;
import java.sql.*;
import javafx.stage.Stage;
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
 * Controller to manage the visualization of students
 *
 * @author GroupIHM24
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

    // Reference to mainApp.
    private MainApp mainApp;

    /**
     * The constructor
     * It is called before the initialize() method.
     */
    public EtudiantListe() {
    }

    /**
     * Initializes the Studentlist class.
     * This method is called automatically after the fxml file has been loaded
     */
    @FXML
    private void initialize() {
        // Initializes the student table containing 5 columns.
        prenomColumn.setCellValueFactory(
            cellData -> cellData.getValue().prenomProperty());
        nomColumn.setCellValueFactory(
            cellData -> cellData.getValue().nomProperty());
        anneeDeNaissanceColumn.setCellValueFactory(
            cellData -> cellData.getValue().anneeDeNaissanceProperty().asObject());
        parcoursColumn.setCellValueFactory(
            cellData -> cellData.getValue().parcoursProperty());
        promotionColumn.setCellValueFactory(
            cellData -> cellData.getValue().promotionProperty());

    }

    /**
     * Method handle called when the user presses the add item.
     * It opens a dialog window to add new data from a student.
     */
    @FXML
    private void handleNewEtudiant() {
        // Create a new temporary Etudiant object
        Etudiant tempEtudiant = new Etudiant();

        // Show a dialog box to add the new Etudiant object
        boolean okClicked = mainApp.showAjouterDialog(tempEtudiant);

        // If the "OK" button was clicked in the dialog box
        if (okClicked) {
            // Add the new Etudiant object to the application's list of Etudiants
            mainApp.getEtudiantData().add(tempEtudiant);
        }

        // Connect to the database to update the data
        mainApp.connect();
    }

    /**
     * Methode handle called when the user presses the add item.
     * It opens a dialog window to add new data of a student.
     */
    @FXML
    private void handleEditEtudiant() {
        // Retrieve the selected student from the TableView
        Etudiant selectedEtudiant = etudiantTable.getSelectionModel().getSelectedItem();
        if (selectedEtudiant != null) {
            // Open the edit dialog with the selected student
            boolean okClicked = mainApp.showAjouterDialogModif(selectedEtudiant);
            if (okClicked) {
                Modifier modifier = new Modifier();
                modifier.handleOK();
            }
        } else {
            // No students selected, display an error message
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Aucun étudiant sélectionné");
            alert.setContentText("Veuillez sélectionner un étudiant dans la liste.");
            alert.showAndWait();
        }
        mainApp.connect();
    }

    /**
     * Method calling the main App to add the student table with the data
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        etudiantTable.setItems(mainApp.getEtudiantData());
    }

    /**
     * Method handle called when the user presses the delete button.
     */
    @FXML
    private void handleDeleteEtudiant() {
        int selectedIndex = etudiantTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            // Confirmation of deletion
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Suppression");
            alert.setHeaderText("Confirmation de suppression");
            alert.setContentText("Etes-vous sûr de vouloir supprimer cet étudiant ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Etudiant selectedEtudiant = etudiantTable.getItems().get(selectedIndex);
                // Delete the student from the database
                String sql = "DELETE FROM Etudiant WHERE nom = ? AND prenom = ?";
                try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.dir") + "/sqlite/db/chinook.db");
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, selectedEtudiant.getNom());
                    pstmt.setString(2, selectedEtudiant.getPrenom());
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                // Delete the student from the table view
                etudiantTable.getItems().remove(selectedIndex);
            }
        } else {
            // If nothing is selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Etudiant Selected");
            alert.setContentText("Please select a etudiant in the table.");

            alert.showAndWait();
        }
    }

    /**
     * Method to change the data of the array by a given list
     * @param newList
     */
    public void changeData(ObservableList<Etudiant> newList){
        etudiantTable.setItems(newList);
    }

    /**
     * Method sets the list as the items of etudiantTable.
     * @param newList
     */
    public void setEtudiantData(ObservableList<Etudiant> etudiantData) {
        etudiantTable.setItems(etudiantData);
    }

}