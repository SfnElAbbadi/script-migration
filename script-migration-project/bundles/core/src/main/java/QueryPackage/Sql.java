package QueryPackage;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.sling.api.resource.ResourceResolver;

public class Sql implements	QueryInterface{

	public List<Node> executeQuery(String searchPath, String queryType, String theQuery, ResourceResolver resourceResolver) {
		
		List<Node> nodeList = new ArrayList();
		Query q= null;
		Session session = resourceResolver.adaptTo(Session.class);
		QueryManager queryManager;
		try {
			queryManager = session.getWorkspace().getQueryManager();
			if(queryType.equals("SQL")){
				   if(theQuery.contains("where")){
					   theQuery = theQuery.concat("/").concat(" and isdescendantnode('"+searchPath+"')");
					}else {
						theQuery = theQuery.concat(" where isdescendantnode('"+searchPath+"')");
					}
				   q = queryManager.createQuery(theQuery,Query.SQL);
			}

			QueryResult result = q.execute();
			NodeIterator nodeIter = result.getNodes(); 

			while ( nodeIter.hasNext() ) {
				Node node = nodeIter.nextNode();   
				nodeList.add(node);
			}      

			return nodeList;
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		   
	}

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   		
		}
		
	}

	public void removeProperty(List<Node> nodesList, String removePropertyName, ResourceResolver resourceResolver) {
		
		Session session = resourceResolver.adaptTo(Session.class);
		for(Node node:nodesList){
				Node nodeTemp;
				try {
					nodeTemp = session.getNode(node.getPath());
					nodeTemp.getProperty(removePropertyName).remove();
			   		session.save();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   		
		   	}

	}

}
