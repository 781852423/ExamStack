package com.examstack.portal.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.examstack.common.domain.user.User;
import com.examstack.portal.persistence.UserMapper;

@Component("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserInfo userInfo;
	@Autowired
	public UserMapper userMapper;
		
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user = null;
		try {
			user = userMapper.getUserByName(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(user == null)
			throw new UsernameNotFoundException("user not found!");
		//roles=角色代码
		List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles());
		// groups=所处的组，代表客户报考的单位
		String groupsStr = user.getGroups();
		List<Integer> groupsList = new ArrayList<Integer>();
		
		if(groupsStr != null && groupsStr.trim() != "")
		{
			for(String s : groupsStr.split(","))
			{
				if(s != null && s != "")
				{
					try
					{
						groupsList.add(Integer.getInteger(s));
						
					}catch (Exception e) {
						
						e.printStackTrace();
					}
				}
			}
		}
		userInfo = new UserInfo(username,user.getPassword(),user.isEnabled(),true,true,true,authorities);
		userInfo.setGroupList(groupsList);	
		userInfo.setUserid(user.getUserId());
		userInfo.setRolesName(user.getRoles());
		userInfo.setTrueName(user.getTrueName());
		userInfo.setEmail(user.getEmail());
		userInfo.setPhoneNum(user.getPhoneNum());
		userInfo.setNationalId(user.getNationalId());
		userInfo.setDepId(user.getDepId());
		userInfo.setExpiredTime(user.getExpiredTime());
		return userInfo;
	}
}
