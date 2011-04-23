package org.jmatrices.dbl.db.cache;

import java.util.Map;
import java.util.HashMap;

/**
 * SoftHashMapTest
 *
 * @author purangp
 *         Created 21.11.2004 - 21:45:34
 */
public class SoftHashMapTest {

    public static void print(Map map ) {
        System.out.println("one=" + map.get("one"));
        System.out.println("two=" + map.get("two"));
        System.out.println("three=" + map.get("three"));
        System.out.println("four=" + map.get("four"));
        System.out.println("five=" + map.get("five"));
    }


    public static void testMap(Map map) throws InterruptedException{
        System.out.println("Testing " + map.getClass());
        map.put("one",new Integer(1));
        map.put("two",new Integer(2));
        map.put("three",new Integer(3));
        map.put("four",new Integer(4));
        map.put("five",new Integer(5));

        print(map);
        Thread.sleep(2000);
        print(map);
        try {
           byte[] block = new byte[200*1024*1024];

        }  catch(OutOfMemoryError ome) {
            ome.printStackTrace();
        }

        print(map);
    }

    public static void main(String[] args) throws InterruptedException {
        testMap(new HashMap());
        testMap(new SoftHashMap());
    }
}
