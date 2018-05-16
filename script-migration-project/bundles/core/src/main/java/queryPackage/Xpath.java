package queryPackage;

import java.util.ArrayList;
import java.util.List;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * Xpath is a class that implements QueryInterface and overrides their method. 
 *
 * @author AEM SQLI.
 * @version 1.0
 */
public class Xpath implements QueryInterface{
	
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
	public List<Node> executeQuery(String searchPath, String queryType, String theQuery, ResourceResolver resourceResolver) {
		
		List<Node> nodeList = new ArrayList();
		Query q= null;
		Session session = resourceResolver.adaptTo(Session.class);
		QueryManager queryManager;
		try {
			queryManager = session.getWorkspace().getQueryManager();
			if(queryType.equals("XPATH")){
				   theQuery = ("/jcr:root").concat(searchPath).concat("/").concat(theQuery);
				   q = queryManager.createQuery(theQuery,Query.XPATH);
			}

			QueryResult result = q.execute();
			NodeIterator nodeIter = result.getNodes(); 

			while ( nodeIter.hasNext() ) {
				Node node = nodeIter.nextNode();   
				nodeList.add(node);
			}      
			return nodeList;
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// **************************************************
    // addModifyProperty method
    // **************************************************
    /**
     * this method contains two features either add a new property in a node or modify a property of a node already exist.<br>
     * @param nodList contains a list of nodes where the functionality of adding and modifying is applied.
     * @param PropertyName the name of the property that one needs to add or modify.
     * @param propertyType the type of the property that one needs to add or modify.
     * @param propertyValue the value of the property that one needs to add or modify.
     */
	public void addModifyProperty(List<Node> nodList, String removePropertyName, String propertyType,
			String propertyValue, ResourceResolver resourceResolver) {
		
		Session session = resourceResolver.adaptTo(Session.class);
   		for(Node node:nodList){
	   		Node nodeTemp;
			try {
				nodeTemp = session.getNode(node.getPath());	
				nodeTemp.setProperty(removePropertyName, propertyValue, Integer.parseInt(propertyType));	
		   		session.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	// **************************************************
    // removeProperty method
    // **************************************************
    /**
     * this method is to remove the properties of the nodes.<br>
     * @param nodesList contains a list of nodes where the functionality of removing is applied.
     * @param removePropertyName the name of the property that we need to delete.
     */
	public void removeProperty(List<Node> nodesList, String removePropertyName, ResourceResolver resourceResolver) {
		
		Session session = resourceResolver.adaptTo(Session.class);
		for(Node node:nodesList){
				Node nodeTemp;
				try {
					nodeTemp = session.getNode(node.getPath());
					nodeTemp.getProperty(removePropertyName).remove();
			   		session.save();
				} catch (Exception e) {
					e.printStackTrace();
				}		   		
		   	}
	}
}