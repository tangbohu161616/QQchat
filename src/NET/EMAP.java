package NET;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EMAP<K,V> extends HashMap<K, V> {
	/**
	 * ����valueֵɾ��map�е���
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
	 * ������е�valueֵ
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
	 * ͨ��Vֵ�ҵ�K
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
				throw new RuntimeException("EMAPʵ���в��������ظ���value");
			}
		}
		return super.put(key, value);
	}
}
