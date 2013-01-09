package fr.free.naoj.mongoticket.server.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import fr.free.naoj.mongoticket.shared.entity.IEntity;


/**
 * <p></p>
 *
 * @author Johann Bernez
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MongoField {

	/** Field name, must be single and defined */
	String key();
	
	/** The kind off object linked with */
	Class<? extends IEntity> relation() default IEntity.class;
}
