
package com.sample;

import java.awt.Dimension;

import javax.swing.UIManager;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.logger.*;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import javax.swing.JOptionPane; 

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
        	
        	UIManager.put("OptionPane.minimumSize",new Dimension(400,150)); 
        	
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");
			KieRuntimeLogger kLogger = ks.getLoggers().newFileLogger(kSession, "test");

            // go !
            kSession.fireAllRules();
            
            QueryResults answers = kSession.getQueryResults( "answers" );
            QueryResults results = kSession.getQueryResults( "result" );
            
            String answerPool = "<html><font size = 5><b>";
            
            for ( QueryResultsRow row : answers ) {
            	answerPool = answerPool + "<html><font size = 5><b>" + (String) row.get("$answer");
            }
            
            String resultsPool = "<html><font size = 6><b>";
            //Should be only 1 result for now
            for ( QueryResultsRow row : results ) {
            	resultsPool = resultsPool + (String) row.get("$result");
            }
            resultsPool = resultsPool + "</b></font></html>";
            
            
            JOptionPane.showMessageDialog(null, "<html><font size = 7><b> According to these answers: </b></font></html> \n" + answerPool + "\n<html><font size = 7><b> We recommend: </b></font></html>\n" + resultsPool, "Results", JOptionPane.INFORMATION_MESSAGE);

			kLogger.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

 

}
