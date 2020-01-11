import domain.LoginUser;
import domain.UserPermission;

import java.util.List;

/**
 * @author ph
 * @description 认证服务接口（用dubbo实现）
 * @date 2020/1/11
 */
public interface AuthenticationRpcService {

    /**
     * 登录校验
     * @param token 请求中的token参数值
     * @return 布尔值
     */
    boolean validate(String token);

    /**
     * 根据token获取登录用户的信息
     * @param token 请求中的token参数值
     * @return 登录用户实体类
     */
    LoginUser findUserInfo(String token);

    /**
     * 根据token和应用代码获取用户权限列表
     * @param token 请求中的token参数值
     * @param appCode 应用代码
     * @return 用户权限列表
     */
    List<UserPermission> findUserPermissionList(String token, String appCode);
}
