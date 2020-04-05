package classes;

public class Client{

    private String dni, nom, cog;



    public Client(String dni) {
        this.dni = dni;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCog() {
        return cog;
    }

    public void setCog(String cog) {
        this.cog = cog;
    }


    public String toStringClie() {
        return this.dni+"--"+this.nom+"--"+this.cog;
    }

    @Override
    public String toString() {
        return this.nom.toUpperCase().charAt(0)+". "+this.cog+" | "+this.dni;
    }

}