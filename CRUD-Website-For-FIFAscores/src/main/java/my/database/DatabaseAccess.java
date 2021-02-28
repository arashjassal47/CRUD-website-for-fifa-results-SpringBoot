package my.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import my.beans.Team;

@Repository
public class DatabaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;	
	
	public void addTeam(Team team) {
		MapSqlParameterSource namedParameters= new MapSqlParameterSource();
		namedParameters.addValue("id",team.getTeamID());
		namedParameters.addValue("flag",team.getTeamFlag());
		namedParameters.addValue("name",team.getTeamName().substring(0,1).toUpperCase()+team.getTeamName().substring(1));
		namedParameters.addValue("continent",team.getContinent());
		namedParameters.addValue("played",team.getPlayed());
		namedParameters.addValue("won",team.getWon());
		namedParameters.addValue("drawn",team.getDrawn());
		namedParameters.addValue("lost",team.getLost());
		
		String query="INSERT INTO Teams(TeamFlag,TeamName,Continent,Played,Won,Drawn,Lost) VALUES "
				+ "(:flag,:name,:continent,:played,:won,:drawn,:lost)";
		int rowsAffected= jdbc.update(query, namedParameters);
		if (rowsAffected> 0) 
			System.out.println("Inserted Team into database.");
	}
	
	
	public void deleteTeamById(int id) {
		MapSqlParameterSource namedParameters= new MapSqlParameterSource();
		namedParameters.addValue("id", id);
		String query="DELETE FROM Teams WHERE TeamID=:id";
		int rowsAffected= jdbc.update(query, namedParameters);
		if (rowsAffected> 0) 
			System.out.println("Team deleted from the database.");
	}
	
	
	public void editTeam(Team team) {
		MapSqlParameterSource namedParameters= new MapSqlParameterSource();
		namedParameters.addValue("id",team.getTeamID());
		namedParameters.addValue("flag",team.getTeamFlag());
		namedParameters.addValue("name",team.getTeamName().substring(0,1).toUpperCase()+team.getTeamName().substring(1));
		namedParameters.addValue("continent",team.getContinent());
		namedParameters.addValue("played",team.getPlayed());
		namedParameters.addValue("won",team.getWon());
		namedParameters.addValue("drawn",team.getDrawn());
		namedParameters.addValue("lost",team.getLost());		
		String query="UPDATE Teams SET TeamFlag=:flag, TeamName=:name, Continent=:continent, Played=:played, Won=:won, Drawn=:drawn, Lost=:lost  WHERE TeamID=:id";
		int rowsAffected= jdbc.update(query, namedParameters);
		if (rowsAffected> 0) 
			System.out.println("Updated Team in database.");
	}
	
	
	public List<Team> getTeams(){
		MapSqlParameterSource namedParameters= new  MapSqlParameterSource();
	  	String query = "SELECT * FROM Teams";
	  	return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Team>(Team.class));
	}
	
	public List<Team> getTeamsInOrder(int num){
		MapSqlParameterSource namedParameters= new  MapSqlParameterSource();
		String query;
		if(num==1) {  query = "SELECT * FROM Teams ORDER BY TeamName";}
		else if(num==2) {  query = "SELECT * FROM Teams ORDER BY Continent";}
		else if(num==3) {  query = "SELECT * FROM Teams ORDER BY Won DESC";}
		else{ query = "SELECT * FROM Teams";}
	  	
	  	return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Team>(Team.class));
	}
	
	
	public List<Team> getTeamById(int id){
		MapSqlParameterSource namedParameters= new  MapSqlParameterSource();
	  	namedParameters.addValue("id", id);
	  	String query = "SELECT * FROM Teams WHERE TeamID = :id";
	  	return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Team>(Team.class));
	}
	
	
	public List<Team> getTeamsByContinent(String contid){
		MapSqlParameterSource namedParameters= new  MapSqlParameterSource();
	  	namedParameters.addValue("id", contid);
	  	String query = "SELECT * FROM Teams WHERE Continent LIKE :id";
	  	return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Team>(Team.class));
	}
	
	public List<Team> getTeamsByCountry(String country){
		MapSqlParameterSource namedParameters= new  MapSqlParameterSource();
	  	namedParameters.addValue("country", country);
	  	String query = "SELECT * FROM Teams WHERE TeamName LIKE :country";
	  	return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Team>(Team.class));
	}
}
