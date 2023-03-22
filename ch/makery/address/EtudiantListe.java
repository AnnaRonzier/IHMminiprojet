package ch.makery.address;

 

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
     * Initialise la classe EtudiantoverviewController.
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
            alert.setTitle("Suppresion");
            alert.setHeaderText("Confirmation de suppression");
            alert.setContentText("Etes-vous s�r de vouloir supprimer cet �tudiant ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                etudiantTable.getItems().remove(selectedIndex);
            }
        } else {
            // Si rien n'est s�lectionn�
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
        Etudiant selectedEtudiant = etudiantTable.getSelectionModel().getSelectedItem();
        if (selectedEtudiant != null) {
            @SuppressWarnings("unused")
            boolean okClicked = mainApp.showEtudiantEditDialog(selectedEtudiant);
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
}