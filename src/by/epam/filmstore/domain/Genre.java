package by.epam.filmstore.domain;

/**
 * @author Olga Shahray
 */
public class Genre {
    private int id;
    private String genreName;

    public Genre() {
    }

    public Genre(int id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public Genre(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() != this.getClass()) return false;

        Genre genre = (Genre) o;

        if (id != genre.id) return false;
        return !(genreName != null ? !genreName.equals(genre.genreName) : genre.genreName != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (genreName != null ? genreName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", genreName='" + genreName + '\'' +
                '}';
    }
}
