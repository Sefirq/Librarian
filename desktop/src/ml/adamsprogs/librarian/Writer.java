package ml.adamsprogs.librarian;

public class Writer {
    private String forename;
    private String surname;
    private String nationality;

    public Writer(String forename, String surname, String nationality) {
        this.forename = forename;
        this.surname = surname;
        this.nationality = nationality;
    }

    private Writer() {
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

    public static Writer fromJson(String authorString) {
        Writer w = new Writer();
        for (String kv : authorString.replaceAll("[ \\[\\]\\{\\}]", "").split(",")) {
            String[] kva = kv.split(":");
            switch (kva[0]) {
                case "'forename'":
                    w.forename = kva[1].replaceAll("[']", "");
                    break;
                case "'surname'":
                    w.surname = kva[1].replaceAll("[']", "");
                    break;
                case "'nationality'":
                    w.nationality = kva[1].replaceAll("[']", "");
                    break;
            }
        }
        return w;
    }

    public String toJson() {
        return "{'forename': '" + forename + "', 'surname': '" + surname + "', 'nationality': '" + nationality + "'}";
    }
}
