package ml.adamsprogs.librarian;

public class Reader {
    private String forename;
    private String surname;
    private String pesel;

    public Reader(String forename, String surname, String pesel) {
        this.forename = forename;
        this.surname = surname;
        this.pesel = pesel;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }
}
