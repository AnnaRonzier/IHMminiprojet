package ch.makery.address;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class Student
 *
 * @author GroupIHM24
 */
public class Etudiant {

    private final StringProperty parcours;
    private final StringProperty prenom;
    private final StringProperty nom;
    private final IntegerProperty anneeDeNaissance;
    private final StringProperty promotion;

    /**
     * Constructor
     */
    public Etudiant() {
        this(null, null, 0, null, null);
    }

    /**
     * Constructor with parameters.
     *
     * @param parcours
     * @param prenom
     * @param nom
     * @param anneeDeNaissance
     * @param promotion
     */
    public Etudiant(String nom, String prenom, Integer anneeDeNaissance, String parcours, String promotion) {
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.anneeDeNaissance = new SimpleIntegerProperty(anneeDeNaissance);
        this.parcours = new SimpleStringProperty(parcours);
        this.promotion = new SimpleStringProperty(promotion);
    }

    /**
     * Getter of student's name
     * @return String nom
     */
    public String getPrenom() {
        return prenom.get();
    }

    /**
     * Setter of student's name
     * @param nom
     */
    public void setPrenom(String prenom) {
        this.prenom.set(prenom);

    }

    /**
     * Getter of the property of the name
     * @return StringProperty prenom
     */
    public StringProperty prenomProperty() {
        return prenom;
    }

    /**
     * Getter of the student's name
     * @return String name
     */
    public String getNom() {
        return nom.get();
    }

    /**
     * Setter of the student's name
     * @param nom
     */
    public void setNom(String nom) {
        this.nom.set(nom);
    }

    /**
     * Getter of the student's name property
     * @return StringProperty nom
     */
    public StringProperty nomProperty() {
        return nom;
    }

    /**
     * Getter of the student's year of birth
     * @return int anneeDeNaissance
     */
    public int getAnneeDeNaissance() {
        return anneeDeNaissance.get();
    }

    /**
     * Setter of the student's year of birth
     * @param anneeDeNaissance
     */
    public void setAnneeDeNaissance(Integer anneeDeNaissance) {
        this.anneeDeNaissance.set(anneeDeNaissance);
    }

    /**
     * Getter of the student's year of birth property
     * @return IntegerProperty anneeDeNaissance
     */
    public IntegerProperty anneeDeNaissanceProperty() {
        return anneeDeNaissance;
    }

    /**
     * Getter of the student's promotion
     * @return String promotion
     */
    public String getPromotion() {
        return promotion.get();
    }

    /**
     * Setter of the student's class
     * @param promotion
     */
    public void setPromotion(String promotion) {
        this.promotion.set(promotion);
    }

    /**
     * Getter of the student's class property
     * @return StringProperty promotion
     */
    public StringProperty promotionProperty() {
        return promotion;
    }

    /**
     * Getter of the student's background
     * @return String parcours
     */
    public String getParcours() {
        return parcours.get();
    }

    /**
     * Setter of the student's course
     * @param promotion
     */
    public void setParcours(String parcours) {
        this.parcours.set(parcours);
    }

    /**
     * Getter of the student's course property
     * @return StringPropertyparcours
     */
    public StringProperty parcoursProperty() {
        return parcours;
    }

}