package me.gbwl.mp.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageBean<T>
  implements Serializable
{
  private static final long serialVersionUID = 6295579614989910451L;
  private int pageNo;
  private int nextNo;
  private int priorNo;
  private int pageCount;
  private int rowCount;
  private int pageSize;
  private int startRow;
  private String orderBy;
  private String orderType;
  private List<String> pageList;
  private int pageListSize = 11;
  private List<T> list;
  private String groupby;

  public PageBean()
  {
    this.pageSize = 10;
    this.pageNo = 1;
    this.startRow = 0;
    this.rowCount = 0;
  }

  public int getNextNo() {
    this.nextNo = (getPageNo() + 1);
    if (this.nextNo > this.pageCount) return this.pageCount;
    return this.nextNo;
  }

  public int getPriorNo() {
    this.priorNo = (getPageNo() - 1);
    if (this.priorNo < 1) return 1;
    return this.priorNo;
  }

  public int getPageCount(int rowCounts) {
    if ((rowCounts % this.pageSize == 0) && (rowCounts > this.pageSize))
      this.pageCount = (rowCounts / this.pageSize);
    else if ((rowCounts % this.pageSize != 0) && (rowCounts > this.pageSize)) {
      this.pageCount = (rowCounts / this.pageSize + 1);
    }
    else {
      this.pageCount = 1;
    }
    return this.pageCount;
  }

  public PageBean(int pageNo, int pageSize) {
    this.pageSize = pageSize;
    this.pageNo = pageNo;
  }

  public int getPageNo() {
    if (this.pageNo <= 0) {
      this.pageNo = 1;
    }
    return this.pageNo;
  }

  public void setPageNo(Integer pageNo) {
    if (pageNo == null) pageNo = Integer.valueOf(0);
    this.pageNo = pageNo.intValue();
  }

  public int getPageCount() {
    if ((this.rowCount % this.pageSize == 0) && (this.rowCount > this.pageSize))
      this.pageCount = (this.rowCount / this.pageSize);
    else if ((this.rowCount % this.pageSize != 0) && (this.rowCount > this.pageSize))
      this.pageCount = (this.rowCount / this.pageSize + 1);
    else {
      this.pageCount = 1;
    }
    return this.pageCount;
  }

  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }

  public int getRowCount() {
    return this.rowCount;
  }

  public void setRowCount(int rowCount) {
    this.rowCount = rowCount;
    if (this.pageSize <= 0) {
      this.pageSize = 10;
    }
    this.pageCount = ((rowCount + this.pageSize - 1) / this.pageSize);
  }

  public int getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(int pageSize) {
    if (pageSize > 100)
      this.pageSize = 100;
    else
      this.pageSize = pageSize;
  }

  public int getStartRow() {
    this.startRow = ((this.pageNo - 1) * this.pageSize);
    if (this.startRow < 0) {
      this.startRow = 0;
    }
    return this.startRow;
  }

  public void setStartRow(int startRow) {
    this.startRow = startRow;
  }

  public List<String> getPageList() {
    if (this.pageCount > 0) {
      this.pageList = new ArrayList();

      int startNo = 1;
      int endNo = 0;
      if (this.pageNo - this.pageListSize / 2 > 0) {
        startNo = this.pageNo - this.pageListSize / 2;
      }
      if (this.pageNo + this.pageListSize / 2 < this.pageCount)
        endNo = this.pageNo + this.pageListSize / 2;
      else {
        endNo = this.pageCount;
      }
      for (int i = startNo; i <= endNo; i++) {
        this.pageList.add(Integer.toString(i));
      }
      return this.pageList;
    }
    return new ArrayList();
  }

  public int getPageListSize()
  {
    return this.pageListSize;
  }

  public void setPageListSize(int pageListSize) {
    this.pageListSize = pageListSize;
  }

  public List<T> getList() {
    return this.list;
  }

  public void setList(List<T> reList) {
    this.list = reList;
  }
  public String getOrderBy() {
    return this.orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  public String getOrderType() {
    return this.orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  public String getGroupby() {
    return this.groupby;
  }

  public void setGroupby(String groupby) {
    this.groupby = groupby;
  }

  public String toString()
  {
    getPageList();
    return "PageBean [pageNo=" + this.pageNo + ", nextNo=" + this.nextNo + 
      ", priorNo=" + this.priorNo + ", pageCount=" + this.pageCount + 
      ", rowCount=" + this.rowCount + ", pageSize=" + this.pageSize + 
      ", startRow=" + this.startRow + ", orderBy=" + this.orderBy + 
      ", orderType=" + this.orderType + ", pageList=" + this.pageList + 
      ", pageListSize=" + this.pageListSize + ", list=" + this.list + 
      ", groupby=" + this.groupby + "]";
  }
}