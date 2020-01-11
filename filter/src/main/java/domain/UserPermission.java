package domain;

import java.io.Serializable;

/**
 * @author ph
 * @description 存储用户权限信息
 * @date 2020/1/11
 */
public class UserPermission implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;

    /**id**/
    private Integer id;

    /**父id**/
    private Integer parentId;

    /**图标**/
    private Integer icon;

    /**菜单名称**/
    private String name;

    /**权限地址**/
    private String url;

    /**是否菜单**/
    private Boolean isMenu;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getMenu() {
        return isMenu;
    }

    public void setMenu(Boolean menu) {
        isMenu = menu;
    }
}
