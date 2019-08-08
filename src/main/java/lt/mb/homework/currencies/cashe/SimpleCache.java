package lt.mb.homework.currencies.cashe;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

public class SimpleCache {
	private static Map<String, EntryContainer<?>> cashe = new Hashtable<String, EntryContainer<?>>();

	public static <T> void set(String key, T entry) {
		set(key, entry, null);
	}

	public static <T> void set(String key, T entry, Date end) {
		EntryContainer<T> eContainer = new EntryContainer<T>();
		eContainer.setBegin(new Date());
		eContainer.setEnd(end);
		eContainer.setEntry(entry);
		cashe.put(key, eContainer);
	}

	public static <T> T get(String key, Class<T> clazz) {
		@SuppressWarnings("unchecked")
		EntryContainer<T> eContainer = (EntryContainer<T>) cashe.get(key);
		if (eContainer == null) {
			return null;
		}
		if (eContainer.getEnd() == null) {
			return eContainer.getEntry();
		}
		if (eContainer.getEnd().getTime() <= System.currentTimeMillis()) {
			clear(key);
			return null;
		} else {
			return eContainer.getEntry();
		}
	}

	private static void clear(String key) {
		cashe.remove(key);
	}
}

class EntryContainer<T> {
	private Date begin;
	private Date end;
	private T entry;

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public T getEntry() {
		return entry;
	}

	public void setEntry(T entry) {
		this.entry = entry;
	}

}
