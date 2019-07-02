package com.teacity.parent.base.web.base;

import com.jfinal.config.*;
import com.jfinal.core.Controller;
import com.jfinal.json.JsonManager;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.template.Engine;
import com.jfinal.template.ext.directive.NowDirective;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.teacity.parent.base.cache.LeFstSerializer;
import io.jboot.Jboot;
import io.jboot.JbootConstants;
import io.jboot.aop.jfinal.JfinalHandlers;
import io.jboot.aop.jfinal.JfinalPlugins;
import io.jboot.component.log.Slf4jLogFactory;
import io.jboot.component.shiro.JbootShiroManager;
import io.jboot.component.swagger.JbootSwaggerConfig;
import io.jboot.component.swagger.JbootSwaggerController;
import io.jboot.component.swagger.JbootSwaggerManager;
import io.jboot.config.JbootConfigManager;
import io.jboot.core.rpc.JbootrpcManager;
import io.jboot.db.JbootDbManager;
import io.jboot.schedule.JbootScheduleManager;
import io.jboot.server.listener.JbootAppListenerManager;
import io.jboot.utils.ClassKits;
import io.jboot.utils.ClassScanner;
import io.jboot.utils.StrUtils;
import io.jboot.web.JbootControllerManager;
import io.jboot.web.JbootJson;
import io.jboot.web.cache.ActionCacheHandler;
import io.jboot.web.controller.annotation.RequestMapping;
import io.jboot.web.directive.annotation.JFinalDirective;
import io.jboot.web.directive.annotation.JFinalSharedMethod;
import io.jboot.web.directive.annotation.JFinalSharedObject;
import io.jboot.web.directive.annotation.JFinalSharedStaticMethod;
import io.jboot.web.fixedinterceptor.FixedInterceptors;
import io.jboot.web.handler.JbootActionHandler;
import io.jboot.web.handler.JbootFilterHandler;
import io.jboot.web.handler.JbootHandler;
import io.jboot.web.limitation.JbootLimitationManager;
import io.jboot.web.limitation.LimitationConfig;
import io.jboot.web.limitation.web.LimitationController;
import io.jboot.web.render.JbootRenderFactory;
import io.jboot.wechat.JbootAccessTokenCache;
import io.jboot.wechat.JbootWechatConfig;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class BaseWebConfig extends JFinalConfig{

    static final Log log = Log.getLog(BaseWebConfig.class);
    private List<Routes.Route> routeList = new ArrayList<>();

    public BaseWebConfig() {
        Jboot.injectMembers(this);
    }


    @Override
    public void configConstant(Constants constants) {

        constants.setRenderFactory(JbootRenderFactory.me());
        constants.setDevMode(Jboot.me().isDevMode());
        ApiConfigKit.setDevMode(Jboot.me().isDevMode());

        JbootWechatConfig config = Jboot.config(JbootWechatConfig.class);
        ApiConfig apiConfig = config.getApiConfig();
        if (apiConfig != null) {
            ApiConfigKit.putApiConfig(apiConfig);
        }

        constants.setLogFactory(Slf4jLogFactory.me());
        constants.setMaxPostSize(1024 * 1024 * 2000);
        constants.setReportAfterInvocation(false);

        constants.setControllerFactory(JbootControllerManager.me());
        constants.setJsonFactory(() -> new JbootJson());

        JbootAppListenerManager.me().onJfinalConstantConfig(constants);
    }


    @Override
    public void configRoute(Routes routes) {

        List<Class<Controller>> controllerClassList = ClassScanner.scanSubClass(Controller.class);
        if (controllerClassList == null) {
            return;
        }

        for (Class<Controller> clazz : controllerClassList) {
            RequestMapping mapping = clazz.getAnnotation(RequestMapping.class);
            if (mapping == null || mapping.value() == null) {
                continue;
            }

            if (StrKit.notBlank(mapping.viewPath())) {
                routes.add(mapping.value(), clazz, mapping.viewPath());
            } else {
                routes.add(mapping.value(), clazz);
            }
        }

        JbootSwaggerConfig swaggerConfig = Jboot.config(JbootSwaggerConfig.class);
        if (swaggerConfig.isConfigOk()) {
            routes.add(swaggerConfig.getPath(), JbootSwaggerController.class, swaggerConfig.getPath());
        }

        LimitationConfig limitationConfig = Jboot.config(LimitationConfig.class);
        if (StrUtils.isNotBlank(limitationConfig.getWebPath())) {
            routes.add(limitationConfig.getWebPath(), LimitationController.class);
        }

        JbootAppListenerManager.me().onJfinalRouteConfig(routes);

        for (Routes.Route route : routes.getRouteItemList()) {
            JbootControllerManager.me().setMapping(route.getControllerKey(), route.getControllerClass());
        }

        routeList.addAll(routes.getRouteItemList());
    }

    @Override
    public void configEngine(Engine engine) {

        /**
         * now 并没有被添加到默认的指令当中
         * 查看：EngineConfig
         */
        engine.addDirective("now", NowDirective.class);

        List<Class> directiveClasses = ClassScanner.scanClass();
        for (Class clazz : directiveClasses) {
            JFinalDirective jFinalDirective = (JFinalDirective) clazz.getAnnotation(JFinalDirective.class);
            if (jFinalDirective != null) {
                engine.addDirective(jFinalDirective.value(), clazz);
            }

            JFinalSharedMethod sharedMethod = (JFinalSharedMethod) clazz.getAnnotation(JFinalSharedMethod.class);
            if (sharedMethod != null) {
                engine.addSharedMethod(ClassKits.newInstance(clazz));
            }

            JFinalSharedStaticMethod sharedStaticMethod = (JFinalSharedStaticMethod) clazz.getAnnotation(JFinalSharedStaticMethod.class);
            if (sharedStaticMethod != null) {
                engine.addSharedStaticMethod(clazz);
            }

            JFinalSharedObject sharedObject = (JFinalSharedObject) clazz.getAnnotation(JFinalSharedObject.class);
            if (sharedObject != null) {
                engine.addSharedObject(sharedObject.value(), ClassKits.newInstance(clazz));
            }
        }

        JbootAppListenerManager.me().onJfinalEngineConfig(engine);
    }


    @Override
    public void configPlugin(Plugins plugins) {

        List<ActiveRecordPlugin> arps = JbootDbManager.me().getActiveRecordPlugins();
        for (ActiveRecordPlugin arp : arps) {
            plugins.add(arp);
        }
        String host = PropKit.get("jboot.redis.host");
        String cacheName = PropKit.get("redis.cacheName");
        String password = PropKit.get("jboot.redis.password");
        RedisPlugin redisPlugin = StrUtils.isBlank(password) ? new RedisPlugin(cacheName, host) : new RedisPlugin(cacheName, host, password);
        redisPlugin.setSerializer(LeFstSerializer.me);
        plugins.add(redisPlugin);
        JbootAppListenerManager.me().onJfinalPluginConfig(new JfinalPlugins(plugins));

    }


    @Override
    public void configInterceptor(Interceptors interceptors) {

        JbootAppListenerManager.me().onInterceptorConfig(interceptors);

        JbootAppListenerManager.me().onFixedInterceptorConfig(FixedInterceptors.me());
    }

    @Override
    public void configHandler(Handlers handlers) {

        //用于对jfinal的拦截器进行注入
        handlers.setActionHandler(new JbootActionHandler());

        //先添加用户的handler，再添加jboot自己的handler
        //用户的handler优先于jboot的handler执行
        JbootAppListenerManager.me().onHandlerConfig(new JfinalHandlers(handlers));

        handlers.add(new JbootFilterHandler());
        handlers.add(new ActionCacheHandler());
        handlers.add(new JbootHandler());
    }

    @Override
    public void afterJFinalStart() {
        super.afterJFinalStart();
        ApiConfigKit.setAccessTokenCache(new JbootAccessTokenCache());
        JsonManager.me().setDefaultDatePattern("yyyy-MM-dd HH:mm:ss");

        /**
         * 初始化
         */
        JbootrpcManager.me().init();
        JbootShiroManager.me().init(routeList);
        JbootLimitationManager.me().init(routeList);
        JbootScheduleManager.me().init();
        JbootSwaggerManager.me().init();

        /**
         * 发送启动完成通知
         */
        Jboot.sendEvent(JbootConstants.EVENT_STARTED, null);

        JbootAppListenerManager.me().onJFinalStarted();
    }

    @Override
    public void beforeJFinalStop() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        if (drivers != null) {
            while (drivers.hasMoreElements()) {
                try {
                    Driver driver = drivers.nextElement();
                    DriverManager.deregisterDriver(driver);
                } catch (Exception e) {
                    log.error(e.toString(), e);
                }
            }
        }
        JbootConfigManager.me().destroy();
        JbootAppListenerManager.me().onJFinalStop();
    }


}
