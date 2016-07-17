package by.epam.filmstore.domain;

import java.time.LocalDateTime;

/**
 * Created by Olga Shahray on 17.06.2016.
 */
public class Comment {
    private User user;
    private Film film;
    private int mark;
    private String text;
    private LocalDateTime dateComment;
    private String status;

    public Comment() {
    }

    public Comment(User user, Film film, int mark, String text, LocalDateTime dateComment, String status) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() != this.getClass()) return false;

        Comment comment = (Comment) o;

        if (mark != comment.mark) return false;
        if (!user.equals(comment.user)) return false;
        if (!film.equals(comment.film)) return false;
        if (text != null ? !text.equals(comment.text) : comment.text != null) return false;
        if (dateComment != null ? !dateComment.equals(comment.dateComment) : comment.dateComment != null) return false;
        return !(status != null ? !status.equals(comment.status) : comment.status != null);

    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + film.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "user=" + user +
                ", film=" + film +
                ", mark=" + mark +
                ", text='" + text + '\'' +
                ", dateComment=" + dateComment +
                ", status='" + status + '\'' +
                '}';
    }
}
