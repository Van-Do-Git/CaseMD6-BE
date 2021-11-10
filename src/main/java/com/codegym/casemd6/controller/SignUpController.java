package com.codegym.casemd6.controller;


import com.codegym.casemd6.dto.LoginAccount;
import com.codegym.casemd6.dto.MesageRespons;
import com.codegym.casemd6.model.Account;
import com.codegym.casemd6.model.AppRole;
import com.codegym.casemd6.model.Image;
import com.codegym.casemd6.service.account.IServiceAccount;
import com.codegym.casemd6.service.approle.IServiceAppRole;
import com.codegym.casemd6.service.image.IServiceImage;
import com.codegym.casemd6.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/account")
@EnableSpringDataWebSupport
public class SignUpController {
    @Autowired
    IServiceAccount serviceAccount;
    @Autowired
    IServiceAppRole serviceAppRole;
    @Autowired
    IServiceImage serviceImage;
    @Autowired
    JwtService jwtService;


    @PostMapping("/signup")
    public ResponseEntity<MesageRespons> createAcc(@Valid @RequestBody Account account) {
        AppRole role = serviceAppRole.findById(2L).get();
        Image image = serviceImage.findById(1L).get();
        account.setAvatar(image);
        account.setRole(role);
        MesageRespons mesage = new MesageRespons();
        if (serviceAccount.add(account)) {
            mesage.setMesage("ok");
        } else {
            mesage.setMesage("exited or password and re_password is not match!");
        }
        return new ResponseEntity<>(mesage, HttpStatus.OK);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        MesageRespons result = new MesageRespons();
        HttpStatus httpStatus = null;
        try {
            if (serviceAccount.checkLogin(account)) {
                String tokenLogin = jwtService.generateTokenLogin(account.getUsername());
                Account account1 = serviceAccount.loadUserByUserName(account.getUsername());
                LoginAccount loginAccount = new LoginAccount();
                loginAccount.setId(account1.getId());
                loginAccount.setFullName(account1.getFullName());
                loginAccount.setAvatar(account1.getAvatar());
                loginAccount.setToken(tokenLogin);
                return new ResponseEntity(loginAccount, HttpStatus.OK);
            } else {
                result.setMesage("Wrong email or password!");
                httpStatus = HttpStatus.OK;
            }
        } catch (Exception ex) {
            result.setMesage("Server Error");
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping("/addroleandimage")
    public ResponseEntity<MesageRespons> fixRoleAndDefaultAvatar() {
        List<AppRole> appRoles = (List<AppRole>) serviceAppRole.findAll();
        Image images = serviceImage.findById(1L).get();
        if (appRoles.size() == 0) {
            AppRole admin = new AppRole();
            admin.setId(1L);
            admin.setRole("ROLE_ADMIN");
            AppRole user = new AppRole();
            user.setRole("ROLE_USER");
            user.setId(2L);
            serviceAppRole.save(admin);
            serviceAppRole.save(user);
        }
        if (images != null) {
            images.setPath("https://1.bp.blogspot.com/-r8taaC_nv5U/XngOYFjbRVI/AAAAAAAAZnc/QjGkkHS78GMm6CocQ1OqrWGgQTkG1oQNACLcBGAsYHQ/s1600/Avatar-Facebook%2B%25281%2529.jpg");
            serviceImage.save(images);
        }
        MesageRespons mesage = new MesageRespons();
        mesage.setMesage("ok");
        return new ResponseEntity<>(mesage, HttpStatus.OK);
    }

}
