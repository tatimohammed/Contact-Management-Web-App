package com.app.ContactManagement.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.ContactManagement.model.Contact;
import com.app.ContactManagement.model.ContactGroup;
import com.app.ContactManagement.model.Groupe;
import com.app.ContactManagement.model.LoginCounter;
import com.app.ContactManagement.model.Trash;
import com.app.ContactManagement.repository.ContactGroupRepository;
import com.app.ContactManagement.repository.ContactRepository;
import com.app.ContactManagement.repository.GroupRepository;
import com.app.ContactManagement.repository.LoginCounterRepository;
import com.app.ContactManagement.repository.TrashRepository;
import com.app.ContactManagement.service.ContactServiceImpl;
import com.app.ContactManagement.service.GroupServiceImpl;
import com.app.ContactManagement.service.MyUserDetails;
import com.app.ContactManagement.utils.AddContactToGroup;
import com.app.ContactManagement.utils.DeleteGroup;

@Controller
public class Navigator {

	@Autowired
	private ContactServiceImpl contactServiceImpl;
	
	private Model modelGlobal;
	
	@Autowired
	private ContactGroupRepository contactGroupRepository;

	private int count;

	@Autowired
	private GroupServiceImpl groupServiceImpl;

	@Autowired
	private LoginCounterRepository loginCounterRepository;

	@Autowired
	private TrashRepository trashRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private GroupRepository groupRepository;

	
	public Model getModelGlobal() {
		return modelGlobal;
	}

	@GetMapping("/dropContact")
	public String dropContact(@RequestParam(name="contactId") String id) {
		
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		String groupSelectedId = (String) modelGlobal.getAttribute("groupSelectedId");
		Contact c = contactRepository.findByid(Long.parseLong(id));
		Groupe g = groupRepository.findByid(Long.parseLong(groupSelectedId));
		ContactGroup cg = contactGroupRepository.findBygroupeAndUserAndContact(g, userDetails.getUser(), c);
		
		contactGroupRepository.delete(cg);
		contactGroupRepository.flush();
		
		return "redirect:/group?groupId=" + groupSelectedId;
	}
	
	public void drawChart(MyUserDetails userDetails, Model model) {
		// Chart Data
		List<LoginCounter> logins = loginCounterRepository.findByUserOrderByDateAsc(userDetails.getUser());

		List<String> labels = new ArrayList<>();
		List<Integer> dataPoints = new ArrayList<>();

		for (LoginCounter login : logins) {
			labels.add(login.getDate().toString());
			System.out.println(login.getDate().toString());
			dataPoints.add(login.getCount());
		}
		// Getting the last week labels
		int lastIndexOflabels = labels.size() - 1;
		int startIndexOflabels = Math.max(0, lastIndexOflabels - 6); // Get the starting index

		List<String> lastWeekLabels = labels.subList(startIndexOflabels, lastIndexOflabels + 1);

		// Getting the last week data points
		int lastIndexOfdataPoints = dataPoints.size() - 1;
		int startIndexOfdataPoints = Math.max(0, lastIndexOflabels - 6); // Get the starting index

		List<Integer> lastWeekdataPoints = dataPoints.subList(startIndexOfdataPoints, lastIndexOfdataPoints + 1);

		model.addAttribute("labels", lastWeekLabels);
		model.addAttribute("dataPoints", lastWeekdataPoints);
	}

	public void showContacts(Model model) {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Set<Contact> contacts = contactServiceImpl.findByUserId(userDetails.getUser());

		String name = userDetails.getName();
		model.addAttribute("user", name);

		model.addAttribute("contacts", contacts);

		List<Groupe> groups = groupServiceImpl.listOfGroups(userDetails.getUser());

		model.addAttribute("groups", groups);

		model.addAttribute("addContactToGroup", new AddContactToGroup());
		model.addAttribute("DeleteGroup", new DeleteGroup());

		int number = contacts.size();
		model.addAttribute("contactTotal", number);

		int number1 = groups.size();
		model.addAttribute("groupTotal", number1);
	}

	@GetMapping("/")
	public String showWelcome() {
		return "welcomePage";
	}

	@GetMapping("/home")
	public String homePage(@RequestParam(name = "contactId", required = false, defaultValue = "NA") String contact,
			Model model, HttpSession session) {
		showContacts(model);
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		LocalDate currentDate = LocalDate.now();
		Date today = Date.valueOf(currentDate);
		LoginCounter counter = loginCounterRepository.findBydateAndUser(today, userDetails.getUser());
		if (session.getAttribute("loggedIn") == null) {
			session.setAttribute("loggedIn", true);
			if (counter == null) {
				this.count++;

				LoginCounter l = new LoginCounter();
				l.setCount(this.count);
				l.setDate(today);
				l.setUser(userDetails.getUser());
				loginCounterRepository.save(l);
			} else {
				counter.setCount(counter.getCount() + 1);
			}
		}
		loginCounterRepository.flush();

		if (contact.equals("NA")) {

			drawChart(userDetails, model);
			return "homePage";
		}

		Contact contactSelected = contactRepository.findByid(Long.parseLong(contact));
		model.addAttribute("contactSelected", contactSelected);
		drawChart(userDetails, model);
		return "homePage";
	}

	@GetMapping("/add")
	public String addContactPage(Model model) {
		showContacts(model);
		return "addContactPage";
	}

	@GetMapping("/delete")
	public String deleteContactPage(
			@RequestParam(name = "contactSelected", required = false, defaultValue = "NA") String id, Model model) {
		showContacts(model);

		if (id.equals("NA")) {
			MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			Long userId = userDetails.getUser().getId();

			List<Trash> trashs = trashRepository.findByuserId(userId);
			model.addAttribute("trashs", trashs);
			return "deleteContactPage";
		}
		System.out.println("Contact To delete: " + id);
		
		// Deleting a contact and save it in a Trash
		contactServiceImpl.deleteContact(id);

		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		Long userId = userDetails.getUser().getId();

		List<Trash> trashs = trashRepository.findByuserId(userId);
		model.addAttribute("trashs", trashs);

		return "deleteContactPage";
	}

	@GetMapping("/search")
	public String searchContactPage(@RequestParam(name = "query", required = false, defaultValue = "") String query,
			Model model) {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (query.equals("")) {
			showContacts(model);
			drawChart(userDetails, model);
			return "redirect:/home";
		}
		showContacts(model);
		drawChart(userDetails, model);

		Set<Contact> contacts = contactServiceImpl.searchContacts(query, userDetails.getUser()); 

		for (Contact c : contacts) {
			System.out.println(c.getLast_name());
		}
		model.addAttribute("queryresult", contacts);
		return "homePage";
	}

	@GetMapping("/group")
	public String groupContactPage(@RequestParam(name = "query", required = false, defaultValue = "") String query,
			@RequestParam(name = "groupId", required = false, defaultValue = "NA") String group,
			Model model) {
		
		modelGlobal = model;
		
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		showContacts(model);
		
		if (group.equals("NA")) {
			if(query.equals("")) {
				return "groupsPage";
			}
			List<Groupe> groups = groupRepository.findByUserAndNameContaining(userDetails.getUser(), query);
			
			model.addAttribute("queryGroups", groups);
			return "groupsPage";
		}
		
		List<Groupe> groups = groupRepository.findByUserAndNameContaining(userDetails.getUser(), query);
		
		model.addAttribute("queryGroups", groups);
		Groupe groupSelected = groupRepository.findByid(Long.parseLong(group));
		List<ContactGroup> contacts = contactGroupRepository.findBygroupeAndUser(groupSelected, userDetails.getUser());

		System.out.println(groupSelected.getName());
		model.addAttribute("groupname", groupSelected.getName());
		modelGlobal.addAttribute("groupSelectedId", groupSelected.getId().toString());
		
		List<Contact> cs = new ArrayList<>();
		for (ContactGroup c : contacts) {
			cs.add(c.getContact());
		}
		model.addAttribute("groupContacts", cs);
		return "groupsPage";
	}


	@GetMapping("/update")
	public String updateContactPage(
			@RequestParam(name = "contactSelected", required = false, defaultValue = "NA") String contact,
			Model model) {
		showContacts(model);
		if (contact.equals("NA")) {
			return "updateContactPage";
		}
		Contact contactSelected = contactRepository.findByid(Long.parseLong(contact));
		model.addAttribute("contactToUpdate", contactSelected);
		System.out.println("Contact Id: " + contact);
		model.addAttribute("contactIdToUpdate", contact);
		System.out.println("Getteddddddd Contact: " + (String) model.getAttribute("contactIdToUpdate"));
		return "updateContactPage";
	}

}
