package ch.makery.address;

 import ch.makery.address.MainApp;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

import ch.makery.address.Etudiant;

/**
 * Controller pour g�rer le panneau d'�dition
 *
 * @author 
 */
public class Ajouter {

   
    @FXML
    private TextField prenomField;
    @FXML
    private TextField nomField;
    @FXML
    private TextField anneeDeNaissanceField;
    @FXML
    private TextField parcoursField;
    @FXML
    private TextField promotionField;
  

private MainApp mainApp;
    private Stage dialogStage;
    private Etudiant etudiant;
    private boolean okClicked = false;
 @FXML
    private TableView<Etudiant> etudiantTable;

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
     * M�thode appel�e lorsque l'utilisateur clique sur OK.
     */
    @FXML
    private void handleOk() {
    Connection conn = null;
    PreparedStatement stmt = null;
    // Fermer la fenêtre pop-up
    dialogStage.close();
if (!isInputValid()) {
return; // les entrées ne sont pas valides, donc ne pas exécuter la requête SQL
}
else {
    try {
        // Établir la connexion à la base de données SQLite
       String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/sqlite/db/chinook.db";
 conn = DriverManager.getConnection(url);

        // Préparer l'instruction SQL pour insérer les données de l'étudiant
        String sql = "INSERT INTO etudiant (Nom, Prenom, Naissance, Parcours, Promotion) VALUES (?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(sql);


        // Ajouter les paramètres de l'instruction SQL avec les données de l'étudiant
        stmt.setString(1, nomField.getText());
        stmt.setString(2, prenomField.getText());
        stmt.setInt(3,Integer.parseInt(anneeDeNaissanceField.getText()));
        stmt.setString(4, promotionField.getText());
        stmt.setString(5, parcoursField.getText());
        

        // Exécuter l'instruction SQL
        stmt.executeUpdate();

        System.out.println("Les données de l'étudiant ont été ajoutées à la base de données.");
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    } finally {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
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
            errorMessage += "Parcours non valide!\n Le parcours doit etre GPHY, GCELL ou ECMPS \n";
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
