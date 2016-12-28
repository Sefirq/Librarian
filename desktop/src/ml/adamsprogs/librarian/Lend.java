package ml.adamsprogs.librarian;

class Lend {

    private String title;
    private String author;
    private String reader;
    private String since;
    private String till;
    private String library;
    private String branch;
    private String signature;
    private String isbn;
    private String librarian;

    public Lend(String title, String author, String reader, String since, String till, String library, String branch,
                String signature, String isbn, String librarian) {
        this.title = title;
        this.author = author;
        this.reader = reader;
        this.since = since;
        this.till = till;
        this.library = library;
        this.branch = branch;
        this.signature = signature;
        this.isbn = isbn;
        this.librarian = librarian;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }

    public String getTill() {
        return till;
    }

    public void setTill(String till) {
        this.till = till;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLibrarian() {
        return librarian;
    }

    public void setLibrarian(String librarian) {
        this.librarian = librarian;
    }
}
