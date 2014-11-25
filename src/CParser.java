import net.anei.cadpage.parsers.*;

public class CParser {
	public static void main(String[] args) {

		// Globals
		net.anei.cadpage.parsers.Message msg;
		net.anei.cadpage.parsers.MsgParser mp=null;
		String page = new String();
		String subject= new String("");
		
		// Make sure we have at least one argument (the parser ID).  
		if(args.length < 1) {
			System.out.println("Please specify a parser ID");
			System.exit(1);
		}
		
		// Parser ID shouldn't be null
		if(args[0].length()==0) {
			System.out.println("Please specify a parser ID");
			System.exit(1);		
		}
		
		// Check for subject
		if(args.length > 1) {
			subject=args[1];
		}		

		// Get the raw page from STDIN.
		try{
			String line;
			java.io.BufferedReader stdin = new java.io.BufferedReader( new java.io.InputStreamReader(System.in));
			do {
				line=stdin.readLine();
				if(line != null) {
				
					line=line.trim();
					if(!line.equals(".")) {
						page+=line+"\n";
						//System.out.println(":"+line + ":");
					}
				} 

			} while(line != null && !line.equals("."));
		} catch (java.io.IOException e) {
			System.out.println("Caught exception while reading input");
			System.out.println(e);
		}

//System.out.println("Parsing message("+subject+"): "+page );

		// Create a new Message and try to get the parser
		msg = new net.anei.cadpage.parsers.Message(true, "", subject, page);
		try {
			mp = net.anei.cadpage.parsers.ManageParsers.getInstance().getParser(args[0]);
		} catch(Exception e) {
			System.out.println("Unable to get parser.");
			System.exit(1);
		}
		
		// Check to make sure MsgParser is OK with this message
		if(!mp.isPageMsg(msg, mp.PARSE_FLG_GEN_ALERT | mp.PARSE_FLG_SKIP_FILTER | mp.PARSE_FLG_POSITIVE_ID)) {	//mp.PARSE_FLG_POSITIVE_ID 
			System.out.println("Parser does not detect message.");
			System.exit(1);
		}
		
		
		String parsed = net.anei.cadpage.parsers.CadpageParser.formatInfo(msg.getInfo(), "\n", true);
		System.out.println(parsed);
		System.exit(0);
		
	}
}
