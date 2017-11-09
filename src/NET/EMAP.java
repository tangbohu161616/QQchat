package NET;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EMAP<K,V> extends HashMap<K, V> {
	/**
	 * 根据value值删除map中的项
	 * @param value
	 */
	public void removeByValue(Object value){
		for (Object key : keySet()) {
			if(get(key)==value){
				remove(key);
				break;
			}
		}
	}
	/**
	 * 获得所有的value值
	 * @return
	 */
	public Set<V> valueSet(){
		Set<V> result = new HashSet<V>();
		for (K key : keySet()) {
			result.add(get(key));
		}
		return result;
	}
	/**
	 * 通过V值找到K
	 * @param value
	 * @return
	 */
	public K getKeyByValue(V value){
		for (K key : keySet()) {
			if(get(key).equals(value)&&get(key) == value){
				return key;
			}
		}
		return null;
	}
	
	public V put(K key,V value){
		for(V val:valueSet()){
			if(val.equals(value)){
				throw new RuntimeException("EMAP实例中不允许有重复的value");
			}
		}
		return super.put(key, value);
	}
}
