package by.epam.filmstore.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by Olga Shahray on 17.06.2016.
 */
public class Comment {
    private int id;
    private User user;
    private Film film;
    private int mark;
    private String text;
    private LocalDateTime dateComment;
    private CommentStatus status;

    public Comment() {
    }

    public Comment(User user, Film film, int mark, String text, LocalDateTime dateComment, CommentStatus status) {
        this.user = user;
        this.film = film;
        this.mark = mark;
        this.text = text;
        this.dateComment = dateComment;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateComment() {
        return dateComment;
    }

    public void setDateComment(LocalDateTime dateComment) {
        this.dateComment = dateComment;
    }

    public CommentStatus getStatus() {
        return status;
    }

    public void setStatus(CommentStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() != this.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id &&
                mark == comment.mark &&
                Objects.equals(user, comment.user) &&
                Objects.equals(film, comment.film) &&
                Objects.equals(text, comment.text) &&
                Objects.equals(dateComment, comment.dateComment) &&
                status == comment.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, film, mark, text, dateComment, status);
    }
}
