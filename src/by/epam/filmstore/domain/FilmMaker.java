package by.epam.filmstore.domain;

/**
 * Created by Olga Shahray on 29.06.2016.
 */
public class FilmMaker {
    private int id;
    private  String name;
    private Profession profession;

    public FilmMaker() {
    }

    public FilmMaker(int id, String name, Profession profession) {
        this.id = id;
        this.name = name;
        this.profession = profession;
    }

    public FilmMaker(String name, Profession profession) {
        this.name = name;
        this.profession = profession;
    }

    public FilmMaker(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() != this.getClass()) return false;

        FilmMaker filmMaker = (FilmMaker) o;

        if (id != filmMaker.id) return false;
        if (name != null ? !name.equals(filmMaker.name) : filmMaker.name != null) return false;
        return profession == filmMaker.profession;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (profession != null ? profession.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FilmMaker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profession=" + profession +
                '}';
    }
}
