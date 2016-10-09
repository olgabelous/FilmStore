package by.epam.filmstore.domain.dto;

import java.util.List;
import java.util.Objects;

/**
 * DTO class PagingListDTO<T> is used for pagination. Field {@code List<T> objectList} is list of objects showing
 * on page and {@code int count} is total count of objects in database
 *
 * @see by.epam.filmstore.jspbean.Paginator
 * @author Olga Shahray
 */
public class PagingListDTO<T> {

    private int count;
    private List<T> objectList;

    public PagingListDTO(int count, List<T> objectList) {
        this.count = count;
        this.objectList = objectList;
    }

    public PagingListDTO() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<T> objectList) {
        this.objectList = objectList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() != this.getClass()) return false;
        PagingListDTO<?> that = (PagingListDTO<?>) o;
        return count == that.count &&
                Objects.equals(objectList, that.objectList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, objectList);
    }

    @Override
    public String toString() {
        return "PagingListDTO{" +
                "count=" + count +
                ", objectList=" + objectList +
                '}';
    }
}
