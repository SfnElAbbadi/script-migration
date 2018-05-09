package net.distilledcode.aem.samples.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.security.AccessControlManager;
import javax.jcr.security.Privilege;

import org.apache.felix.scr.annotations.sling.SlingServlet;

import org.apache.jackrabbit.api.security.JackrabbitAccessControlList;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.commons.jackrabbit.authorization.AccessControlUtils;
import org.apache.jackrabbit.oak.spi.security.privilege.PrivilegeConstants;

import org.apache.sling.jcr.base.util.AccessControlUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

import QueryPackage.*;

/**
 * <b>QueryServlet is the class representing one of the functions of adding, modifying or removing the properties of the nodes.</b>
 * <p>
 * one of the features of this class is characterized by the following information :
 * <ul>
 * <li>searchPath to know well the path where we apply one of the features of this class.</li>
 * <li>queryType It can be SQL, SQL2, XPath.</li>
 * <li>query is the query that will select the specific nodes inside searchPath.</li>
 * <li>operation it can be Add, Edit or Delete.</li>
 * <li>names is an array of strings that contains the names of properties that can add or modify if already exists.</li>
 * <li>types it can be String, Boolean, Decimal, etc.</li>
 * <li>values is an array contains property values that can be added or changed if they already exist.</li>
 * <li>RemValues is an array contains the names of properties that can be deleted</li>
 * </ul>
 * </p>
 * 
 * @see List
 * @author AEM SQLI Oujda.
 * @version 1.0
 */

@SlingServlet(	paths = "/bin/queryservlet",
				extensions = "html",
				methods = "GET"	)
				
public class QueryServlet extends SlingSafeMethodsServlet{

	private static final long serialVersionUID = 1L;
	List<Node> nodesList = null;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException{
		/**
         * The resourceResolver defines the service API which may be used to resolve Resource objects.
         */
		ResourceResolver resourceResolver = request.getResourceResolver();
		/**
         * The session object provides read and write access to the content of a particular workspace in the repository.
         */
		Session session = resourceResolver.adaptTo(Session.class);
		nodesList = new ArrayList();
		
	   	String searchPath = request.getParameter("searchPath");
	   	String queryType = request.getParameter("querytype");
	   	String query = request.getParameter("query");
	   	String operation = request.getParameter("operation");
	   	String groupeId = request.getParameter("groupeId");
	   	
		String[] names = request.getParameterValues("property-name");
		String[] types = request.getParameterValues("property-type");
		String[] values = request.getParameterValues("property-value");
		String[] Remvalues = request.getParameterValues("remove-property-name");
		
		QueryInterface queryInter = null;
		QueryLangage queryLangage = new QueryLangage();
	   	try {
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
	   	
	   	try {
	   		if (session != null) {
			    // Set ACLs
			    final AccessControlManager accessControlManager = session.getAccessControlManager();
			    final UserManager userManager = AccessControlUtil.getUserManager(session);
			        if (userManager != null) {
			            List<Privilege> allowPrivileges = new ArrayList<Privilege>(); 
			            //Add rights
			            allowPrivileges.add(accessControlManager.privilegeFromName(PrivilegeConstants.JCR_MODIFY_ACCESS_CONTROL));
			            Privilege[] allowPrivilegesArray = new Privilege[allowPrivileges.size()];
			            allowPrivilegesArray = allowPrivileges.toArray(allowPrivilegesArray);
			            for(Node node:nodesList) {
			            	//String test = node.getPath();
			            	JackrabbitAccessControlList acl = AccessControlUtils.getAccessControlList(session, node.getPath());
			                 if (acl != null) {
			                    final Authorizable authorizableContentProducers = userManager.getAuthorizable(groupeId);
			                    if (authorizableContentProducers != null) {
			                    	acl.addEntry(authorizableContentProducers.getPrincipal(), allowPrivilegesArray, true);
			                    }
			                    accessControlManager.setPolicy(node.getPath(), acl);
			                 }
			            }
			        }
			        if (session.hasPendingChanges()) {
			             session.save();
			        }
	   		}
	   	} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}