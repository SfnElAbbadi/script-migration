package net.distilledcode.aem.samples.core;

import java.awt.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

@SlingServlet(	paths = "/bin/queryservlet",
				extensions = "html",
				methods = "GET"	)
				
public class QueryServlet extends SlingSafeMethodsServlet{

	private static final long serialVersionUID = 1L;
	java.util.List<Node> nodesList = new ArrayList();
	
	
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		ResourceResolver resourceResolver = request.getResourceResolver();
		Session session = resourceResolver.adaptTo(Session.class);

	   	String searchPath = request.getParameter("searchPath");
	   	String querytype = request.getParameter("querytype");
	   	String query = request.getParameter("query");
	   	String operation = request.getParameter("operation");
	  	String propertyName = request.getParameter("property-name");
	   	String propertyType = request.getParameter("property-type");
	   	String propertyValue = request.getParameter("property-value");
	   	int counter = Integer.parseInt( request.getParameter("counter") );
		int counterRem = Integer.parseInt( request.getParameter("counterRem") );
		String removePropertyName = request.getParameter("remove-property-name");
	   	try {
			nodesList = executeQuery(searchPath, querytype, query, request.getResourceResolver());
			PrintWriter outprint = response.getWriter();
			outprint.write("This is our counter's value "+counter);
			outprint.write("</br>");
			outprint.write("This is our counter's value "+counterRem);
		   	if(counter == 0 && operation.equals("add-modify"))
		   		addModifyNodesProperty(nodesList, propertyName, propertyType, propertyValue, resourceResolver,outprint);
		   	else if(counterRem == 0 && operation.equals("remove")){
				outprint.write(counter);
		        removeNodesProperty(nodesList, removePropertyName, resourceResolver,outprint);
		   	}else if(counter>0 && operation.equals("add-modify")){

		   		for (int j=0; j<counter; j++){
		   	   		if(request.getParameter("name"+j)!=null){
						 addModifyNodesProperty(nodesList, request.getParameter("name"+j), request.getParameter("type"+j), request.getParameter("value"+j), resourceResolver,outprint);
		   	   			   outprint.write("This is from add-modify block");
		   	   		}
		   	   	}

		   	}else if(counterRem>0 && operation.equals("remove")){
		   		for (int j=0; j<counterRem; j++){
		   	   		if(request.getParameter("name"+j)!=null){
		   	   		   		removeNodesProperty(nodesList, request.getParameter("name"+j), resourceResolver,outprint);
		   	   		}
		   	   	}
		   		int i = 1;
		   		for(Node node : nodesList){
		   		   		outprint.write("Node "+i+" :"+node.getPath());
		   		   	   	outprint.write("</br>");
		   		   	   	i++;
		   		   	}

		   		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	

	   	}	
	   	

	public java.util.List<Node> executeQuery(String searchpath, String querytype, String thequery, ResourceResolver resourceResolver) throws RepositoryException{
		   java.util.List<Node> nodeList = new ArrayList();
		   Query q= null;
		   Session session = resourceResolver.adaptTo(Session.class);
		   QueryManager queryManager = session.getWorkspace().getQueryManager();
		   
		   if(querytype.equals("XPATH")){
			   thequery = ("/jcr:root").concat(searchpath).concat("/").concat(thequery);
			   q = queryManager.createQuery(thequery,Query.XPATH);
		   }else if(querytype.equals("SQL")){
			   if(thequery.contains("where")){
				   thequery = thequery.concat("/").concat(" and isdescendantnode('"+searchpath+"')");
				}else {
					thequery = thequery.concat(" where isdescendantnode('"+searchpath+"')");
				}
			   q = queryManager.createQuery(thequery,Query.SQL);
		   }else if(querytype.equals("JCR_SQL2")){
			   if(thequery.contains("where")){
				   thequery = thequery.concat("/").concat(" and isdescendantnode('"+searchpath+"')");
				}else {
					thequery = thequery.concat(" where isdescendantnode('"+searchpath+"')");
				}
			   q = queryManager.createQuery(thequery,Query.JCR_SQL2);
		   }

		   QueryResult result = q.execute();
		   NodeIterator nodeIter = result.getNodes(); 

		   while ( nodeIter.hasNext() ) {
			    Node node = nodeIter.nextNode();   
				nodeList.add(node);
		   }      

		    return nodeList;
	   	}


	   	public void addModifyNodesProperty(java.util.List<Node> nodList,String removePropertyName, String propertyType, String propertyValue, ResourceResolver resourceResolver, PrintWriter outprint) throws Exception{
	   		Session session = resourceResolver.adaptTo(Session.class);
	   		for(Node node:nodList){
		   		Node nodeTemp = session.getNode(node.getPath());
		   		nodeTemp.setProperty(removePropertyName, propertyValue, Integer.parseInt(propertyType));	
		   		//nodeTemp.save();
		   		session.save();
	   			/* Resource resource = resourceResolver.getResource("/content/we-retail/es/es/jcr:content");
	   			ModifiableValueMap mvm = resource.adaptTo(ModifiableValueMap.class);
	   			mvm.put(propertyName,propertyValue);
	   			resource.getResourceResolver().commit(); */
			   	}
	   		//outprint.write("This is node list size +++ "+nodList.size());
	   		//outprint.write("This is jcr:title property value +++ "+resource.getValueMap().get("jcr:title"));

	   }
	   	   
		public void removeNodesProperty(java.util.List<Node> nodList,String propertyName, ResourceResolver resourceResolver, PrintWriter outprint) throws Exception{
			Session session = resourceResolver.adaptTo(Session.class);
			for(Node node:nodList){
			   		//outprint.write(node.getPath());
					Node nodeTemp = session.getNode(node.getPath());
			   		nodeTemp.getProperty(propertyName).remove();
			   		session.save();
			   	}
	 
	   }
		
	
}
