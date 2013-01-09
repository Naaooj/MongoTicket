package fr.free.naoj.mongoticket.server.entity;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;

import fr.free.naoj.mongoticket.shared.entity.IEntity;

/**
 * <p></p>
 *
 * @author Johann Bernez
 * @param <T>
 */
public abstract class MongoEntity<T> extends BasicDBObject {

	private static final long serialVersionUID = 5136212901638019963L;
	private static final String ID = "_id";
	
	@MongoField(key=ID)
	private ObjectId _id = null;
	
	private Map<String, Field> rel;
	
	public MongoEntity() {
		bind();
	}
	
	/**
	 * 
	 */
	private void bind() {
		try {
			rel = new HashMap<String, Field>();
			
			Field[] fields = getClass().getDeclaredFields();
			MongoField field = null;
			for (Field f : fields) {
				f.setAccessible(true);
				if ((field=f.getAnnotation(MongoField.class)) != null) {
					rel.put(field.key(), f);
				}
			}
			
			rel.put(ID, MongoEntity.class.getDeclaredField(ID));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Field> getRelations() {
		return rel;
	}
	
	/**
	 * 
	 */
	public void update() {
		try {
			MongoField annot = null;
			for (Entry<String, Field> entry : rel.entrySet()) {
				if ((annot=entry.getValue().getAnnotation(MongoField.class)) != null && annot.relation() != IEntity.class) {
					put(entry.getKey(), ((IEntity) entry.getValue().get(this)).get_id());
				} else {
					put(entry.getKey(), entry.getValue().get(this));
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public String get_id() {
		return _id != null ? _id.toString() : null;
	}

	public void set_id(String _id) {
		this._id = new ObjectId(_id);
	}
	
	public ObjectId getId() {
		return this._id;
	}

	protected abstract T createNew();
	
	public T convertTo() {
		T entity = createNew();

		try {
			Field f = null;
			Object value = null;
			List<String> fields = getDeclaredFields();
			for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(getClass()).getPropertyDescriptors()) {
				if (fields.contains(propertyDescriptor.getName())) {
					if (propertyDescriptor.getName().equals(ID)) {
						f = getClass().getSuperclass().getDeclaredField(propertyDescriptor.getName());
					} else {
						f = getClass().getDeclaredField(propertyDescriptor.getName());
					}
					if (f != null) {
						value = propertyDescriptor.getReadMethod().invoke(this, new Object[]{});
						entity.getClass().getMethod(propertyDescriptor.getWriteMethod().getName(), propertyDescriptor.getWriteMethod().getParameterTypes()).invoke(entity, new Object[]{value});
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
	
	public void convertFrom(T entity) {
		if (entity == null) {
			return;
		}
		try {
			Field f = null;
			Object value = null;
			List<String> fields = getDeclaredFields();
			for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(getClass()).getPropertyDescriptors()) {
				if (fields.contains(propertyDescriptor.getName())) {
					if (propertyDescriptor.getName().equals(ID)) {
						f = getClass().getSuperclass().getDeclaredField(propertyDescriptor.getName());
					} else {
						f = getClass().getDeclaredField(propertyDescriptor.getName());
					}
					if (f != null) {
						value = entity.getClass().getMethod(propertyDescriptor.getReadMethod().getName(), propertyDescriptor.getReadMethod().getParameterTypes()).invoke(entity, new Object[]{});
						if (value == null && propertyDescriptor.getName().equals(ID)) {
							continue;
						}
						propertyDescriptor.getWriteMethod().invoke(this, new Object[]{value});
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		update();
	}
	
	private List<String> getDeclaredFields() {
		List<String> fields = new ArrayList<String>();
		for (Field field : rel.values()) {
			if (field.getAnnotation(MongoField.class) != null) {
				fields.add(field.getName());
			}
		}
		return fields;
	}
}
