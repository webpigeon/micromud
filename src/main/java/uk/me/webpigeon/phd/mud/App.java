package uk.me.webpigeon.phd.mud;

import uk.me.webpigeon.phd.mud.netty.TelnetServer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	
    	
    	TelnetServer telnet = new TelnetServer(1337);
    	telnet.run();
    	
        System.out.println( "Hello World!" );
    }
}
