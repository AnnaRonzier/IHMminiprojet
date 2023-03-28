package ch.makery.address;



import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe Etudiant
 *
 * @author 
 */
public class Etudiant {

    private final StringProperty parcours;
    private final StringProperty prenom;
    private final StringProperty nom;
    private final IntegerProperty anneeDeNaissance;
    private final StringProperty promotion;

    /**
     * Constructeur par d�faut.
     */
    public Etudiant() {
        this(null, null, 0, null, null);
    }

    /**
     * Constructeur avec param�tres.
     *
     * @param parcours
     * @param prenom
     * @param nom
     * @param anneeDeNaissance
     * @param promotion
     */
    public Etudiant(String prenom, String nom, Integer anneeDeNaissance, String promotion, String parcours) {
        this.prenom = new SimpleStringProperty(prenom);
        this.nom = new SimpleStringProperty(nom);
        this.anneeDeNaissance = new SimpleIntegerProperty(anneeDeNaissance);
        this.promotion = new SimpleStringProperty(promotion);
        this.parcours = new SimpleStringProperty(parcours);
    }

    /**
     * Getter du pr�nom de l'�tudiant
     * @return String prenom
     */
    public String getPrenom() {
        return prenom.get();
    }

    /**
     * Setter du prenom de l'�tudiant
     * @param prenom
     */
    public void setPrenom(String prenom) {
    this.prenom.set(prenom);
   
    }


    /**
     * Getter de la propri�t� du pr�nom
     * @return StringProperty prenom
     */
    public StringProperty prenomProperty() {
        return prenom;
    }

    /**
     * Getter du nom de l'�tudiant
     * @return String nom
     */
    public String getNom() {
        return nom.get();
    }

    /**
     * Setter du nom de l'�tudiant
     * @param nom
     */
    public void setNom(String nom) {
        this.nom.set(nom);
    }

    /**
     * Getter de la propri�t� du nom de l'�tudiant
     * @return StringProperty nom
     */
    public StringProperty nomProperty() {
        return nom;
    }

    /**
     * Getter de l'ann�e de naissance de l'�tudiant
     * @return int anneeDeNaissance
     */
    public int getAnneeDeNaissance() {
        return anneeDeNaissance.get();
    }

    /**
     * Setter de l'ann�e de naissance de l'�tudiant
     * @param anneeDeNaissance
     */
    public void setAnneeDeNaissance(Integer anneeDeNaissance) {
        this.anneeDeNaissance.set(anneeDeNaissance);
    }

    /**
     * Getter de la propri�t� de l'ann�e de naissance de l'�tudiant
     * @return IntegerProperty anneeDeNaissance
     */
    public IntegerProperty anneeDeNaissanceProperty() {
        return anneeDeNaissance;
    }

    /**
     * Getter de la promotion de l'�tudiant
     * @return String promotion
     */
    public String getPromotion() {
        return promotion.get();
    }

    /**
     * Setter de la promotion de l'�tudiant
     * @param promotion
     */
    public void setPromotion(String promotion) {
        this.promotion.set(promotion);
    }

    /**
     * Getter de la propri�t� de  la promotion de l'�tudiant
     * @return StringProperty promotion
     */
    public StringProperty promotionProperty() {
        return promotion;
    }

    /**
     * Getter de la parcours de l'�tudiant
     * @return String parcours
     */
    public String getParcours() {
        return parcours.get();
    }

    /**
     * Setter de la parcours de l'�tudiant
     * @param promotion
     */
    public void setParcours(String parcours) {
        this.parcours.set(parcours);
    }

    /**
     * Getter de la propri�t� du parcours de l'�tudiant
     * @return StringPropertyparcours
     */
    public StringProperty parcoursProperty() {
        return parcours;
    }

}