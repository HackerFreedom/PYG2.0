package entity;

import java.io.Serializable;
import java.util.List;

//数据是在网络上传输的,所以需要序列化
public class PageResult implements Serializable {
    //总记录数
    private long total;
    //当前页的记录数
    private List rows;

  public PageResult(long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
