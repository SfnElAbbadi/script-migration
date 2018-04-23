package QueryPackage;

import java.io.PrintWriter;
import java.util.List;

import javax.jcr.Node;

import org.apache.sling.api.resource.ResourceResolver;

public interface QueryInterface {

	
	List<Node> executeQuery(String searchPath, String queryType, String theQuery, ResourceResolver resourceResolver);
	void addModifyProperty(List<Node> nodList,String removePropertyName, String propertyType, String propertyValue, ResourceResolver resourceResolver);
	void removeProperty(List<Node> nodesList, String removePropertyName,ResourceResolver resourceResolver);
}
