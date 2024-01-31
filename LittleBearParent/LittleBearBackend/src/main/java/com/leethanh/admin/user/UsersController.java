package com.leethanh.admin.user;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.leethanh.admin.FileUploadUtil;
import com.leethanh.common.entity.Roles;
import com.leethanh.common.entity.Users;

@Controller
public class UsersController {

	@Autowired
	private UsersService usersService;

	@GetMapping("/users")
	public void listAll(Model model) {
		listUsersByPage(1, model, "id", "asc");
	}

	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Roles> listRoles = usersService.listRoles();
		Users user = new Users();
		user.setEnabled(true);
		System.out.println("user id == null ?" + (user.getId() == null));
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create New User");
		return "user_form";
	}

	@PostMapping("/users/save")
	public String saveUser(Users user, @RequestParam("image") MultipartFile multipartFile) throws IOException {

		System.out.println("multipartfile is empty?" + multipartFile.isEmpty());
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			System.out.println("photo name" + fileName);
			user.setPhotos(fileName);

			Users savedUser = usersService.save(user);
			String uploadDir = "user-photos/" + savedUser.getId();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		} else {
			System.out.println("user photo is empty ? :" + user.getPhotos().isEmpty());
			if (user.getPhotos().isEmpty()) {
				user.setPhotos(null);
			}

			Users savedUser = usersService.save(user);

		}

		return "redirect:/users";
	}

	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try {
			Users users = usersService.getUserById(id);
			List<Roles> listRoles = usersService.listRoles();

			model.addAttribute("user", users);
			model.addAttribute("id", id);

			model.addAttribute("listRoles", listRoles);
			model.addAttribute("pageTitle", "Edit User (ID - " + id + ")");
			return "user_form";
		} catch (UserNotFoundException e) {
			// TODO: handle exception
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/users";
		}

	}

	@GetMapping("/users/page/{pageNum}")
	public String listUsersByPage(@PathVariable("pageNum") int pageNum, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir) {
		if (pageNum <= 0) {
			pageNum = 1;
		}
		int lastPage = Math.round((float) (usersService.listAll().size() / (float) usersService.USER_PER_PAGE));
		if (pageNum > lastPage) {
			pageNum = lastPage;
		}
		Page<Users> page = usersService.listUsersByPage(pageNum, sortField, sortDir);
		List<Users> listUsers = page.getContent();

		int startCount = (pageNum - 1) * usersService.USER_PER_PAGE + 1;
		int endCount = startCount + usersService.USER_PER_PAGE - 1;
		int totalItem = usersService.listAll().size();

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

		if (endCount > totalItem) {
			endCount = totalItem;
		}
		System.out.println("reverse sort dir is :"+reverseSortDir);
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItem", totalItem);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);

		return "users";

	}
}
