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
 * Controller to manage the add-on panel
 *
 * @author GroupIHM24
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
     * Initializes the StudentListController class.
     * This method is called automatically after the fxml file has been loaded
     */
    @FXML
    private void initialize() {
    }

    /**
     * Set the stage for this dialog box.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the student that will be modified in the window.
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
     * Returns true if the user clicks on OK, otherwise it returns false.
     *
     * @return okClicked
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Method called when the user clicks on OK.
     */
    @FXML
    private void handleOk() {
        Connection conn = null;
        PreparedStatement stmt = null;
        // Close the pop-up window
        dialogStage.close();
        if (!isInputValid()) {
            return; // the entries are not valid, so do not execute the SQL query
        }
        else {
            try {
                // Establish the connection to the SQLite database
                String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/sqlite/db/chinook.db";
                conn = DriverManager.getConnection(url);

                // Prepare the SQL statement to insert the student's data
                String sql = "INSERT INTO etudiant (Nom, Prenom, Naissance, Parcours, Promotion) VALUES (?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);

                // Add the parameters of the SQL statement with the student's data
                stmt.setString(1, nomField.getText());
                stmt.setString(2, prenomField.getText());
                stmt.setInt(3,Integer.parseInt(anneeDeNaissanceField.getText()));
                stmt.setString(4, parcoursField.getText());
                stmt.setString(5, promotionField.getText());


                // Execute the SQL statement
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
     * Method called when the user clicks on Cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Method that validates the data entered in the fields.
     *
     * @return true if the entry is valid
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
            // Try to change the year of birth to an integer.
            try {
                Integer.parseInt(anneeDeNaissanceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Annee de Naissance non valide (il doit etre un entier a 4 chiffres) !\n";
            }
        }

        if (promotionField.getText() == null || promotionField.getText().length() == 0 || (promotionField.getText().matches("M1") == false && promotionField.getText().matches("M2") == false)) {
            errorMessage += "Promotion non valide!\n La promotion doit etre M1 ou M2 \n";
        }
        if (parcoursField.getText() == null || parcoursField.getText().length() == 0 || (parcoursField.getText().matches("GPHY") == false && parcoursField.getText().matches("GCELL") == false && parcoursField.getText().matches("ECMPS") == false)) {
            errorMessage += "Parcours non valide!\n Le parcours doit etre GPHY, GCELL ou ECMPS \n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Entrées non valides");
            alert.setHeaderText(" Corriger les données invalides, s'il vous plait");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
