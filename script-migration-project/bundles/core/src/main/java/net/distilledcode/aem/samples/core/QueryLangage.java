package net.distilledcode.aem.samples.core;

import queryPackage.QueryInterface;
import queryPackage.Sql;
import queryPackage.Sql2;
import queryPackage.Xpath;

/**
 * <b>QueryLanguage is a calss that does the test on query type.</b>
 * 
 * @author AEM SQLI Oujda.
 * @version 1.0
 */
public class QueryLangage {
	
	  // **************************************************
      // Public methods
      // **************************************************
      /**
       * return a new object with the content you have passed in the test if true.<br>
       * 
       * @param  queryType is the list contains SQL, SQL2 and XPath whose elements are tested.
       * @return a new object with the content you have passed in the test if true.
       */
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