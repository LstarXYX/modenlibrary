package modenlibrary.Common.eum;

/**
 * 借出去的书本的状态
 *
 * @author L.star
 * @date 2020/12/22 16:12
 */
public enum BookStatus {
    /**
     * 表示图书借出状态信息
     */
    LENDING("借出"), RETURNED("归还"),OTHERS("其他");

    private String msg;

    BookStatus(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
