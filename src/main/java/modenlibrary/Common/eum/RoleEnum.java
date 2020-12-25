package modenlibrary.Common.eum;

/**
 * @author L.star
 * @date 2020/12/23 17:17
 */
public enum  RoleEnum {
    /**
     * 管理员名
     */
    SPADMIN("超级管理员"),ADMIN("普通管理员"),READER("读者");

    private String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
