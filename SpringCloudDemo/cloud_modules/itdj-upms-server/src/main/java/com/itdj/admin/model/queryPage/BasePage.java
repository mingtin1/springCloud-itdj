package com.itdj.admin.model.queryPage;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BasePage {
    private int page = 1;
    private int limit = 10;
    private int pageIndex;

    public BasePage() {
    }

    public BasePage(int page, int limit, int pageIndex) {
        this.page = page;
        this.limit = limit;
        this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
        return (page - 1) * limit;
    }
}
