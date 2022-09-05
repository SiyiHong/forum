package forum.dto;

import java.util.List;

//范型
public class PageDTO<T> {
    private int pageNumber;//当前页面
    private int totalRecord;//总条数
    private int pageSize;//每页显示的记录数
    private int totalPage;//总页数
    private List<T> list;
    public PageDTO(int pageNumber,int pageSize,int totalRecord){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        this.totalPage = totalRecord/pageSize + (totalRecord%pageSize!=0? 1:0);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageDTO{" +
                "pageNumber=" + pageNumber +
                ", totalRecord=" + totalRecord +
                ", pageSize=" + pageSize +
                ", totalPage=" + totalPage +
                ", list=" + list +
                '}';
    }
}
