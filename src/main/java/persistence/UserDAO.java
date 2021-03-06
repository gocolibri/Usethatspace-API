package persistence;

import entities.User;
import com.google.code.morphia.dao.BasicDAO;
import org.bson.types.ObjectId;

public class UserDAO extends BasicDAO<User, ObjectId> {
	public UserDAO() {
		super(User.class, MongoConnectionManager.instance().getDb());
	}
	
	public User findByUsername(String username){
		return ds.createQuery(User.class).field("username").equal(username).get();
	}
}
