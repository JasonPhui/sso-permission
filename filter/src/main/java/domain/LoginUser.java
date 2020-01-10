package domain;

import java.io.Serializable;

/**
 * @author ph
 * @description 存储登录用户信息实体类
 * @date 2020/1/10
 */
public class LoginUser implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;

    /**登录用户token**/
    private String token;

    /**登录用户名**/
    private String username;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
