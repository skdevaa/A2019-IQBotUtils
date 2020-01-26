package com.automationanywhere.botcommand.sk;

import com.automationanywhere.bot.service.GlobalSessionContext;
import com.automationanywhere.botcommand.BotCommand;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import java.lang.ClassCastException;
import java.lang.Deprecated;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class GetNoofFilesCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(GetNoofFilesCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    GetNoofFiles command = new GetNoofFiles();
    if(parameters.get("sessionName") == null || parameters.get("sessionName").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","sessionName"));
    }

    if(parameters.get("li_name") == null || parameters.get("li_name").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","li_name"));
    }

    if(parameters.get("doctype") == null || parameters.get("doctype").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","doctype"));
    }
    if(parameters.get("doctype") != null && parameters.get("doctype").get() != null && !(parameters.get("doctype").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","doctype", "String", parameters.get("doctype").get().getClass().getSimpleName()));
    }
    if(parameters.get("doctype") != null) {
      switch((String)parameters.get("doctype").get()) {
        case "SUCCESS" : {

        } break;
        case "INVALID" : {

        } break;
        case "UNCLASSIFIED" : {

        } break;
        case "UNTRAINED" : {

        } break;
        default : throw new BotCommandException(MESSAGES_GENERIC.getString("generic.InvalidOption","doctype"));
      }
    }

    command.setSessions(sessionMap);
    if(parameters.get("sessionName") != null && parameters.get("sessionName").get() != null && !(parameters.get("sessionName").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","sessionName", "String", parameters.get("sessionName").get().getClass().getSimpleName()));
    }
    if(parameters.get("li_name") != null && parameters.get("li_name").get() != null && !(parameters.get("li_name").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","li_name", "String", parameters.get("li_name").get().getClass().getSimpleName()));
    }
    if(parameters.get("doctype") != null && parameters.get("doctype").get() != null && !(parameters.get("doctype").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","doctype", "String", parameters.get("doctype").get().getClass().getSimpleName()));
    }
    try {
      Optional<Value> result =  Optional.ofNullable(command.action(parameters.get("sessionName") != null ? (String)parameters.get("sessionName").get() : (String)null ,parameters.get("li_name") != null ? (String)parameters.get("li_name").get() : (String)null ,parameters.get("doctype") != null ? (String)parameters.get("doctype").get() : (String)null ));
      logger.traceExit(result);
      return result;
    }
    catch (ClassCastException e) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.IllegalParameters","action"));
    }
    catch (BotCommandException e) {
      logger.fatal(e.getMessage(),e);
      throw e;
    }
    catch (Throwable e) {
      logger.fatal(e.getMessage(),e);
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.NotBotCommandException",e.getMessage()),e);
    }
  }
}
