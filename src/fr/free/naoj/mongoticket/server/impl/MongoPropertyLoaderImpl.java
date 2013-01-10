package fr.free.naoj.mongoticket.server.impl;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;

import com.google.inject.Inject;

import fr.free.naoj.mongoticket.server.MongoPropertyLoader;
import fr.free.naoj.mongoticket.shared.Constant.ExceptionCode;
import fr.free.naoj.mongoticket.shared.exception.InternalException;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class MongoPropertyLoaderImpl implements MongoPropertyLoader {
	
	private static final String PROPERTY_PARAMETER = "MongoProperties";
	
	@Inject
	private ServletContext servletContext;

	private Properties properties = null; 
	
	@Override
	public void load() throws InternalException {
		properties = new Properties();
		
		try {
			properties.load(servletContext.getResourceAsStream(servletContext.getInitParameter(PROPERTY_PARAMETER)));
		} catch (IOException e) {
			throw new InternalException(ExceptionCode.CONFIGURATION);
		}
	}

	@Override
	public String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}
}
