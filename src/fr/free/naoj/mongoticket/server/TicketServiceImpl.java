package fr.free.naoj.mongoticket.server;

import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.bson.types.ObjectId;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import fr.free.naoj.mongoticket.client.TicketService;
import fr.free.naoj.mongoticket.server.entity.DeploymentEntity;
import fr.free.naoj.mongoticket.server.entity.MongoEntity;
import fr.free.naoj.mongoticket.server.entity.MongoField;
import fr.free.naoj.mongoticket.server.entity.TicketEntity;
import fr.free.naoj.mongoticket.shared.entity.Deployment;
import fr.free.naoj.mongoticket.shared.entity.IEntity;
import fr.free.naoj.mongoticket.shared.entity.Ticket;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class TicketServiceImpl extends RemoteServiceServlet implements TicketService {

	private static final long serialVersionUID = 2679955228130988518L;
	
	private static final String DEPLOYMENTS_COLLECTION = "deployments";
	private static final String TICKETS_COLLECTION = "tickets";
	
	private static final String MONGO_ID = "_id";
	
	@Override
	public Ticket getTicket(String ticketId) {
		if (ticketId != null) {
			DB db = null;
			if ((db=getDB()) != null) {
				DBCollection collection = db.getCollection(TICKETS_COLLECTION);
				collection.setObjectClass(TicketEntity.class);
				BasicDBObject query = new BasicDBObject(MONGO_ID, new ObjectId(ticketId));
				TicketEntity entity = (TicketEntity) collection.findOne(query);
				loadEntity(db, entity);
				return entity != null ? entity.convertTo() : null;
			}
		}
		return null;
	}
	
	@Override
	public List<Ticket> getTickets(String filterId) {
		List<Ticket> tickets = Collections.emptyList();
		
		DB db = null;
		if ((db=getDB()) != null) {
			DBCollection collection = db.getCollection(TICKETS_COLLECTION);
			collection.setObjectClass(TicketEntity.class);
			DBCursor collectionCursor = filterId != null ? collection.find(new BasicDBObject("deployment", filterId)) : collection.find();
			if (collectionCursor != null) {
				tickets = new ArrayList<Ticket>(collectionCursor.size());
				TicketEntity t = null;
				while (collectionCursor.hasNext()) {
					t = (TicketEntity) collectionCursor.next();
					loadEntity(db, t);
					tickets.add(t.convertTo());
				}
			}
		}
		
		return tickets;
	}

	@Override
	public void deleteTicket(Ticket ticket) {
		if (ticket != null) {
			DB db = getDB();
			TicketEntity entity = new TicketEntity();
			entity.convertFrom(ticket);
			db.getCollection(TICKETS_COLLECTION).remove(entity);
		}
	}

	@Override
	public void saveTicket(Ticket ticket) {
		if (ticket != null) {
			TicketEntity entity = new TicketEntity();
			entity.convertFrom(ticket);
			// Insertion
			if (entity.get_id() == null) {
				getDB().getCollection(TICKETS_COLLECTION).insert(entity);
			} else { // Update
				BasicDBObject query = new BasicDBObject();
				query.put(MONGO_ID, new ObjectId(ticket.get_id()));
				getDB().getCollection(TICKETS_COLLECTION).update(query, entity);
			}
		}
	}

	@Override
	public Deployment getDeployment(String deploymentId) {
		if (deploymentId != null) {
			DB db = null;
			if ((db=getDB()) != null) {
				DBCollection collection = db.getCollection(DEPLOYMENTS_COLLECTION);
				collection.setObjectClass(DeploymentEntity.class);
				BasicDBObject query = new BasicDBObject(MONGO_ID, new ObjectId(deploymentId));
				DeploymentEntity entity = (DeploymentEntity) collection.findOne(query);
				loadEntity(db, entity);
				return entity != null ? entity.convertTo() : null;
			}
		}
		return null;
	}

	@Override
	public List<Deployment> getDeployments(String filterId) {
		List<Deployment> deployments = Collections.emptyList();
		
		DB db = null;
		if ((db=getDB()) != null) {
			DBCollection collection = db.getCollection(DEPLOYMENTS_COLLECTION);
			collection.setObjectClass(DeploymentEntity.class);
			DBCursor collectionCursor = filterId != null ? collection.find(new BasicDBObject(MONGO_ID, new ObjectId(filterId))) : collection.find();
			collectionCursor.sort(new BasicDBObject("deployedDate", -1));
			if (collectionCursor != null) {
				deployments = new ArrayList<Deployment>(collectionCursor.size());
				DeploymentEntity t = null;
				while (collectionCursor.hasNext()) {
					t = (DeploymentEntity) collectionCursor.next();
					loadEntity(db, t);
					deployments.add(t.convertTo());
				}
			}
		}
		
		return deployments;
	}

	@Override
	public void deleteDeployment(Deployment deployment) {
		if (deployment != null) {
			DB db = getDB();
			DeploymentEntity entity = new DeploymentEntity();
			entity.convertFrom(deployment);
			BasicDBObject query = new BasicDBObject();
			query.put("deployment", entity.get_id());
			db.getCollection(TICKETS_COLLECTION).remove(query);
			db.getCollection(DEPLOYMENTS_COLLECTION).remove(entity);
		}
	}

	@Override
	public void saveDeployment(Deployment deployment) {
		if (deployment != null) {
			DeploymentEntity entity = new DeploymentEntity();
			entity.convertFrom(deployment);
			// Insertion
			if (entity.get_id() == null) {
				getDB().getCollection(DEPLOYMENTS_COLLECTION).insert(entity);
			} else { // Update
				BasicDBObject query = new BasicDBObject();
				query.put(MONGO_ID, new ObjectId(deployment.get_id()));
				getDB().getCollection(DEPLOYMENTS_COLLECTION).update(query, entity);
			}
		}
	}
	
	private void loadEntity(DB db, MongoEntity<?> entity) {
		try {
			MongoField annot = null;
			for (Entry<String, Field> entry : entity.getRelations().entrySet()) {
				if (entity.get(entry.getKey()) != null) {
					entry.getValue().setAccessible(true);
					// On ne set pas l'entitée liée, il faut la charger à part...
					if ((annot=entry.getValue().getAnnotation(MongoField.class)) != null && annot.relation() != IEntity.class) {
						String relId = (String) entity.get(entry.getKey());
						DBCollection col = db.getCollection(DEPLOYMENTS_COLLECTION);
						col.setObjectClass(annot.relation());
						MongoEntity<?> rel = (MongoEntity<?>) col.findOne(new BasicDBObject(MONGO_ID, new ObjectId(relId)));
						loadEntity(db, rel);
						entry.getValue().set(entity, rel);
					} else {
						entry.getValue().set(entity, entity.get(entry.getKey()));
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	private DB getDB() {
		DB dbConnection = null;
		try {
			Mongo m = new Mongo("localhost");
		
			dbConnection = m.getDB("mongoticket");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return dbConnection;
	}
}
