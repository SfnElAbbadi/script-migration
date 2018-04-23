package net.distilledcode.aem.samples.core;

import QueryPackage.QueryInterface;
import QueryPackage.Sql;
import QueryPackage.Sql2;
import QueryPackage.Xpath;

public class QueryLangage {
	
	   //use getShape method to get object of type shape 
	   public QueryInterface getLangageClass(String queryType){
	      if(queryType == null){
	         return null;
	      }		
	      if(queryType.equalsIgnoreCase("SQL")){
	         return new Sql();
	         
	      } else if(queryType.equalsIgnoreCase("JCR_SQL2")){
	         return new Sql2();
	         
	      } else if(queryType.equalsIgnoreCase("XPATH")){
	         return new Xpath();
	      }
	      
	      return null;
	   }
	}