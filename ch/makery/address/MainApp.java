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



/**
 * Classe Main application
 * Elle permet de lancer l'application
 *
 * @author 
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    EtudiantListe controllerPO;
    /**
     * Les donnees sont stockees dans differentes listes
     */
    //liste principale
    private ObservableList<Etudiant> etudiantData = FXCollections.observableArrayList();
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



    /**
     * Constructeur
     */
    public MainApp() {

    }
    

    /**
     * M�thode qui retourne les donn�es du tableau en une liste observable
     * @return etudiantData
     */
    public ObservableList<Etudiant> getEtudiantData() {
        return etudiantData;
    }


    /**
     * M�thode qui filtre les etudiants de la promotion ,
     * les stocke dans une liste
     * et retourne les donnees en une liste observable
     * @return listM1
     */
    public ObservableList<Etudiant> getM1Data() {
        for (Etudiant p : etudiantData){
            if(p.getPromotion() == "M1"){
                listM1.add(p);
            }
        }
        return listM1;
    }

    /**
     * M�thode qui filtre les etudiants de la promotion ,
     * les stocke dans une liste
     * et retourne les donnees en une liste observable
     * @return listM2
     */
    public ObservableList<Etudiant> getM2Data() {
        for (Etudiant p : etudiantData){
            if(p.getPromotion() == "M2"){
                listM2.add(p);
            }
        }
        return listM2;
    }
  /**
     * M�thode qui filtre les etudiants de la promotion ,
     * les stocke dans une liste
     * et retourne les donnees en une liste observable
     * @return listGPHY
     */
    public ObservableList<Etudiant> getGPHYData() {
        for (Etudiant p : etudiantData){
            if(p.getParcours() == "GPHY"){
                listGPHY.add(p);
            }
        }
        return listGPHY;
    }

    /**
     * M�thode qui filtre les etudiants de la promotion ,
     * les stocke dans une liste
     * et retourne les donnees en une liste observable
     * @return listGCELL
     */
    public ObservableList<Etudiant> getGCELLData() {
        for (Etudiant p : etudiantData){
            if(p.getParcours() == "GCELL"){
                listGCELL.add(p);
            }
        }
        return listGCELL;
    }

    /**
     * M�thode qui filtre les etudiants de la promotion ,
     * les stocke dans une liste
     * et retourne les donnees en une liste observable
     * @return listECMPS
     */
    public ObservableList<Etudiant> getECMPSData() {
        for (Etudiant p : etudiantData){
            if(p.getParcours() == "ECMPS"){
                listECMPS.add(p);
            }
        }
        return listECMPS;
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestion des Etudiants IDLS");
        this.primaryStage.getIcons().add(new Image("/ressources/SFA.png"));


        initAcceuil();

        showEtudiantListe();
    }

    /**
     * Initialise le root layout.
     */
    public void initAcceuil() {
        try {
            // Charge le root layout venant du fichier fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Acceuil2.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Affiche la scene contenant le root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

         // Donne � l'application main l'acc�s au controller.
            Acceuil controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche la liste des �tudiants dans le root layout.
     */
    public void showEtudiantListe() {
        try {
            // Charge le fichier �tudiant liste.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("EtudiantListe.fxml"));
            AnchorPane etudiantListe = (AnchorPane) loader.load();

            // Set etudiant liste au centre du root layout.
            rootLayout.setCenter(etudiantListe);

            // Donne � l'application main l'acc�s au controller.
            controllerPO = loader.getController();
            controllerPO.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retoune le main stage.
     * @return primaryStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
         String url = "jdbc:sqlite:/path/to/your/database.db";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // exécuter le script de création de la table
            stmt.execute("CREATE TABLE Etudiants (id INTEGER PRIMARY KEY, nom TEXT, prenom TEXT, promotion TEXT, parcours TEXT)");
            System.out.println("Table Etudiants créée avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ouvre une fen�tre de dialogue pour �diter les donn�es pour un �tudiant s�lectionner
     * Si l'utilisateur clique sur OK, le changement sera sauvegarder dans l'objet �tudiant fourni
     * Le bool�en true est retroun�.
     *
     * @param etudiant l'objet �tudiant qui doit �tre �dit�
     * @return true si l'utilisateur clique sur OK sinon il retourne false.
     */
    public boolean showAjouterDialog(Etudiant etudiant) {

        try {
   
            // Charge le fichier fxml et cr�� un nouveau stage pour la fen�tre de dialogue popup.
            FXMLLoader loader = new FXMLLoader();
    
            loader.setLocation(MainApp.class.getResource("Ajouter2.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            // Cr�� la fen�tre de dialogue Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editer Etudiant");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            // Set l'etudiant dans le controller.
            Ajouter controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setEtudiant(etudiant);
            // Affiche la fen�tre de dialogue et attends jusqu'� ce que l'utilisateur la ferme.
            dialogStage.showAndWait();
   
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Methode permettant de changer les donn�es du tableau par une liste donn�e
     * @param liste
     */
    public void changeData(ObservableList<Etudiant> liste){
        controllerPO.changeData(liste);
    }
}
