package org.agoncal.application.petstore.util;

import java.lang.reflect.Field;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.Id;

/**
 *
 * @author jtervoorde
 */
@Cacheable
@Interceptor
public class CachingInterceptor {
    @AroundInvoke
    public Object fromCache(InvocationContext ic) throws Exception {
        if (ic.getParameters().length == 1) {
            ObjectCache.Key key = null;
            if (ic.getParameters()[0] instanceof Long) {
                key = new ObjectCache.Key((Long)ic.getParameters()[0]);
            } else if (ic.getParameters()[0] instanceof String) {
                key = new ObjectCache.Key((String)ic.getParameters()[0]);
            } else {
                key = findKey(ic.getParameters()[0]);
            }
            if (key != null) {            
                if (ic.getMethod().getReturnType() == Void.class) {
                    ObjectCache.getInstance().remove(key);
                    return ic.proceed();
                } else {
                    Object value = ic.proceed();
                    ObjectCache.getInstance().put(key, value);
                    return value;
                }            
            }
        }
        return ic.proceed();
    }
    
    private ObjectCache.Key findKey(Object object) throws IllegalAccessException {
        for (Field field : object.getClass().getFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                if (field.getType().equals(Long.class)) {
                    return new ObjectCache.Key(field.getLong(object));
                } else if (field.getType().equals(String.class)) {
                    return new ObjectCache.Key((String)field.get(object));
                }
            }
        }
        return null;
    }
}
