package ch.makery.address;


import ch.makery.address.MainApp;
import ch.makery.address.Etudiant;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller pour g�rer le panneau de visualisation
 *
 * @author 
 */
public class Acceuil {

    // Reference � mainApp.
    private MainApp mainApp;

    //Table des �tudiants
    @FXML
    private TableView<Etudiant> personTable;
    @FXML
    private TableColumn<Etudiant, String> prenomColumn, nomColumn, promotionColumn, parcoursColumn;
    @FXML
    private TableColumn<Etudiant, Integer>  anneeDeNaissanceColumn;

    //id de l'item list dans le menu
    @FXML private Menu listMenu;


    /**
     * Methode appelant la mainApp
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }

    /**
     * Initialise la classe EtudiantlisteController.
     * Cette m�thode est appel� automatiquement apr�s que le fichier fxml ait �t� charg�
     */
    @FXML
    private void initialize() {
    }

  

    /**
     * Methode handle appel�e lorsque l'utilisateur appuie sur l'item Liste > Tous.
     * Elle permet de mettre � jour le tableau en affichant tous les �tudiants enregistr�s.
     */
    @FXML
    private void handleListeTous() {
        mainApp.changeData(mainApp.getEtudiantData());
    }

    /**
     * Methode handle appel�e lorsque l'utilisateur appuie sur l'item Liste > M1.
     * Elle permet de mettre � jour le tableau en affichant tous les �tudiants enregistr�s faisant partie de la promotion M1.
     */
    @FXML
    private void handleListeM1() {
        mainApp.changeData(mainApp.getM1Data());
    }

    /**
     * Methode handle appel�e lorsque l'utilisateur appuie sur l'item Liste > M2.
     * Elle permet de mettre � jour le tableau en affichant tous les �tudiants enregistr�s faisant partie de la promotion M2.
     */
    @FXML
    private void handleListeM2() {
        mainApp.changeData(mainApp.getM2Data());
    }
    /**
     * Methode handle appel�e lorsque l'utilisateur appuie sur l'item Liste > GPHY.
     * Elle permet de mettre � jour le tableau en affichant tous les �tudiants enregistr�s faisant partie du parcours GPHY.
     */
    @FXML
    private void handleListeGPHY() {
        mainApp.changeData(mainApp.getGPHYData());
    }
    /**
     * Methode handle appel�e lorsque l'utilisateur appuie sur l'item Liste > GCELL.
     * Elle permet de mettre � jour le tableau en affichant tous les �tudiants enregistr�s faisant partie du parcours GCELL.
     */
    @FXML
    private void handleListeGCELL() {
        mainApp.changeData(mainApp.getGCELLData());
    }
    /**
     * Methode handle appel�e lorsque l'utilisateur appuie sur l'item Liste > ECMPS.
     * Elle permet de mettre � jour le tableau en affichant tous les �tudiants enregistr�s faisant partie du parcours ECMPS.
     */
    @FXML
    private void handleListeECMPS() {
        mainApp.changeData(mainApp.getECMPSData());
    }
}

