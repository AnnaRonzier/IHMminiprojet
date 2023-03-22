package ch.makery.address;

 

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private TextField promotionField;
     @FXML
    private TextField parcoursField;


    private Stage dialogStage;
    private Etudiant etudiant;
    private boolean okClicked = false;


    /**
     * Initialise la classe EtudiantOverviewController.
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

        prenomField.setText(etudiant.getPrenom());
        nomField.setText(etudiant.getNom());
        anneeDeNaissanceField.setText(Integer.toString(etudiant.getAnneeDeNaissance()));
        promotionField.setText(etudiant.getPromotion());
        parcoursField.setText(etudiant.getParcours());
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
        if (isInputValid()) {
            etudiant.setPrenom(prenomField.getText());
            etudiant.setNom(nomField.getText());
            etudiant.setAnneeDeNaissance(Integer.parseInt(anneeDeNaissanceField.getText()));
            etudiant.setPromotion(promotionField.getText());
            etudiant.setParcours(parcoursField.getText());

            okClicked = true;
            dialogStage.close();
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
            errorMessage += "Pr�nom non valide!\n Il ne doit pas contenir d'accent ou de caract�re sp�ciaux \n ";
        }
        if (nomField.getText() == null || nomField.getText().length() == 0 || (nomField.getText().toUpperCase().matches("[A-Z]+") == false) || nomField.getText().toUpperCase().matches(".*\\d+.*")) {
            errorMessage += "Nom non valide!\n Il ne doit pas contenir d'accent ou de caract�re sp�ciaux \n";
        }

        if (anneeDeNaissanceField.getText() == null || anneeDeNaissanceField.getText().length() == 0 || anneeDeNaissanceField.getText().length() < 4 || anneeDeNaissanceField.getText().length() > 4) {
            errorMessage += "Ann�e de naissance non valide!\n Il doit �tre un entier � 4 chiffres \n";
        } else {
            // Essaye de changer l'ann�e de naissance en un entier.
            try {
                Integer.parseInt(anneeDeNaissanceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Annee de Naissance non valide (il doit �tre un entier � 4 chiffres) !\n";
            }
        }

        if (promotionField.getText() == null || promotionField.getText().length() == 0 || (promotionField.getText().matches("M1") == false && promotionField.getText().matches("M2") == false)) {
            errorMessage += "Promotion non valide!\n La promotion doit �tre M1 ou M2 \n";
        }
        if (parcoursField.getText() == null || parcoursField.getText().length() == 0 || (parcoursField.getText().matches("GPHY") == false && promotionField.getText().matches("GCELL") == false && promotionField.getText().matches("ECPMPS") == false)) {
            errorMessage += "Promotion non valide!\n La promotion doit �tre GPHY, GCELL ou ECMPS \n";
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
