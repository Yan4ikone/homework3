package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListDto {

        @JsonProperty("page")
        private String page;

        @JsonProperty("per_page")
        private String per_page;

        @JsonProperty("total")
        private String total;

        @JsonProperty("total_pages")
        private String total_pages;

        @JsonProperty("data")
        private List<DataDto> data;

        @JsonProperty("support")
        private SupportDto support;

        public ListDto() {
            super();
        }

        public ListDto(String page, String per_page, String total,
                           String total_pages, List<DataDto> data,
                           SupportDto support) {
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

        public List<DataDto> getData() {
            return data;
        }

        public void setData(List<DataDto> data) {
            this.data = data;
        }

        public SupportDto getSupport() {
            return support;
        }

        public void setSupport(SupportDto support) {
            this.support = support;
        }
}


