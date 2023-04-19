package ch.makery.address;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.io.IOException;
import ch.makery.address.Etudiant;
import ch.makery.address.Ajouter;
import ch.makery.address.Acceuil;
import ch.makery.address.Modifier;
import ch.makery.address.EtudiantListe;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;

/**
 * Classe Main application
 * It allows to launch the application
 *
 * @GroupIHM24
 */
public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    EtudiantListe controllerPO;
    /**
     * Les donnees sont stockees dans differentes listes
     */
    @FXML
    private TableView<Etudiant> etudiantTable;
    //liste principale
    public ObservableList<Etudiant> etudiantData = FXCollections.observableArrayList();
    //Liste des M1
    private ObservableList<Etudiant> listM1 = FXCollections.observableArrayList();
    //Liste des M2
    private ObservableList<Etudiant> listM2 = FXCollections.observableArrayList();

    //Liste des GPHY
    private ObservableList<Etudiant> listGPHY = FXCollections.observableArrayList();
    //Liste des GCELL
    private ObservableList<Etudiant> listGCELL = FXCollections.observableArrayList();
    //Liste des ECMPS
    private ObservableList<Etudiant> listECMPS = FXCollections.observableArrayList();

    private Stage dialogStageM;

    /**
     * Constructor
     */
    public MainApp() {

    }

    /**
     * Method that returns the data of the table in an observable list
     * @return etudiantData
     */
    public ObservableList<Etudiant> getEtudiantData() {
        return etudiantData;
    }

    /**
     * Method that filters the students in the class,
     * stores them in a list
     * and returns the data in an observable list
     * @return listM1
     */
    public ObservableList<Etudiant> getM1Data() {
        ObservableList<Etudiant> listM1 = FXCollections.observableArrayList();
        for (Etudiant p : etudiantData){
            if(p.getPromotion().equals("M1")){
                listM1.add(p);
            }
        }
        return listM1;
    }

    /**
     * Method that filters the students in the class,
     * stores them in a list
     * and returns the data in an observable list
     * @Param option1
     * @return listD
     */
    public ObservableList<Etudiant> getParcData(String option1) {
        ObservableList<Etudiant> listParc = FXCollections.observableArrayList();
        for (Etudiant p : etudiantData){
            if(p.getPromotion().equals(option1)){
                listParc.add(p);
            }
        }
        return listParc;
    }

    /**
     * Method that filters the students in the class,
     * stores them in a list
     * and returns the data in an observable list
     * @Param option2
     * @return listPromData
     */
    public ObservableList<Etudiant> getPromData(String option2) {
        ObservableList<Etudiant> listProm = FXCollections.observableArrayList();
        for (Etudiant p : etudiantData){
            if(p.getParcours().equals(option2)){
                listProm.add(p);
            }
        }
        return listProm;
    }

    /**
     * This function returns an ObservableList of Etudiant objects
     * @Param promotion, parcours
     * @return ObservableList<Etudiant>
     */
    public ObservableList<Etudiant> getData(String promotion, String parcours) {
        // Create a new ObservableList to store the filtered data.
        ObservableList<Etudiant> filteredData = FXCollections.observableArrayList();
        // Iterate through each Etudiant object in the etudiantData list.
        for (Etudiant etudiant : etudiantData) {
            // Check if the Etudiant object meets both criteria.
            if (etudiant.getPromotion().equals(promotion) && etudiant.getParcours().equals(parcours)) {
                filteredData.add(etudiant);
            }
            // Check if the Etudiant object only meets the promotion criterion.
            else if (etudiant.getPromotion().equals(promotion)) {
                filteredData.add(etudiant);
            }
            // Check if the Etudiant object only meets the parcours criterion.
            else if (etudiant.getParcours().equals(parcours)) {
                filteredData.add(etudiant);
            }
        }

        // Return the filtered data.
        return filteredData;
    }
    /**
     * This function  is responsible for initializing the 
     * user interface and showing the list of Etudiants.
     * @Param primaryStage
     */

    @Override
    public void start(Stage primaryStage) {
        // Set the title and icon of the primary stage.
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestion des Etudiants IDLS");
        this.primaryStage.getIcons().add(new Image("/ressources/SFA.png"));

        // Connect to the database.
        connect();

        // Initialize the Acceuil view.
        initAcceuil();

        // Show the list of Etudiants.
        showEtudiantListe();

    }

    /**
     * This function is responsible for initializing the Acceuil view, which is loaded from an FXML file.
     */
    public void initAcceuil() {
        try {
            // Load the root layout from the FXML file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Acceuil2.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Set the scene to display the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the main application access to the controller.
            Acceuil controller = loader.getController();
            controller.setMainApp(this);

            // Show the primary stage.
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This function is responsible for showing the list of Etudiants in the user interface.
     */
    public void showEtudiantListe() {
        try {
            // Load the FXML file for the EtudiantListe view.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("EtudiantListe.fxml"));
            AnchorPane etudiantListe = (AnchorPane) loader.load();
            // Set the EtudiantListe view to be the center of the root layout.
            rootLayout.setCenter(etudiantListe);

            // Give the main application access to the controller and the Etudiant data.
            controllerPO = loader.getController();
            controllerPO.setMainApp(this);
            controllerPO.setEtudiantData(etudiantData);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Return the main stage.
     * @return primaryStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * This function is responsible for connecting to the SQLite database 
     * and populating the etudiantData list with the data retrieved from the database.
     */
    public void connect() {
        // Clear the etudiantData list before populating it with new data.
        etudiantData.clear();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Define the URL of the SQLite database.
            String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/sqlite/db/chinook.db";

            // Create a connection to the database.
            conn = DriverManager.getConnection(url);

            // Display a message indicating that the connection to the database has been established.
            System.out.println("Connection to SQLite has been established.");

            // Create an SQL statement to select the columns from the Etudiant table.
            stmt = conn.createStatement();

            // Execute the SQL query and obtain a result set.
            rs = stmt.executeQuery("SELECT Nom, Prenom, Naissance, Parcours, Promotion FROM Etudiant");

            // Loop through the results of the query.
            while (rs.next()) {
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");
                int anneeDeNaissance = rs.getInt("Naissance");
                String parcours = rs.getString("Parcours");
                String promotion = rs.getString("Promotion");

                // Create a new Etudiant object with the retrieved information.
                Etudiant nouvelEtudiant = new Etudiant(nom, prenom, anneeDeNaissance, parcours, promotion);

                // Add the new Etudiant object to the etudiantData list.
                etudiantData.add(nouvelEtudiant);
            }

        } catch (SQLException e) {
            // If an exception occurs, display the error message.
            System.out.println(e.getMessage());
        } finally {
            try {
                // Close the connection to the database.
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
    /**
     * Opens a dialog window to edit the data for a selected Student
     * If the user clicks OK the change will be saved to the provided student object
     * The true boolean is found.
     *
     * @param student the student object to be edited
     * @return true if the user clicks OK otherwise it returns false.
     */
    public boolean showAjouterDialog(Etudiant etudiant) {

        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(MainApp.class.getResource("Ajouter2.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            // Created the Stage dialog box.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editer Etudiant");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            // Set the student in the controller.
            Ajouter controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setEtudiant(etudiant);
            // Displays the dialog box and waits until the user closes it.
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Opens a dialog window to edit the data for a selected Student
     * If the user clicks OK the change will be saved to the provided student object
     * The true boolean is found.
     *
     * @param student the student object to be edited
     * @return true if the user clicks OK otherwise it returns false.
     */
    public boolean showAjouterDialogModif(Etudiant etudiant) {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(MainApp.class.getResource("Modifier.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            // Created the Stage dialog box.
            Stage dialogStageM= new Stage();
            dialogStageM.setTitle("Editer Etudiant");
            dialogStageM.initModality(Modality.WINDOW_MODAL);
            dialogStageM.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStageM.setScene(scene);
            // Set the student in the controller.
            Modifier controller = loader.getController();
            controller.setDialogStage(dialogStageM);
            controller.setEtudiant(etudiant);
            // Displays the dialog box and waits until the user closes it.
            dialogStageM.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method to change the data of the table by a given list
     * @param list
     */
    public void changeData(ObservableList<Etudiant> liste){
        controllerPO.changeData(liste);
    }

}
