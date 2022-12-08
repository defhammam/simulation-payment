package id.co.bankmandiri.micropayment.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PageResponseWrapper<T> {
    private List<T> data;
    private Long totalElement;
    private Integer totalPage;
    private Integer pageIndex;
    private Integer pageSize;

    public PageResponseWrapper(Page<T> page) {
        this.data = page.getContent();
        this.totalElement = page.getTotalElements();
        this.totalPage = page.getTotalPages();
        this.pageIndex = page.getNumber();
        this.pageSize = page.getSize();
    }
}
