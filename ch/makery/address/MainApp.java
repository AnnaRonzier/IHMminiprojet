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
     * Constructeur
     */
    public MainApp() {


    }
    

    /**
     * Methode qui retourne les donn�es du tableau en une liste observable
     * @return etudiantData
     */
    public ObservableList<Etudiant> getEtudiantData() {
        return etudiantData;
    }


    /**
     * Methode qui filtre les etudiants de la promotion ,
     * les stocke dans une liste
     * et retourne les donnees en une liste observable
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
     * Methode qui filtre les etudiants de la promotion ,
     * les stocke dans une liste
     * et retourne les donnees en une liste observable
     * @return listM2
     */
    public ObservableList<Etudiant> getM2Data() {
        ObservableList<Etudiant> listM2 = FXCollections.observableArrayList();
    for (Etudiant p : etudiantData){
        if(p.getPromotion().equals("M2")){
            listM2.add(p);
        }
    }
    return listM2;
    }
  /**
     * Methode qui filtre les etudiants de la promotion ,
     * les stocke dans une liste
     * et retourne les donnees en une liste observable
     * @return listGPHY
     */
    public ObservableList<Etudiant> getGPHYData() {
        ObservableList<Etudiant> listGPHY = FXCollections.observableArrayList();
    for (Etudiant p : etudiantData){
        if(p.getParcours().equals("GPHY")){
            listGPHY.add(p);
        }
    }
    return listGPHY;
    }
 

    /**
     * Methode qui filtre les etudiants de la promotion ,
     * les stocke dans une liste
     * et retourne les donnees en une liste observable
     * @return listGCELL
     */
    public ObservableList<Etudiant> getGCELLData() {
        ObservableList<Etudiant> listGCELL = FXCollections.observableArrayList();
    for (Etudiant p : etudiantData){
        if(p.getParcours().equals("GCELL")){
            listGCELL.add(p);
        }
    }
    return listGCELL;
    }

    /**
     * Methode qui filtre les etudiants de la promotion ,
     * les stocke dans une liste
     * et retourne les donnees en une liste observable
     * @return listECMPS
     */
    public ObservableList<Etudiant> getECMPSData() {
        ObservableList<Etudiant> listECMPS = FXCollections.observableArrayList();
    for (Etudiant p : etudiantData){
        if(p.getParcours().equals("ECMPS")){
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

        connect();
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
     * Affiche la liste des etudiants dans le root layout.
     */
    public void showEtudiantListe() {
        try {

            // Charge le fichier �tudiant liste.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("EtudiantListe.fxml"));
            AnchorPane etudiantListe = (AnchorPane) loader.load();

            // Set etudiant liste au centre du root layout.
            rootLayout.setCenter(etudiantListe);

            controllerPO = loader.getController();
            controllerPO.setMainApp(this);
            controllerPO.setEtudiantData(etudiantData);

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

        

    

 public  void connect() {
    etudiantData.clear();
     Connection conn = null;
      Statement stmt = null;
      ResultSet rs = null;
        
        try {
            
            // db parameters
           String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/sqlite/db/chinook.db";




           // String url = "jdbc:sqlite:/Users/thomastessier/Desktop/GestionEtudiantsFinal-copy-copy2/sqlite/db/chinook.db";

      



            
            ///
          // String url = "jdbc:sqlite:/Users/PascalineCoiffure/projetIHM/sqlite/db/chinook.db";


            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
             // Créer une instruction SQL pour sélectionner les colonnes de la table Etudiant
         stmt = conn.createStatement();



         rs = stmt.executeQuery("SELECT Nom, Prenom, Naissance, Parcours, Promotion FROM Etudiant");



         // Parcourir les résultats de la requête
        while (rs.next()) {
            String nom = rs.getString("Nom");
            String prenom = rs.getString("Prenom");
            int anneeDeNaissance = rs.getInt("Naissance");
            String parcours = rs.getString("Parcours");
            String promotion = rs.getString("Promotion");
            
            // Créer un nouvel étudiant avec les informations récupérées



        Etudiant nouvelEtudiant = new Etudiant(nom, prenom,anneeDeNaissance, parcours, promotion);
          
        etudiantData.add(nouvelEtudiant);
     

        }


        
            // Exécuter la requête SQL et obtenir un objet
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
    }

    /**
     * Ouvre une fenetre de dialogue pour editer les donnees pour un etudiant selectionner
     * Si l'utilisateur clique sur OK, le changement sera sauvegarder dans l'objet etudiant fourni
     * Le booleen true est retroune.
     *
     * @param etudiant l'objet etudiant qui doit etre edite
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
     * Ouvre une fenetre de dialogue pour editer les donnees pour un etudiant selectionner
     * Si l'utilisateur clique sur OK, le changement sera sauvegarder dans l'objet etudiant fourni
     * Le booleen true est retroune.
     *
     * @param etudiant l'objet etudiant qui doit etre edite
     * @return true si l'utilisateur clique sur OK sinon il retourne false.
     */
    public boolean showAjouterDialogModif(Etudiant etudiant) {
        try {
    
            // Charge le fichier fxml et cr�� un nouveau stage pour la fen�tre de dialogue popup.
            FXMLLoader loader = new FXMLLoader();
    
            loader.setLocation(MainApp.class.getResource("Modifier.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            // Cr�� la fen�tre de dialogue Stage.
            Stage dialogStageM= new Stage();
            dialogStageM.setTitle("Editer Etudiant");
            dialogStageM.initModality(Modality.WINDOW_MODAL);
            dialogStageM.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStageM.setScene(scene);
            // Set l'etudiant dans le controller.
            Modifier controller = loader.getController();
            controller.setDialogStage(dialogStageM);
            controller.setEtudiant(etudiant);
            // Affiche la fen�tre de dialogue et attends jusqu'� ce que l'utilisateur la ferme.
            dialogStageM.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
       
    }
 

    /**
     * Methode permettant de changer les donnees du tableau par une liste donnee
     * @param liste
     */
    public void changeData(ObservableList<Etudiant> liste){
        controllerPO.changeData(liste);
    }
   
}
