package introsde.finalproject.quotes;

import java.net.URI;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;

@Path("/quote/random")
public class Service {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String quotePlain() {
		return randomQuote();
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public String quoteXML() {
		return "<?xml version=\"1.0\"?>\n" + "<quote>" + randomQuote() + "</quote>";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String quoteJson() {
		return "{ 'quote': '" + randomQuote() + "' }";
	}

	private String randomQuote() {
		try {
			ClientConfig clientConfig = new ClientConfig();
			Client client = ClientBuilder.newClient(clientConfig);
			WebTarget service = client.target(getRemoteURI());

			String stringResult = service.queryParam("method", "getQuote")
										 .queryParam("format", "json")
										 .queryParam("lang", "en")
										 .request().get(String.class).replaceAll("\'", "\\u0027");

			ObjectMapper m = new ObjectMapper();
			HashMap<String,String> result = new HashMap<>();
			result = m.readValue(stringResult, HashMap.class);
			String quote = result.get("quoteText");
			String author = result.get("quoteAuthor");
			
			return (author.equals("")) ? quote : quote + " (" + author + ")";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private URI getRemoteURI() {
		return UriBuilder.fromUri("http://api.forismatic.com/api/1.0/").build();
	}
}
