package ca.sheridancollege.controllers;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Mission;
import ca.sheridancollege.database.DatabaseAccess;

/**
 * This is a Spring Controller. The controller annotation ensures that the
 * spring framework recognize this class as "Something it must manage". 
 * 
 * @author asra.k
 *
 */
@Controller
public class HomeController {
	private DatabaseAccess database;

	/**
	 * for dependency injection one arg constructor.
	 * @param database will be injected at runtime by Spring
	 */
	public HomeController(DatabaseAccess database) {
		this.database = database;
	}
	
	// a thread-safe arraylist.
	public CopyOnWriteArrayList<Mission> missionsList = new CopyOnWriteArrayList<>();

	/**
	 * This method will be used by the framework whenever there is a request for the
	 * root of the web site.
	 * @param model Model object supplied by MVC
	 * @param mission an instance of model that can be used to send data back and
	 *                forward .html
	 * @return index an html file marked with thymeleaf
	 */
	@GetMapping("/")
	public String goHome(Model model) {
		model.addAttribute("mission", new Mission());
		// System.out.print(mission);
		return "index";
	}

	/**
	 * Method used by the frame work when their is a request for create mission.
	 * @param model Model object supplied by MVC.
	 * @param mission a special instance with the values user entered.
	 * @return create_mission form page to create mission.
	 */
	@GetMapping("/addMission")
	public String createMission(Model model) {
		model.addAttribute("mission", new Mission());
		 model.addAttribute("missionsList", missionsList);
		return "create_mission";
	}
	
	/**
	 * this page is requested by the frame work when a mission is 
	 * created to add a new mission.
	 * @param model Model object passed by MVC
	 * @param mission
	 * @return view_misisons. mission created can be viewed
	 */
	@PostMapping("/createMission")
	public String addMission(Model model, @ModelAttribute Mission mission ,@RequestParam String agent) {
		missionsList.add(mission);
		database.addMission(mission);
		model.addAttribute("agent", agent);
		model.addAttribute("missionsList", database.getMissionsAgent(mission.getAgent()));
		return "view_missions";
	}

	
	

	/**
	 * this page is requested by the framework when a request
	 * for view mission for an agent is requested
	 * @param model model Model object passed by MVC
	 * @param agent Agent selected 
	 * @return view_mission  
	 */
	@GetMapping("/viewMission")
	public String viewMissions(Model model, @RequestParam String agent,@ModelAttribute Mission mission) {
		model.addAttribute("agent",mission.getAgent());
		model.addAttribute("missionsList", database.getMissionsAgent(mission.getAgent()));
		return "view_missions";

	}

	/**
	 * this will show the updated list of missions
	 * @param model it will update the mission 
	 * @param mission it will update
	 * @return returns view_missions. where update list
	 */
	@PostMapping("/updateMission")
	public String updateMission( Model model,@ModelAttribute Mission mission) {	

		int returnValue = database.updateMission(mission);
		System.out.println("agent in the update is "+ mission.getAgent());
		System.out.println(returnValue);
		model.addAttribute("missionsList", database.getMissionsAgent(mission.getAgent()));
		model.addAttribute("agent",mission.getAgent());
		return "view_missions";
	}

	/**
	 * the details of the mission  created previously can be edited.
	 * @param id it will be the last element in the URL path.
	 * @param model it will store mission here for binding 
	 * @return return edit_mission .
	 */
	@GetMapping("/editMission/{id}")
	public String editMission(@PathVariable Long id, Model model) {
		String agent = database.getMissions(id).getAgent();
		model.addAttribute("agent", agent);
		Mission mission = database.getMissions(id);
		model.addAttribute("mission", mission);
		return "edit_mission";
	}
	
	/**
	 * Mission that was created previously can be deleted.
	 * @param id id it will be the last element in the URL path.
	 * @param model  it will delete the mission 
	 * @return view_mission. it will shows all the updated mission.
	 */
	@GetMapping("/deleteMission/{id}")
	public String deleteMission(@PathVariable Long id, Model model) {
		String agent = database.getMissions(id).getAgent();
		database.deleteMission(id);
		model.addAttribute("missionsList", database.getMissionsAgent(agent));
		model.addAttribute("agent",agent);
		return "view_missions";

	}

}
