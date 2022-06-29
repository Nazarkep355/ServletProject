package com.example.finalproject3.jstl;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Locale;
import java.util.ResourceBundle;

public class LangOut extends TagSupport {
    private String key;
    private boolean toLowerCase;
    public void setKey(String key) {
        this.key = key;
    }
    public void setToLowerCase(boolean toLowerCase) {
        this.toLowerCase = toLowerCase;
    }
    Logger logger = Logger.getLogger(LangOut.class);
    public int doStartTag(){
    JspWriter out = pageContext.getOut();
    try {
        String str="locale_";
        if((String)pageContext.getSession().getAttribute("lang")==null){
            str+="en";
        } else
            str="locale_"+(String)pageContext.getSession().getAttribute("lang");
        ResourceBundle bundle= ResourceBundle.getBundle(str);
        String localeMessage = bundle.getString(key);
        if(toLowerCase)
            localeMessage= localeMessage.toLowerCase();
        out.print(localeMessage);
        return TagSupport.EVAL_BODY_INCLUDE;
    }catch (Throwable e ){
        String message ="Couldn't out a locale string : "+key;
        logger.info(message,e);
        throw new RuntimeException(message,e);
    }
}


}
