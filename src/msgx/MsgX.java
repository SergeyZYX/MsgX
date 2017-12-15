package msgx;

import java.awt.*;
import java.awt.datatransfer.*;

/**
 *
 * @author SergeyZYX
 */
public class MsgX {
    
    public static enum MODE {MSG_ENCODE, MSG_DECODE, FILE_ENCODE, FILE_DECODE, NONE};
    
    
public static void main(String[] args)
{
    try {

    String initVector = null;
    String secretKey = null;
    String message = null;
    String messageProcessed = null;
    String messageCharset = "UTF-8"; // default
    String file = null;
    MODE mode = MODE.NONE;

    if (args == null || args.length == 0) {
	
	System.out.println("MsgX v0.01 (15.12.2017) by SergeyZYX");
	System.out.println("------------------------------------");
	System.out.println("Syntax: e/d/ef/df iv=$initVector sk=$secretKey [msg=$message] [chars=UTF-8] [file=$filepath]");
	System.out.println("e - encrypt message");
	System.out.println("d - decrypt message");
	System.out.println("ef - encrypt file to message");
	System.out.println("df - decrypt message to file");
	return;
    }
    
    String cmdInitVector = "iv=";
    String cmdSecretKey = "sk=";
    String cmdMessage = "msg=";
    String cmdMessageCharset = "chars=";
    String cmdFile = "file=";
    String cmdEncode = "e";
    String cmdEncodeFile = "ef";
    String cmdDecode = "d";
    String cmdDecodeFile = "df";

    for (String arg : args) {
	if (arg.equalsIgnoreCase(cmdEncode)) mode = MODE.MSG_ENCODE; else
	if (arg.equalsIgnoreCase(cmdEncodeFile)) mode = MODE.FILE_ENCODE; else
	if (arg.equalsIgnoreCase(cmdDecode)) mode = MODE.MSG_DECODE; else
	if (arg.equalsIgnoreCase(cmdDecodeFile)) mode = MODE.FILE_DECODE; else
	if (arg.startsWith(cmdInitVector)) initVector = arg.substring(cmdInitVector.length()); else
	if (arg.startsWith(cmdSecretKey)) secretKey = arg.substring(cmdSecretKey.length()); else
	if (arg.startsWith(cmdMessage)) message = arg.substring(cmdMessage.length()); else
	if (arg.startsWith(cmdMessageCharset)) messageCharset = arg.substring(cmdMessageCharset.length()); else
	if (arg.startsWith(cmdFile)) file = arg.substring(cmdFile.length()); else
	{
	    System.out.println("unknown command : "+arg);
	    return;
	}
    }
	
    if (mode == MODE.NONE) throw new RuntimeException("Undefined mode!");
    if (initVector == null || initVector.length() == 0) throw new RuntimeException("IV is empty!");
    if (secretKey == null || secretKey.length() == 0) throw new RuntimeException("SK is empty!");
    
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    
    if (mode != MODE.FILE_ENCODE && message == null) {
	
	message = (String) clipboard.getData(DataFlavor.stringFlavor);
	
	// fix last /n from excel
	if (message != null && message.length() > 1 && message.endsWith("\n")) {
	    
	    message = message.substring(0, message.length()-1);
	}
    }
    
    if (mode != MODE.FILE_ENCODE && (message == null || message.length() == 0)) throw new RuntimeException("MSG is empty!");

    if ((mode == MODE.FILE_ENCODE || mode == MODE.FILE_DECODE) &&
	(file == null || file.length() == 0)) throw new RuntimeException("File undefined!");
    
    switch (mode) {
	
	case MSG_ENCODE  : messageProcessed = MessageX.encryptString(message, secretKey, initVector, messageCharset); break;
	case MSG_DECODE  : messageProcessed = MessageX.decryptString(message, secretKey, initVector, messageCharset); break;
	case FILE_ENCODE : message = "file -> "+file; 
			   messageProcessed = MessageX.encryptFile(file, secretKey, initVector); break;
	case FILE_DECODE : messageProcessed = "file -> "+file;
			   MessageX.decryptFile(file, message, secretKey, initVector); break;
    }

    System.out.println("Message.IN : "+message);
    System.out.println("Message.OUT: "+messageProcessed);
    
    if (mode != MODE.FILE_DECODE) {
	
	StringSelection selection = new StringSelection(messageProcessed);
	clipboard.setContents(selection, selection);
    }
    
    }
    catch (Exception ex) { ex.printStackTrace(); }
}

}
