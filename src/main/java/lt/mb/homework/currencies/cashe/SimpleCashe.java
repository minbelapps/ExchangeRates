package lt.mb.homework.currencies.cashe;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

public class SimpleCashe {
	private static Map<String, EntryContainer<?>> cashe = new Hashtable<String, EntryContainer<?>>();

	public static <T> void set(String key, T entry) {
		set(key, entry, null);
	}

	public static <T> void set(String key, T entry, Date end) {
		EntryContainer<T> econtainer = new EntryContainer<T>();
		econtainer.setBegin(new Date());
		econtainer.setEnd(end);
		econtainer.setEntry(entry);
		cashe.put(key, econtainer);
	}

	public static <T> T get(String key, Class<T> clazz) {
		@SuppressWarnings("unchecked")
		EntryContainer<T> econtainer = (EntryContainer<T>) cashe.get(key);
		if (econtainer == null) {
			return null;
		}
		if (econtainer.getEnd() == null) {
			return econtainer.getEntry();
		}
		if (econtainer.getEnd().getTime() <= System.currentTimeMillis()) {
			clear(key);
			return null;
		} else {
			return econtainer.getEntry();
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
