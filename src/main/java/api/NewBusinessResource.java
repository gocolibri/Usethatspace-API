package api;

import auth.AuthenticatableResource;
import auth.Password;
import auth.Token;
import com.google.gson.Gson;
import entities.Business;
import entities.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import persistence.BusinessDAO;
import persistence.UserDAO;

import java.io.IOException;

@Slf4j
public class NewBusinessResource extends LoggableResource{
    @Get
    public String represent() {
        return "New Business";
    }

    @Post
    public Representation acceptItem(Representation entity) throws JSONException, IOException {
        //Get me the JSON
        JSONObject json = new JsonRepresentation(entity).getJsonObject();

        //Log it
        logRequest(json.toString());

        processJSON(json);

        return getResponseRepresentation(true, "User data updated");
    }

    public void processJSON(JSONObject json) throws JSONException {
        Gson gson = new Gson();

        Business newBusiness = gson.fromJson(json.toString(), Business.class);
        BusinessDAO businessDAO = new BusinessDAO();

        businessDAO.save(newBusiness);
    }

    private JsonRepresentation getResponseRepresentation(boolean success, String message){
        JSONObject object = new JSONObject();
        try {
            object.put("operation_success", success);
            object.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        log.info("Response to client was " + " sucess: " + success + " message: " + message);

        return new JsonRepresentation(object);
    }

}
