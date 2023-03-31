package ch.makery.address;
 
import java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.*;

import ch.makery.address.Etudiant;

/**
 * Décrivez votre classe Modifier ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Modifier
{
     @FXML
    private TableView<Etudiant> etudiantTable;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField anneeDeNaissanceField;
    @FXML
    private TextField parcoursField;
    @FXML
    private TextField promotionField;
    
// Reference � la main application.
    private MainApp mainApp;

    private Stage dialogStage;
    private Etudiant etudiant;
    private boolean okClicked = false;


    /**
     * Initialise la classe EtudiantlisteController.
     * Cette m�thode est appel� automatiquement apr�s que le fichier fxml ait �t� charg�
     */
    @FXML
    private void initialize() {
    }

    /**
     * Set le stage de cette fen�tre de dialogue.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets l'�tudiant qui va �tre modifier dans la fen�tre.
     *
     * @param etudiant
     */
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
        nomField.setText(etudiant.getNom());
        prenomField.setText(etudiant.getPrenom());
        anneeDeNaissanceField.setText(Integer.toString(etudiant.getAnneeDeNaissance()));
        parcoursField.setText(etudiant.getParcours());
        promotionField.setText(etudiant.getPromotion());
        
    }

    /**
     * Retourne true si l'utilisateur clique sur OK, sinon elle retourne false.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

     /**
     * Methode handle appel�e lorsque l'utilisateur appuie sur le bouton modifier.
     * Elle ouvre une fen�tre de dialogue pour modifier les donn�es d'un �tudiant s�lectionn�.
     */
    @FXML
    public void handleOK() {
        Connection conn = null;
    
        // Fermer la fenêtre pop-up
    dialogStage.close();

            
            // Open a connection to the SQLite database
            try  {
                // Établir la connexion à la base de données SQLite
        String url = "jdbc:sqlite:/Users/PascalineCoiffure/projetIHM/sqlite/db/chinook.db";
        conn = DriverManager.getConnection(url);
        
                // Prepare an SQL UPDATE statement to update the selected student
PreparedStatement stmt = conn.prepareStatement("UPDATE etudiant SET Nom=?, Prenom=?, Naissance=?, Parcours=?, Promotion=? WHERE nom = ? AND prenom = ?");
                stmt.setString(1, nomField.getText());
                stmt.setString(2, prenomField.getText());
        stmt.setInt(3,Integer.parseInt(anneeDeNaissanceField.getText()));
            stmt.setString(4, parcoursField.getText());
        stmt.setString(5, promotionField.getText());
    
        stmt.setString(6, nomField.getText());
        stmt.setString(7, prenomField.getText());
     
                // Execute the UPDATE statement
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

          
    
    //} else {
        // Nothing selected.
        //Alert alert = new Alert(AlertType.WARNING);
        //alert.initOwner(mainApp.getPrimaryStage());
        //alert.setTitle("No Selection");
        //alert.setHeaderText("No Etudiant Selected");
        //alert.setContentText("Please select a etudiant in the table.");

        //alert.showAndWait();
    //}
    
//}
}

    /**
     * M�thode appel�e lorsque l'utilisateur clique sur Cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * M�thode qui valide les donn�es entr�es dans les fields.
     *
     * @return true si l'entr�e est valide
     */
    private boolean isInputValid() {
        String errorMessage = "";

        
        if (prenomField.getText() == null || prenomField.getText().length() == 0 || (prenomField.getText().toUpperCase().matches("[A-Z]+") == false) || prenomField.getText().toUpperCase().matches(".*\\d+.*")) {
            errorMessage += "Prenom non valide!\n Il ne doit pas contenir d'accent ou de caractere speciaux \n ";
        }
        if (nomField.getText() == null || nomField.getText().length() == 0 || (nomField.getText().toUpperCase().matches("[A-Z]+") == false) || nomField.getText().toUpperCase().matches(".*\\d+.*")) {
            errorMessage += "Nom non valide!\n Il ne doit pas contenir d'accent ou de caractere speciaux \n";
        }

        if (anneeDeNaissanceField.getText() == null || anneeDeNaissanceField.getText().length() == 0 || anneeDeNaissanceField.getText().length() < 4 || anneeDeNaissanceField.getText().length() > 4) {
            errorMessage += "Annee de naissance non valide!\n Il doit etre un entier a 4 chiffres \n";
        } else {
            // Essaye de changer l'ann�e de naissance en un entier.
            try {
                Integer.parseInt(anneeDeNaissanceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Annee de Naissance non valide (il doit etre un entier a 4 chiffres) !\n";
            }
        }

        if (promotionField.getText() == null || promotionField.getText().length() == 0 || (promotionField.getText().matches("M1") == false && promotionField.getText().matches("M2") == false)) {
            errorMessage += "Promotion non valide!\n La promotion doit etre M1 ou M2 \n";
        }
        if (parcoursField.getText() == null || parcoursField.getText().length() == 0 || (parcoursField.getText().matches("GPHY") == false && parcoursField.getText().matches("GCELL") == false && parcoursField.getText().matches("ECPMPS") == false)) {
            errorMessage += "Promotion non valide!\n La promotion doit etre GPHY, GCELL ou ECMPS \n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Fields non valides");
            alert.setHeaderText(" Corriger les fields invalides, s'il vous plait");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
