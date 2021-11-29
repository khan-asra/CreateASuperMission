 package ca.sheridancollege.beans;



import lombok.Data;


/**
 * This is the Mission class.
 * with private data members. id, title, gadget1 and gadget2 and list of agents.
 * @author asra.k
 *
 */
@Data
public class Mission {
	
	private  int id;
	private String title;
	private String gadget1;
	private String gadget2;
	private String agent;
	private final String[] agents= {"Johnny English","Natasha Romanova","Austin Powers"};

}
