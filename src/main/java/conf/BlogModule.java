package conf;

import com.fit.tx.TxInterceptor;

import com.fit.web.Blog;
import com.google.inject.Binder;
import com.google.inject.Module;
import org.expressme.webwind.guice.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * Module for Guice.
 */
public class BlogModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(Blog.class).asEagerSingleton();
        //拦截器
        binder.bind(TxInterceptor.class).asEagerSingleton();
    }
}