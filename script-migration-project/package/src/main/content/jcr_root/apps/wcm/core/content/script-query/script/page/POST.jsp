
<%--
  ADOBE CONFIDENTIAL

  Copyright 2012 Adobe Systems Incorporated
  All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Adobe Systems Incorporated and its suppliers,
  if any.  The intellectual and technical concepts contained
  herein are proprietary to Adobe Systems Incorporated and its
  suppliers and may be covered by U.S. and Foreign Patents,
  patents in process, and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden unless prior written permission is obtained
  from Adobe Systems Incorporated.
--%><%@page import="javax.jcr.Property"%>
<%@page import="org.apache.sling.api.resource.ModifiableValueMap"%>
<%@page import="com.adobe.granite.ui.components.ds.SimpleDataSource"%>
<%@page import="com.adobe.granite.ui.components.ds.DataSource"%>
<%@page import="org.apache.sling.api.resource.ResourceMetadata"%>
<%@page import="com.adobe.granite.ui.components.ds.ValueMapResource"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.sling.api.wrappers.ValueMapDecorator"%>
<%@page import="javax.jcr.NodeIterator"%>
<%@page import="javax.jcr.query.QueryResult"%>
<%@page import="javax.jcr.query.Query"%>
<%@page import="com.day.util.ExecutionContext"%>
<%@page import="org.apache.sling.api.SlingHttpServletRequest"%>
<%@page import="org.apache.sling.api.resource.ResourceResolverFactory"%>
<%@page import="javax.jcr.query.QueryManager"%>
<%@page import="javax.jcr.Session"%>
<%@page import="com.day.cq.search.PredicateGroup"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.io.PrintWriter"%>
<%
%><%@include file="/libs/granite/ui/global.jsp"%><%
%><%@page session="false"
          import="java.util.ArrayList,
                  java.util.Enumeration,
                  java.util.List,
                  java.util.Locale,
                  javax.jcr.Node,
                  javax.jcr.PropertyType,
                  org.apache.jackrabbit.util.Text,
                  org.apache.sling.api.resource.Resource,
                  org.apache.sling.api.resource.ResourceResolver,
                  org.apache.sling.api.resource.ValueMap,
                  org.apache.sling.servlets.post.SlingPostConstants,
                  com.day.cq.commons.servlets.HtmlStatusResponseHelper,
                  org.apache.sling.api.servlets.HtmlResponse,
                  com.day.cq.tagging.Tag,
                  com.day.cq.tagging.TagManager,
                  com.day.cq.wcm.api.Page,
                  com.day.cq.wcm.api.PageManager,
                  com.day.cq.commons.jcr.JcrUtil"%><%

   String searchPath = request.getParameter("searchPath");
   String queryType = request.getParameter("querytype");
   String query = request.getParameter("query");
   String operation = request.getParameter("operation");
   String propertyName = request.getParameter("property-name");
   String propertyType = request.getParameter("property-type");
   String propertyValue = request.getParameter("property-value");
   String removePropertyName = request.getParameter("remove-property-name");
   List<Node> nodesList = new ArrayList();
   nodesList = executeQuery(searchPath, queryType, query);
   
   if(operation == "add-modify"){
	   addModifyNodesProperty(nodesList, propertyName, propertyType, propertyValue);
   }else if(operation == "remove"){
	   removeNodesProperty(nodesList, propertyName);
   }
   %>
   <%!	
   public List<Node> executeQuery(String searchpath, String querytype, String query){
	   List<Node> nodeList = new ArrayList();
	   if(query.contains("where")){
			query = query.concat(" and isdescendantnode('"+searchpath+"')");
		}else {
			//query += " where isdescendantnode('/content/')";
			query = query.concat(" where isdescendantnode('"+searchpath+"')");

		}
	   try
	   {
			Session session = resourceResolver.adaptTo(Session.class);
			QueryManager queryManager = session.getWorkspace().getQueryManager();
			Query q = queryManager.createQuery(query,querytype);
			QueryResult result = q.execute();
			NodeIterator nodeIter = result.getNodes(); 
			while ( nodeIter.hasNext() ) {
				Node node = nodeIter.nextNode();   
				nodeList.add(node);
			}      
			// Log out
			 session.logout();
			 return nodeList;
   		}
   		catch(Exception e)
   		{
    		e.printStackTrace();
   		}
   			return null;
   	}


   	public void addModifyNodesProperty(List<Node> nodList,String propertyName, String propertyType, String propertyValue){
	   
	   for(Node node:nodList){
		   	Session session = resourceResolver.adaptTo(Session.class);
	   		Node nodeTemp = session.getNode(node.getPath());
	   		nodeTemp.setProperty(propertyName, propertyValue, Integer.parseInt(propertyType));	

		   	}

   }
   
	public void removeNodesProperty(List<Node> nodList,String propertyName){
	   
	   for(Node node:nodList){
		   		Session session = resourceResolver.adaptTo(Session.class);
		   		Node nodeTemp = session.getNode(node.getPath());
		   		nodeTemp.getProperty(propertyName).remove();
		   	}
 
   }
%>

