package ch.makery.address;
import ch.makery.address.MainApp;
import ch.makery.address.Etudiant;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller to manage the visualization panel
 *
 * @author GroupIHM24
 */
public class Acceuil {

    // Reference to mainApp.
    private MainApp mainApp;

    //Students' table
    @FXML
    private TableView<Etudiant> personTable;
    @FXML
    private TableColumn<Etudiant, String> prenomColumn, nomColumn, promotionColumn, parcoursColumn;
    @FXML
    private TableColumn<Etudiant, Integer>  anneeDeNaissanceColumn;

    // Id of the list item in the menu
    @FXML private Menu listMenu;

    /**
     * Method calling the mainApp
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Initialize the StudentListController class.
     * This method is called automatically after the fxml file is loaded
     */
    @FXML
    private void initialize() {
    }

    /**
     * Method handle called when the user presses the List > All item.
     * It allows to update the table with all the registered students.
     */
    @FXML
    private void handleListeTous() {
        mainApp.changeData(mainApp.getEtudiantData());
    }

    /**
     * Method handle called when the user presses the List > M1 item.
     * It allows to update the table by displaying all the registered students who are part of the M1 class.
     */
    @FXML
    private void handleListeM1() {
        String promotion = "M1";
        mainApp.changeData(mainApp.getData(promotion, null));
    }

    /**
     * Methode handle called when the user presses the List > M2 item.
     * It allows to update the table by displaying all the registered students who are part of the M2 class.
     */
    @FXML
    private void handleListeM2() {
        String promotion = "M2";
        mainApp.changeData(mainApp.getData(promotion, null));
    }

    /**
     * Methode handle called when the user presses the List > GPHY item.
     * It allows to update the table by displaying all the registered students who are part of the GPHY pathway.
     */
    @FXML
    private void handleListeGPHY() {
        String parcours = "GPHY";
        mainApp.changeData(mainApp.getData(null, parcours));
    }

    /**
     * Methode handle called when the user presses the List > GCELL item.
     * It allows to update the table by displaying all the registered students who are part of the GCELL pathway.
     */
    @FXML
    private void handleListeGCELL() {
        String parcours = "GCELL";
        mainApp.changeData(mainApp.getData(null, parcours));
    }

    /**
     * Methode handle called when the user presses the List > ECMPS item.
     * It allows to update the table by displaying all the registered students who are part of the ECMPS pathway.
     */
    @FXML
    private void handleListeECMPS() {
        String parcours = "ECMPS";
        mainApp.changeData(mainApp.getData(null, parcours));
    }
}

