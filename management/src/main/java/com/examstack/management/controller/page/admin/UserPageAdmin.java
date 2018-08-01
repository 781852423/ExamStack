package com.examstack.management.controller.page.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examstack.common.domain.question.Field;
import com.examstack.common.domain.question.Group2Field;
import com.examstack.common.domain.user.Department;
import com.examstack.common.domain.user.Group;
import com.examstack.common.domain.user.User;
import com.examstack.common.util.Page;
import com.examstack.common.util.PagingUtil;
import com.examstack.management.security.UserInfo;
import com.examstack.management.service.QuestionService;
import com.examstack.management.service.UserService;

@Controller
public class UserPageAdmin {

	@Autowired
	private UserService userService;
	@Autowired
	private QuestionService questionService;
	/**
	 * 学员管理
	 * @return
	 */
	@RequestMapping(value = { "admin/user/student-list" }, method = RequestMethod.GET)
	public String userListPage(Model model, HttpServletRequest request) {

		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int index = 1;
		if (request.getParameter("page") != null)
			index = Integer.parseInt(request.getParameter("page"));
		Page<User> page = new Page<User>();
		page.setPageNo(index);
		page.setPageSize(50);
		int groupId = 0;
		String searchStr = "";
		if(request.getParameter("groupId") != null){
			groupId = Integer.parseInt(request.getParameter("groupId"));
			searchStr = request.getParameter("searchStr");
		}
		List<Department> depList = userService.getDepList(null);
		List<User> userList = userService.getUserListByGroupIdAndParams(groupId, "ROLE_STUDENT", searchStr, page);
		String pageStr = PagingUtil.getPagelink(index, page.getTotalPage(), "", "admin/user-list");
		List<Group> groupList = userService.getGroupListByUserId(userInfo.getUserid(), null);
		model.addAttribute("depList", depList);
		model.addAttribute("groupId", groupId);
		model.addAttribute("userList", userList);
		model.addAttribute("pageStr", pageStr);
		model.addAttribute("groupList", groupList);
		return "user-list";
	}
	
	/**
	 * 考试历史
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "admin/user/student-examhistory" }, method = RequestMethod.GET)
	public String examHistoryPage(Model model, HttpServletRequest request){
		return "";
	}
	
	/**
	 * 学员资料
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "admin/user/student-profile" }, method = RequestMethod.GET)
	public String studentProfilePage(Model model, HttpServletRequest request){
		return "";
	}
	
	/**
	 * 教师管理
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "admin/user/teacher-list" }, method = RequestMethod.GET)
	public String teacherListPage(Model model, HttpServletRequest request) {

		int index = 1;
		if (request.getParameter("page") != null)
			index = Integer.parseInt(request.getParameter("page"));
		Page<User> page = new Page<User>();
		page.setPageNo(index);
		page.setPageSize(50);
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Department> depList = userService.getDepList(null);
		List<User> userList = userService.getUserListByRoleId(userInfo.getRoleMap().get("ROLE_TEACHER").getRoleId(), page);
		String pageStr = PagingUtil.getPagelink(index, page.getTotalPage(), "", "admin/user/teacher-list");
		model.addAttribute("depList", depList);
		model.addAttribute("userList", userList);
		model.addAttribute("pageStr", pageStr);
		return "admin/teacher-list";
	}
	
	@RequestMapping(value = { "admin/user/group2field" }, method = RequestMethod.GET)
	public String group2fieldPage(Model model, HttpServletRequest request) {

		int index = 1;
		if (request.getParameter("page") != null)
			index = Integer.parseInt(request.getParameter("page"));
		Page<User> page = new Page<User>();
		page.setPageNo(index);
		page.setPageSize(50);
		
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<Group> groupList = userService.getAllGroups(null);
		List<Field> fieldList = questionService.getAllField(null);
		
		String pageStr = PagingUtil.getPagelink(index, page.getTotalPage(), "", "admin/user/group2field");
		
		model.addAttribute("groupList", groupList);
		model.addAttribute("fieldList", fieldList);
		model.addAttribute("pageStr", pageStr);
		
		return "admin/group2field-list";
	}
	
	
	@RequestMapping(value = { "admin/user/inner/group2field-list/{groupId}" }, method = RequestMethod.GET)
	public String showGroup2FieldListInnerAdminPage(Model model, HttpServletRequest request, @PathVariable Integer groupId) {
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int index = 1;
		if (request.getParameter("page") != null)
			index = Integer.parseInt(request.getParameter("page"));
		Page<User> page = new Page<User>();
		page.setPageNo(index);
		page.setPageSize(50);

		List<Group2Field> user2GroupList = userService.getGroup2FieldById(groupId);
		List<Group> groupList = userService.getAllGroups(null);
		String pageStr = PagingUtil.getPagelink(index, page.getTotalPage(), null, "admin/user/inner/group2field/" + groupId);
		
		List<Field> fieldList = questionService.getAllField(null);
		
		model.addAttribute("user2GroupList", user2GroupList);
		model.addAttribute("groupList", groupList);
		model.addAttribute("pageStr", pageStr);
		model.addAttribute("fieldList", fieldList);
		
		model.addAttribute("groupId", groupId);


		return "inner/group2field-list";
	}
	
	/**
	 * 教师资料
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "admin/user/teacher-profile" }, method = RequestMethod.GET)
	public String teacherProfilePage(Model model, HttpServletRequest request){
		return "";
	}
	
	@RequestMapping(value = { "admin/user/inner/user-list/{groupId}-{authority}" }, method = RequestMethod.GET)
	public String showUserListInnerAdminPage(Model model, HttpServletRequest request, @PathVariable Integer groupId, @PathVariable String authority) {
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int index = 1;
		if (request.getParameter("page") != null)
			index = Integer.parseInt(request.getParameter("page"));
		Page<User> page = new Page<User>();
		page.setPageNo(index);
		page.setPageSize(50);
		String searchStr = "";
		if(request.getParameter("searchStr") != null){
			searchStr = request.getParameter("searchStr");
		}
		List<User> userList = userService.getUserListByGroupIdAndParams(groupId, authority, searchStr, page);
		String pageStr = PagingUtil.getPagelink(index, page.getTotalPage(), "&searchStr=" + searchStr, "admin/user/inner/user-list/" + groupId + "-" + authority);
		
		List<Group> groupList = userService.getGroupListByUserId(userInfo.getUserid(), null);
		model.addAttribute("userList", userList);
		model.addAttribute("pageStr", pageStr);
		model.addAttribute("groupList", groupList);
		model.addAttribute("groupId", groupId);
		model.addAttribute("authority", "ROLE_STUDENT");
		model.addAttribute("searchStr", searchStr);
		return "inner/user-list";
	}

	/**
	 * 添加用户界面
	 * 暂时保留，可能废弃
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "admin/user/add-teacher", method = RequestMethod.GET)
	private String addUserPage(Model model, HttpServletRequest request) {

		List<Field> fieldList = questionService.getAllField(null);
		model.addAttribute("fieldList", fieldList);
		return "admin/add-user";
	}
	
	@RequestMapping(value = { "logout" }, method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
		    if (auth != null){      
		        new SecurityContextLogoutHandler().logout(request, response, auth);  
		    }  
		return "login";
	}
	
	
}
