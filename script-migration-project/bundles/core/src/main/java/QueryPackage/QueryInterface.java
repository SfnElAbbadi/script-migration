package QueryPackage;

import java.util.List;
import javax.jcr.Node;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * <b>QueryInterface is an interface that contains methods that add, modify, and delete node properties, and execute the query.</b>
 * 
 * @author AEM SQLI Oujda.
 * @version 1.0
 */
public interface QueryInterface {
	
	// **************************************************
    // executeQuery method
    // **************************************************
    /**
     * return a list of nodes.<br>
     * 
     * @param searchPath to successfully execute the query.
     * @param queryType It can be SQL, SQL2, XPath.
     * @param theQuery is the query that will select the specific nodes inside searchPath.
     * @return a list of nodes.
     */
	List<Node> executeQuery(String searchPath, String queryType, String theQuery, ResourceResolver resourceResolver);
	
	// **************************************************
    // addModifyProperty method
    // **************************************************
    /**
     * this method contains two features either add a new property in a node or modify a property of a node already exist.<br>
     * @param nodList contains a list of nodes where the functionality of adding and modifying is applied.
     * @param removePropertyName the name of the property that one needs to add or modify.
     * @param propertyType the type of the property that one needs to add or modify.
     * @param propertyValue the value of the property that one needs to add or modify.
     */
	void addModifyProperty(List<Node> nodList,String removePropertyName, String propertyType, String propertyValue, ResourceResolver resourceResolver);
	
	// **************************************************
    // removeProperty method
    // **************************************************
    /**
     * this method is to remove the properties of the nodes.<br>
     * @param nodesList contains a list of nodes where the functionality of removing is applied.
     * @param removePropertyName the name of the property that we need to delete.
     */
	void removeProperty(List<Node> nodesList, String removePropertyName,ResourceResolver resourceResolver);
}
