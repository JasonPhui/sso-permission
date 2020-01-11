/**
 * @author ph
 * @description 参数注入类,包括登录模块ur(用于其他模块未登录访问时跳转到登录页面)和token检验（使用dubbo提供服务），两者均通过
 * Spring注入初始化
 * @date 2020/1/11
 */
public class ParamInitializing {

    /**登录url**/
    protected String loginUrl;

    /**校验服务接口**/
    protected AuthenticationRpcService authenticationRpcService;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public AuthenticationRpcService getAuthenticationRpcService() {
        return authenticationRpcService;
    }

    public void setAuthenticationRpcService(AuthenticationRpcService authenticationRpcService) {
        this.authenticationRpcService = authenticationRpcService;
    }
}
