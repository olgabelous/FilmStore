package by.epam.filmstore.domain.dto;

import java.util.List;

/**
 * Created by Olga Shahray on 19.09.2016.
 */
public class PagingListDTO<T> {

    int count;
    List<T> objectList;

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
}
