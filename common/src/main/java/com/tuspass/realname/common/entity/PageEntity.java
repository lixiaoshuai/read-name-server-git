/**
 * 
 */
package com.tuspass.realname.common.entity;


import com.tuspass.realname.common.constant.Constants;

import java.util.List;

/**
 * @author zhouyang
 * 
 */
public class PageEntity<T> extends RespEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 总的记录条数
	 */
	private Long totalCount = 0L;

	/**
	 * 当前页码
	 */
	private Integer page;

	/**
	 * 分页长度
	 */
	private Integer length;

	/**
	 * 记录集合
	 */
	private List<T> dataList;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static PageEntity successPage(List dataList,Long totalCount,String message) {
		PageEntity pageEntity = new PageEntity();
		pageEntity.setCode(Constants.BUSI_SUCCESS);
		pageEntity.setDataList(dataList);
		pageEntity.setTotalCount(totalCount);
		pageEntity.setMessage(message);
		return pageEntity;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static RespEntity errorPage(String code,String message) {
		PageEntity pageEntity = new PageEntity();
		pageEntity.setCode(Constants.SERVICE_ERROR);
		pageEntity.setMessage(message);
		return pageEntity;
	}


	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
}
