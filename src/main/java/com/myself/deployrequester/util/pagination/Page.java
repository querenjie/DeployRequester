package com.myself.deployrequester.util.pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Page<T> {

	protected static final int DEFAULT_SHOW_PAGE_NUMBER_COUNT = 9;
	private int startIndex;
	private int pageSize = 15;
	private int totalCount;
	private Collection<T> results;
	private int[] showPageNumbers;
	private InnerPage[] pages;
	private int pageNo;
	private int pageCount;
	private List<Map<String, String>> params = null;

	public List<Map<String, String>> getParams() {
		return this.params;
	}

	public void setParams(List<Map<String, String>> params) {
		this.params = params;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public Page() {
		this.startIndex = 0;
		this.pageSize = 15;
		this.totalCount = 0;
		this.pageNo = 1;
		this.pageCount = 1;
		this.results = new ArrayList();
	}

	public Page(int startIndex, int pageSize, int totalCount,
			Collection<T> results) {
		this.startIndex = startIndex;
		this.pageSize = ((pageSize <= 0) ? 15 : pageSize);
		this.totalCount = totalCount;
		this.results = results;
	}

	public int[] getShowPageNumbers() {
		return getShowPageNumbers(9);
	}

	public int[] getShowPageNumbers(int showCount) {
		int currentPage = this.startIndex / this.pageSize;
		if (this.showPageNumbers == null) {
			int totalPageCount = getTotalPageCount();
			this.showPageNumbers = new int[(totalPageCount > showCount) ? showCount
					: totalPageCount];
			if (totalPageCount > showCount) {
				int firstShowPage = currentPage - (showCount / 2);
				int endShowPage = currentPage + showCount / 2;

				if ((firstShowPage > 0) && (endShowPage < totalPageCount)) {
					int i = 0;
					for (int max = this.showPageNumbers.length; i < max; ++i) {
						this.showPageNumbers[i] = (firstShowPage + i);
					}
				} else if (firstShowPage > 0) {
					int i = 0;
					for (int max = this.showPageNumbers.length; i < max; ++i) {
						this.showPageNumbers[i] = (totalPageCount - showCount + i);
					}
				} else {
					int i = 0;
					for (int max = this.showPageNumbers.length; i < max; ++i) {
						this.showPageNumbers[i] = i;
					}
				}
			} else {
				int i = 0;
				for (int max = this.showPageNumbers.length; i < max; ++i) {
					this.showPageNumbers[i] = i;
				}
			}
		}
		return this.showPageNumbers;
	}

	public int getTotalPageCount() {
		return ((this.totalCount % this.pageSize == 0) ? this.totalCount
				/ this.pageSize : this.totalCount / this.pageSize + 1);
	}

	public int getNextIndex() {
		return (this.startIndex + this.pageSize);
	}

	public int getPreviousIndex() {
		int previousIndex = this.startIndex - this.pageSize;
		return ((previousIndex >= 0) ? previousIndex : 0);
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Collection<T> getResults() {
		return this.results;
	}

/*	public List<T> getList() {
		return new ArrayList(this.results);
	}

	public Set<T> getSet() {
		return new HashSet(this.results);
	}*/

/*	public List<T> getSortList() {
		List list = getList();
		if (list != null) {
			Collections.sort(list);
		}
		return list;
	}*/

	public void setResults(Collection<T> results) {
		this.results = results;
	}

	public int getStartIndex() {
		this.startIndex = ((this.pageNo - 1) * this.pageSize);
		return this.startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageCount() {
		return this.pageCount;
	}

	public int getPage() {
		return ((this.startIndex >= 0) ? this.startIndex / this.pageSize + 1
				: 0);
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void fillIntoPage(Page page) {
		if (page == null) {
			throw new RuntimeException("分页信息错误");
		}
		page.setStartIndex((this.pageNo - 1) * this.pageSize);
		page.setPageSize(this.pageSize);
	}

	public InnerPage[] getPages() {
		return ((this.pages == null) ? (this.pages = generatePages())
				: this.pages);
	}

	private InnerPage[] generatePages() {
		int page = getPage() - 1;
		int from = page * this.pageSize;
		if (from > this.totalCount) {
			from = this.totalCount - this.pageSize;
			if (from < 0) {
				from = 0;
			}
		}
		int numberOfPages = (int) Math.ceil(this.totalCount / this.pageSize);
		InnerPage[] pages = new InnerPage[numberOfPages];
		for (int i = 0; i < pages.length; ++i) {
			pages[i] = new InnerPage();
			pages[i].setFrom(i * this.pageSize + 1);
			pages[i].setSize(this.pageSize);
			pages[i].setTo((i + 1) * this.pageSize);
			if ((from >= pages[i].getFrom() - 1) && (from < pages[i].getTo())) {
				pages[i].setSelected(true);
			} else {
				pages[i].setSelected(false);
			}
		}
		if (numberOfPages > 0) {
			InnerPage lastPage = pages[(numberOfPages - 1)];
			if (lastPage.getTo() > this.totalCount) {
				lastPage.setSize(this.totalCount - lastPage.getFrom());
				lastPage.setTo(this.totalCount);
			}
		}
		return pages;
	}

	public static class InnerPage {
		private int from;
		private int to;
		private int size;
		private boolean selected;

		public int getFrom() {
			return this.from;
		}

		public void setFrom(int from) {
			this.from = from;
		}

		public boolean isSelected() {
			return this.selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		public int getSize() {
			return this.size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public int getTo() {
			return this.to;
		}

		public void setTo(int to) {
			this.to = to;
		}
	}


}
