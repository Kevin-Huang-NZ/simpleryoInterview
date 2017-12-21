package co.nz.simpleryo.shop.web.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.nz.simpleryo.shop.domain.model.User;
import co.nz.simpleryo.shop.domain.service.UserService;
import co.nz.simpleryo.shop.web.common.ResponseResult;
import co.nz.simpleryo.shop.web.common.RestResultGenerator;

@RestController
@RequestMapping("/api/user")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@GetMapping(value= {"/{id}"})
	public ResponseResult<User> getByKey(@PathVariable("id") Long id) {
		User user = this.userService.selectByKey(id);
		return RestResultGenerator.genResult(user, "");
	}

}
