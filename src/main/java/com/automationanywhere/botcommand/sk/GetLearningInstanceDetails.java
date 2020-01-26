/*
 * Copyright (c) 2019 Automation Anywhere.
 * All rights reserved.
 *
 * This software is the proprietary information of Automation Anywhere.
 * You shall use it only in accordance with the terms of the license agreement
 * you entered into with Automation Anywhere.
 */
/**
 * 
 */
package com.automationanywhere.botcommand.sk;



import static com.automationanywhere.commandsdk.model.AttributeType.TEXT;
import static com.automationanywhere.commandsdk.model.DataType.STRING;


import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.DictionaryValue;
import com.automationanywhere.botcommand.data.impl.NumberValue;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;

import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.Sessions;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.commandsdk.model.DataType;
import com.automationanywhere.commandsdk.annotations.Execute;


/**
 * @author Stefan Karsten
 *
 */

@BotCommand
@CommandPkg(label = "Details of a Learning Instance", name = "LIDetails",
        description = "Details of a Learning Instance",
        node_label = "Details of a Learning Instance {{sessionName}}|", icon = "",
        return_type=DataType.DICTIONARY, return_sub_type=DataType.NUMBER, return_label="Details", return_required=true)
public class GetLearningInstanceDetails {
	
	  private static final Logger logger = LogManager.getLogger(GetLearningInstanceDetails.class);
	
    @Sessions
    private Map<String, Object> sessions;
    
    private static final Messages MESSAGES = MessagesFactory
			.getMessages("com.automationanywhere.botcommand.demo.messages");
	
	   
	@Execute
    public  DictionaryValue action(@Idx(index = "1", type = TEXT)  @Pkg(label = "Session name" , default_value_type = STRING) @NotEmpty String sessionName ,
    					      @Idx(index = "2", type = TEXT)  @Pkg(label = "Name" , default_value_type = STRING) @NotEmpty String li_name ) throws Exception
     {
		
		HashMap<String,Integer> details = new HashMap<String,Integer>();
	    IQBotConnection connection  = (IQBotConnection) this.sessions.get(sessionName);  
	    
	    HashMap<String,String> lis = Utils.getLearningInstances(connection);
	   
	    if(lis.containsKey(li_name)) {
	    	details = Utils.getLearningInstanceDetails(connection, lis.get(li_name)) ;
	    }
	    
		HashMap<String,Value> map = new HashMap();
	    details.forEach((k,v)->{
	        map.put(k, new NumberValue(v));
	    });
	   
	    DictionaryValue dictmap = new DictionaryValue();
	    dictmap.set(map);
	    return dictmap;

     
     }
	
	
    public void setSessions(Map<String, Object> sessions) {
        this.sessions = sessions;
    }
    
		
	
}