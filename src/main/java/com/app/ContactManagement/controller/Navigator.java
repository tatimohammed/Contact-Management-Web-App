package com.app.ContactManagement.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.app.ContactManagement.repository.UserRepository;
import com.app.ContactManagement.service.ContactServiceImpl;
import com.app.ContactManagement.service.GroupServiceImpl;
import com.app.ContactManagement.service.MyUserDetails;
import com.app.ContactManagement.utils.AddContactToGroup;
import com.app.ContactManagement.utils.DeleteGroup;

@Controller
public class Navigator {

	@Autowired
	private ContactServiceImpl contactServiceImpl;
	
	@Autowired
	private ContactGroupRepository contactGroupRepository;

	private int count;

	@Autowired
	private GroupServiceImpl groupServiceImpl;

	@Autowired
	private LoginCounterRepository loginCounterRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TrashRepository trashRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private GroupRepository groupRepository;

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
		List<Contact> contacts = contactServiceImpl.findByUserId(userDetails.getUser());

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
		Contact c = contactRepository.findByid(Long.parseLong(id));
		List<ContactGroup> cg = contactGroupRepository.findBycontact(c);
		for(ContactGroup con : cg) {
			con.setContact(null);
		}
		contactGroupRepository.flush();
		Trash t = new Trash();
		t.setFirst_name(c.getFirst_name());
		t.setLastName(c.getLast_name());
		t.setAddress(c.getAddress());
		t.setEmail_personal(c.getEmail_personal());
		t.setEmail_professional(c.getEmail_professional());
		t.setGender(c.getGender());
		t.setUserId(c.getUserId().getId());
		t.setPhone1(c.getPhone1());
		t.setPhone2(c.getPhone2());

		trashRepository.save(t);
		contactRepository.delete(c);
		contactRepository.flush();
		trashRepository.flush();

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
		// Regex for Last Name
		String lastNameRegex = "^[a-zA-Z]*$";
		Pattern lastNamePattern = Pattern.compile(lastNameRegex);
		// Matcher for Last Name
		Matcher lastNameMatcher = lastNamePattern.matcher(query);

		// Regex for phone number
		String phoneRegex = "^[0-9]*$";
		Pattern phonePattern = Pattern.compile(phoneRegex);
		// Matcher for Last Name
		Matcher phoneMatcher = phonePattern.matcher(query);

		List<Contact> contacts = null;
		if (lastNameMatcher.matches()) {
			contacts = contactRepository.findByUserIdAndLastNameContainingOrderByLastNameAsc(userDetails.getUser(),
					query);
		} else if (phoneMatcher.matches()) {
			List<Contact> contacts1 = contactRepository.findByUserIdAndPhone1ContainingOrderByLastNameAsc(userDetails.getUser(),
					query);
			
			List<Contact> contacts2 = contactRepository.findByUserIdAndPhone2ContainingOrderByLastNameAsc(userDetails.getUser(),
					query);
			contacts = new ArrayList<>(contacts1);
			contacts.addAll(contacts2);
		} 

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
		
		List<Contact> cs = new ArrayList<>();
		for (ContactGroup c : contacts) {
			cs.add(c.getContact());
		}
		model.addAttribute("groupContacts", cs);
		return "groupsPage";
	}

	@GetMapping("/restore")
	public String restore(@RequestParam(name = "trashId") String trashId, Model model) {
		Trash t = trashRepository.findByid(Long.parseLong(trashId));

		Contact c = new Contact();
		c.setAddress(t.getAddress());
		c.setEmail_personal(t.getEmail_personal());
		c.setEmail_professional(t.getEmail_professional());
		c.setFirst_name(t.getFirst_name());
		c.setGender(t.getGender());
		c.setLast_name(t.getLastName());
		c.setPhone1(t.getPhone1());
		c.setPhone2(t.getPhone2());
		c.setUserId(userRepository.findByid(t.getUserId()));

		contactRepository.save(c);
		trashRepository.delete(t);
		contactRepository.flush();
		trashRepository.flush();

		return "redirect:/delete";
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
