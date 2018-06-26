package employeeutil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;

public class CustomPageImpl<T>  {
    private List<T> content;
    private int number;
    private int size;
    private long totalElements;

    public CustomPageImpl() {
        content = new ArrayList<>();
        number = 0;
        size = 0;
        totalElements = 0;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}