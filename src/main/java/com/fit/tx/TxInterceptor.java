package com.fit.tx;

import java.sql.Connection;

import org.expressme.webwind.Execution;
import org.expressme.webwind.Interceptor;
import org.expressme.webwind.InterceptorChain;
import org.expressme.webwind.InterceptorOrder;
import com.fit.util.DbUtils;

/**
 * Transaction interceptor.
 */
@InterceptorOrder(1)
public class TxInterceptor implements Interceptor {

    public void intercept(Execution execution, InterceptorChain chain) throws Exception {
    	DbUtils.initDb();
        Connection connection = DbUtils.getConnection();
        connection.setAutoCommit(false);
        try {
            TxHolder.setCurrentConnection(connection);
            chain.doInterceptor(execution);
            connection.commit();
        }
        catch (Exception e) {
            connection.rollback();
            throw e;
        }
        finally {
            TxHolder.removeCurrentConnection();
        }
    }
}
