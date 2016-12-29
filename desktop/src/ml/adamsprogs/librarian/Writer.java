package ml.adamsprogs.librarian;

class Writer {
    private String forename;
    private String surname;
    private String nationality;

    public Writer(String forename, String surname, String nationality) {
        this.forename = forename;
        this.surname = surname;
        this.nationality = nationality;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
