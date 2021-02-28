/**
 * 
 * @author Arashdeep Singh
 * @date February 2021
 * @version 1.0
 */
package my.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import my.beans.Team;
import my.database.DatabaseAccess;

@Controller
public class TeamController {
	
	@Autowired
	private DatabaseAccess da;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@PostMapping("/addTeam")
	public String addTeam(Model model,@ModelAttribute Team team) {
		String continent;
		switch(team.getContinent()) {
			case "1":
				continent="Asia";
				break;
			case "2":
				continent="North America";
				break;
			case "3":
				continent="South America";
				break;
			case "4":
				continent="Africa";
				break;
			case "5":
				continent="Australia";
				break;
			case "6":
				continent="Europe";
				break;
			case "7":
				continent="Antartica";
				break;
			default:
				continent="N.A.";
				break;
		}
		team.setContinent(continent);
		da.addTeam(team);
		model.addAttribute("team",new Team());
		return "addTeam";
	}
	
	@GetMapping("/editTeamById/{id}")
	public String editTeamById(Model model,@PathVariable int id) {
		Team team = da.getTeamById(id).get(0);
		model.addAttribute("team",team);
		return "modify";
	}
	
	@PostMapping("/modifyTeam")
	public String modifyTeam(Model model, @ModelAttribute Team team) {
		String continent;
		switch(team.getContinent()) {
			case "1":
				continent="Asia";
				break;
			case "2":
				continent="North America";
				break;
			case "3":
				continent="South America";
				break;
			case "4":
				continent="Africa";
				break;
			case "5":
				continent="Australia";
				break;
			case "6":
				continent="Europe";
				break;
			case "7":
				continent="Antartica";
				break;
			default:
				continent="N.A.";
				break;
		}
		team.setContinent(continent);
		da.editTeam(team);
		model.addAttribute("teamList",da.getTeams());
		return "editTeam";
	}

	@GetMapping("/deleteTeamById/{id}")
	public String deleteTeamById(Model model, @PathVariable int id) {
		model.addAttribute("id", id);
		model.addAttribute("TeamID",id);
		return "deleteConfirmation";
	}
	
	@PostMapping("/deleteConfirmation/{num}")
	public String deleteConfirmation(Model model, @PathVariable int num,@RequestParam int id) {
		if(num==2) {
			da.deleteTeamById(id);
		}
		model.addAttribute("teamList",da.getTeams());
		return "deleteTeam";
	}
	
	@PostMapping("/searchTeamByContinent/{num}")
	public String searchTeamByContinent(Model model,@RequestParam String continent,@PathVariable String num) {
		String continent0;
		switch(continent) {
			case "1":
				continent0="Asia";
				break;
			case "2":
				continent0="North America";
				break;
			case "3":
				continent0="South America";
				break;
			case "4":
				continent0="Africa";
				break;
			case "5":
				continent0="Australia";
				break;
			case "6":
				continent0="Europe";
				break;
			case "7":
				continent0="Antartica";
				break;
			default:
				continent0="N.A.";
				break;
		}
		model.addAttribute("teamList",da.getTeamsByContinent("%"+continent0+"%"));
		if(num.equals("1")) {
			return "editTeam";
		}
		else if(num.equals("2")) {
			return "deleteTeam";
		}
		else {
			return "error404";
		}
	}
	
	@PostMapping("/searchTeamByCountry/{num}")
	public String searchTeamByCountry(Model model,@RequestParam String country,@PathVariable String num) {
		String country0=country.substring(0,1).toUpperCase() + country.substring(1);
		model.addAttribute("teamList",da.getTeamsByCountry(country0+"%"));
		if(num.equals("1")) {
			return "editTeam";
		}
		else if(num.equals("2")) {
			return "deleteTeam";
		}
		else {
			return "error404";
		}
	}
	
	@PostMapping("/sortResults")
	public String sortResults(Model model,@RequestParam String sort) {
		int type=Integer.parseInt(sort);
		model.addAttribute("teamList",da.getTeamsInOrder(type));
		return "displayResults";
	}
	
	//Anchor list
	@RequestMapping("/homeView")
	public String homeView() {
	    return "home";
	}
	@RequestMapping("/addTeamView")
	public String addTeamView(Model model) {
		model.addAttribute("team", new Team());
	    return "addTeam";
	}
	@RequestMapping("/editTeamView")
	public String editTeamView(Model model) {
		model.addAttribute("teamList",da.getTeams());
	    return "editTeam";
	}
	@RequestMapping("/deleteTeamView")
	public String deleteTeamView(Model model) {
		model.addAttribute("teamList",da.getTeams());
	    return "deleteTeam";
	}
	@RequestMapping("/displayResultsView")
	public String displayResultsView(Model model) {
	    model.addAttribute("teamList",da.getTeams());
		
		return "displayResults";
	}
}
