package com.lolstudio.base.assit;

import java.util.HashMap;
import java.util.Map;


/**
 * Singleton helper class for lazily initialization.
 *		 testsingleton testsingleton = SingletonUtil.get(testsingleton.class);
		testsingleton testsingleton2 = SingletonUtil.get(testsingleton.class);
		System.err.println(testsingleton.toString());
		System.err.println(testsingleton2.toString());
 *
 */
public  class SingletonUtil {
	private static Map<Class<?>, Object> classMap = new HashMap<>();

    public static <Clazz> Clazz get(Class<Clazz> clazz){
        if(!classMap.containsKey(clazz)){
            try {
                classMap.put(clazz, clazz.newInstance());
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return (Clazz)classMap.get(clazz);
    }
}
