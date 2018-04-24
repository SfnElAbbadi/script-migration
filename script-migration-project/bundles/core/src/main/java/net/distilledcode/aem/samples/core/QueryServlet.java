package net.distilledcode.aem.samples.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Session;


import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

import QueryPackage.*;


@SlingServlet(	paths = "/bin/queryservlet",
				extensions = "html",
				methods = "GET"	)
				
public class QueryServlet extends SlingSafeMethodsServlet{

	private static final long serialVersionUID = 1L;
	List<Node> nodesList = null;

	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException{
		ResourceResolver resourceResolver = request.getResourceResolver();
		Session session = resourceResolver.adaptTo(Session.class);
		nodesList = new ArrayList();
		PrintWriter out = response.getWriter();
		
		// Query parameters
	   	String searchPath = request.getParameter("searchPath");
	   	String queryType = request.getParameter("querytype");
	   	String query = request.getParameter("query");
	   	String operation = request.getParameter("operation");
	   	// Retreiving data from multifield
		String[] names = request.getParameterValues("property-name");
		String[] types = request.getParameterValues("property-type");
		String[] values = request.getParameterValues("property-value");
		String[] Remvalues = request.getParameterValues("remove-property-name");
		
		QueryInterface queryInter = null;
		QueryLangage queryLangage = new QueryLangage();
	   	try {
			
	   		if(query.equals(null) && searchPath.equals(null)) {
	   			query = "select * from [nt:unstructured]";
	   			searchPath = "/content/we-retail/es/es";
	   		}
	   		
		    queryInter = queryLangage.getLangageClass(queryType);
		    nodesList = queryInter.executeQuery(searchPath, queryType, query, resourceResolver);
			
		   	if(operation.equals("add-modify"))
		   		for(int i=0;i<names.length;i++)
		   			queryInter.addModifyProperty(nodesList, names[i], types[i], values[i], resourceResolver);
		   	else if(operation.equals("remove"))
		   		for(int i=0;i<Remvalues.length;i++)
		   			queryInter.removeProperty(nodesList, Remvalues[i], resourceResolver);
		   	
		} catch (Exception e) {
			e.printStackTrace();
		}
	   	
	}	
	   	

	
}
