package modenlibrary.Common.eum;

/**
 * @author L.star
 * @date 2020/12/23 17:17
 */
public enum  RoleEnum {
    /**
     * 管理员名
     */
    SPADMIN("超级管理员",1),ADMIN("普通管理员",2),READER("读者",3),
    BLACK("黑名单",4);

    private String name;
    private Integer roleid;

    RoleEnum(String name,Integer roleid) {
        this.name = name;
        this.roleid = roleid;
    }

    public String getName() {
        return name;
    }
    public Integer getRoleid(){
        return roleid;
    }
}
