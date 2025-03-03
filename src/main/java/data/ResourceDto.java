package data;

import java.util.List;

public class ResourceDto {

    private String page;
    private String per_page;
    private String total;
    private String total_pages;
    private List<UserDto> data;
    private SupportDto support;

    public ResourceDto() {
        super();
    }

    public ResourceDto(String page, String per_page, String total, String total_pages, List<UserDto> data, SupportDto support) {
        this.page = page;
        this.per_page = per_page;
        this.total = total;
        this.total_pages = total_pages;
        this.data = data;
        this.support = support;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPer_page() {
        return per_page;
    }

    public void setPer_page(String per_page) {
        this.per_page = per_page;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public List<UserDto> getData() {
        return data;
    }

    public void setData(List<UserDto> data) {
        this.data = data;
    }

    public SupportDto getSupport() {
        return support;
    }

    public void setSupport(SupportDto support) {
        this.support = support;
    }
}
