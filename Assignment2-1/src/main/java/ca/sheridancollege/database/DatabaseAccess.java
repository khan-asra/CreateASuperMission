package ca.sheridancollege.database;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.Mission;

@Repository
public class DatabaseAccess {
	
	private NamedParameterJdbcTemplate jdbc;
	
	
	public DatabaseAccess(NamedParameterJdbcTemplate jdbc) {
		this.jdbc=jdbc;
	}
	
	/**
	 * Add an mission to database
	 * @param mission : mission to add
	 * @return the number of rows affected; 1 successful and 0 not successful 
	 */
	public int addMission(Mission mission) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO missions (title, agent, gadget1, gadget2) "
				+ "VALUES (:title, :agent, :gadget1, :gadget2)";
		namedParameters
		.addValue("title", mission.getTitle())
		.addValue("agent", mission.getAgent())
		.addValue("gadget1", mission.getGadget1())
		.addValue("gadget2", mission.getGadget2());
		int returnValue =jdbc.update(query, namedParameters);
		 return returnValue;
	}
	/**
	 * This method selects the mission by agent 
	 * @param agent selected agent
	 * @return list of mission by selected agent 
	 */
	public List<Mission> getMissionsAgent(String agent) {
		MapSqlParameterSource namedParameter = new MapSqlParameterSource();
		String query ="SELECT * from missions where agent = :agent";
		namedParameter.addValue("agent", agent);
		// will map a row coming in to an instance of Mission
		BeanPropertyRowMapper<Mission> missionMapper= new BeanPropertyRowMapper<>(Mission.class);
	  return jdbc.query(query,namedParameter, missionMapper);
	
	}
	
	 /* Select mission by ID from the database
	 * @param id is used and to select the mission 
	 * @return return the mission 
		 */	  
	public Mission getMissions(Long id) {
		MapSqlParameterSource namedParameter = new MapSqlParameterSource();
		String query ="SELECT * from missions where id = :id";
		namedParameter.addValue("id", id);
		BeanPropertyRowMapper<Mission> missionMapper= new BeanPropertyRowMapper<>(Mission.class);
		List<Mission> missions=jdbc.query(query,namedParameter, missionMapper);
		
		if(missions.isEmpty()) {
			return null;
		}
		else
			 return missions.get(0);
	}
		 
	/**
	 * This method deletes the mission from the database 
	 * @param id : id of the Mission to be deleted 
	 * @return the number of rows affected.
	 */

	public int deleteMission(Long id) {
		
		MapSqlParameterSource namedParameter = new MapSqlParameterSource();
		String query ="Delete From missions WHERE id = :id ";
		namedParameter.addValue("id", id);
		int returnValue=jdbc.update(query, namedParameter);
		System.out.print(returnValue);
		return returnValue;
	}

	/**
	 * This method update/edit the mission. 
	 * @param  mission that we need to edit
	 * @return The number of row affected 
	 */
	public int updateMission(Mission mission) {
			MapSqlParameterSource namedParameters = new MapSqlParameterSource();
			String query = "UPDATE missions SET title = :title, gadget1 = :gadget1 , gadget2 =:gadget2 " 
					+ " WHERE id = :id";
					
			namedParameters
			.addValue("title", mission.getTitle())
			.addValue("gadget1", mission.getGadget1())
			.addValue("gadget2", mission.getGadget2())
			.addValue("id", mission.getId());
			
			int returnValue =jdbc.update(query, namedParameters);
			return returnValue;	
	}
	

		
		
	
	
	

}
