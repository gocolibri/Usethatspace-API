package auth;


import api.LoggableResource;
import entities.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import persistence.UserDAO;

@Slf4j
public class AuthenticatableResource extends LoggableResource {

	@SneakyThrows
	public boolean authenticateRequest(JSONObject json){

		try {
			JSONObject authData;
			
			if(json.has("auth_data"))
				authData = json.getJSONObject("auth_data");
			else
				return false;
			
			if(authData.has("username") && authData.has("device_id") && authData.has("token")) {
				String username = authData.getString("username");
				String token = authData.getString("token");

				log.info("Authenticating " + username);

				UserDAO dao = new UserDAO();
				User user = dao.findByUsername(username);

				if(user == null)
					return false;
			}
			else
				return false;
			} catch (JSONException e) {
				e.printStackTrace();
			} 
		

		
		return false;
	}
	
	public JsonRepresentation authenticationError(){
		JSONObject object = new JSONObject();
		try {
			object.put("auth_result", false);
			object.put("message", "Invalid auth token");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		log.warn("Device authentication failed");
		return new JsonRepresentation(object);
	}
}
