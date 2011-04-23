package org.jmatrices.dbl.db.cache;

import java.util.*;
import java.io.Serializable;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.Reference;

/**
 * SoftHashMap
 *
 * @author purangp
 *         Created 21.11.2004 - 21:20:57
 */
public class SoftHashMap extends AbstractMap implements Serializable {

    private final Map hashMap = new HashMap();

    private final Map reverseLookup = new HashMap();

    private final ReferenceQueue queue = new ReferenceQueue();

    public Object get(Object key) {
        expungeStaleEntries();
        Object result = null;
        SoftReference softRefernce = (SoftReference) hashMap.get(key);
        if (softRefernce != null) {
            result = softRefernce.get();
            if (result == null) {
                hashMap.remove(key);
                reverseLookup.remove(softRefernce);
            }


        }

        return result;
    }

    private void expungeStaleEntries() {
        Reference ref;
        while((ref = queue.poll()) != null) {
            hashMap.remove(reverseLookup.remove(ref.get()));
        }
    }

    public Object put(Object key, Object value) {
        expungeStaleEntries();
        SoftReference softReference = new SoftReference(value,queue);
        reverseLookup.put(softReference, key);
        Object toSend = null;
        SoftReference result = (SoftReference) hashMap.put(key,softReference);
        if (result != null)
            toSend = result.get();
        return toSend;
    }

    public Object remove (Object key) {
        expungeStaleEntries();
        SoftReference softReference  = (SoftReference) hashMap.remove(key);
        Object result = null;
        if (softReference != null)
            result = softReference.get();
        return  result;
    }


    public void clear() {
        hashMap.clear();
        reverseLookup.clear();
    }

    public int size() {
        expungeStaleEntries();
        return hashMap.size();
    }

    public Set entrySet() {
        expungeStaleEntries();
        Set result = new LinkedHashSet();

        for (Iterator iter = hashMap.entrySet().iterator(); iter.hasNext(); ) {
            final Entry entry = (Entry) iter.next();
            final Object value = ((SoftReference)entry.getValue()).get();
            if (value != null) {
                result.add(new Entry(){
                   public Object getKey() {
                       return  entry.getKey();
                   }
                   public Object getValue() {
                       return entry.getValue();
                   }

                    public Object setValue( Object value) {
                        entry.setValue(new SoftReference(value,queue));
                        return value;
                    }
                });

            }

        }
        return result;

    }




}
