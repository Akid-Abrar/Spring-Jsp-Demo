package com.crud_login_register.spring_login_register_demo.controller;

import com.crud_login_register.spring_login_register_demo.constants.Constants;
import com.crud_login_register.spring_login_register_demo.controller.bean.ShowUser;
import com.crud_login_register.spring_login_register_demo.controller.bean.User;
import com.crud_login_register.spring_login_register_demo.controller.bean.UserRegister;
import com.crud_login_register.spring_login_register_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Part;
import javax.xml.ws.RequestWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login" , method = RequestMethod.GET)
    public String loginPage(){
        return "login";
    }

    @RequestMapping(value = "/login" , method = RequestMethod.POST)
    public String welcomePage(ModelMap modelMap, @RequestParam String username , @RequestParam String password){
        User user = userService.getUserByUserName(username);
        if(user == null)
        {
            modelMap.put("errorMsg","Please Enter Right Infos");
            return "login";
        }
        if(user.getPassword().equals(password))
        {
            if (user.getRoleId() == Constants.ROLE_EMPLOYEE) {
                //modelMap.put("user", user);
                return "redirect:/user/" + username;
            }

            return "redirect:/admin";
        }
        modelMap.put("errorMsg","Please Enter Right Infos");
        return "login";
    }

    @RequestMapping(value = "/user/{username}" , method = RequestMethod.GET)
    public String userPage(@PathVariable("username") String username ,ModelMap modelMap){
        User user = userService.getUserByUserName(username);
        modelMap.put("user", user);
        return "user";
    }

    @RequestMapping(value = "/admin" , method = RequestMethod.GET)
    public String adminPage(ModelMap modelMap){
        User user = userService.getUserByUserName("admin");
        List<User> usersList = userService.getAllUsers();
        modelMap.put("usersList", usersList);
        modelMap.put("username", user.getUserName());
        return "admin";
    }

    @RequestMapping(value = "/user" , method = RequestMethod.GET)
    public String userPage1(ModelMap modelMap ,@RequestParam String username){
        User user = userService.getUserByUserName(username);
        modelMap.put("user", user);
        return "user";
    }

    @RequestMapping(value = "/register" , method = RequestMethod.GET)
    public String registerPage(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") User user, ModelMap modelMap) throws IOException {
        InputStream inputStream = null;
        MultipartFile filepart = user.getFile();
        inputStream = filepart.getInputStream();
        if(inputStream != null) System.out.println("got file in controller");
        int count = userService.createNewUser(user , inputStream);
        if(count !=1){
            modelMap.put("errorMsg","Something Went Wrong");
            return "register";
        }
        modelMap.put("successMsg", "User created successfully!!");
        ShowUser showUser = userService.showUserDetails(user.getUserName());
        modelMap.put("showUser",showUser);
        return "confirmation";
    }

    @RequestMapping(value = "/update/{userName}" , method = RequestMethod.GET)
    public String showUpdatePage(@PathVariable String userName , Model model , ModelMap modelMap){
        User user = userService.getUserByUserName(userName);
        List<Integer> selected_certificate_ids = userService.getCertificateIds(user);
        model.addAttribute("user",user);

        modelMap.put("selected_certificate_ids",selected_certificate_ids);
        modelMap.put("usershow",user);
        return "update";
    }
    /*@RequestMapping(value = "/update/{userName}", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User user, ModelMap modelMap)
    {
        int count = userService.updateUser(user);
        if(count !=1){
            modelMap.put("errorMsg","Something Went Wrong");
            return "update";
        }
        System.out.println("before returning from post method");
        return "update";
        //return "redirect:/update/" + user.getUserName();
    }*/

    @RequestMapping(value = "/update/{userName}", method = RequestMethod.POST)
    public @ResponseBody User updateUser(@ModelAttribute("user") User user)
    {
        System.out.println("user got");
        System.out.println(user.toString());
        User oUser = new User();
        int count = userService.updateUser(user);
        if(count >0){
            oUser.setMessageCode(1);
            oUser.setMessage("Update Successfully");
            return oUser;
        }
        oUser.setMessageCode(0);
        oUser.setMessage("Update failed");
        return oUser;
    }


    @RequestMapping(value = "/delete/{userName}" , method = RequestMethod.GET)
    public String deleteuser(@PathVariable String userName)
    {
        User user = userService.getUserByUserName(userName);
        userService.deleteUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/getAllUsers")
    @ResponseBody
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @ModelAttribute("boardList")
    public Map<Integer, String> getBoardList() {
        Map<Integer, String> getBoardList = new HashMap<Integer, String>();

        getBoardList.put(1, "Dhaka");
        getBoardList.put(2, "Rajshahi");
        getBoardList.put(3, "Sylhet");
        return getBoardList;
    }

    @ModelAttribute("genderList")
    public Map<Integer, String> getGenderList() {
        Map<Integer, String> getGenderList = new HashMap<Integer, String>();
        getGenderList.put(1,"Male");
        getGenderList.put(2,"Female");
        getGenderList.put(3,"Other");
        return getGenderList;
    }

    @ModelAttribute("certificateList")
    public Map<Integer, String> getCertificateList() {
        Map<Integer, String> getCertificateList = new HashMap<Integer, String>();
        getCertificateList.put(1,"SSC");
        getCertificateList.put(2,"HSC");
        getCertificateList.put(3,"B.Sc");
        getCertificateList.put(4,"M.Sc");
        return getCertificateList;
    }


    //New task starts here

    @RequestMapping(value = "/userLogin" , method = RequestMethod.GET)
    public String userLoginPage(){
        return "userLogin";
    }

    @RequestMapping(value = "/userLogin" , method = RequestMethod.POST)
    public String userPage(ModelMap modelMap, @RequestParam String username , @RequestParam String password){
        UserRegister userRegister = userService.getRegisteredUserByUserName(username);
        if(userRegister == null)
        {
            modelMap.put("errorMsg","Please Enter Right Infos");
            return "userLogin";
        }
        if(userRegister.getPassword().equals(password))
        {
            return "userAdd";
        }
        modelMap.put("errorMsg","Please Enter Right Infos");
        return "userLogin";
    }

    @RequestMapping(value = "/userAdd" , method = RequestMethod.GET)
    public String userAdd() {
        return "userAdd";
    }

    @RequestMapping(value = "/insertRegisteredUser" , method = RequestMethod.POST)
    @ResponseBody
    public String saveRegisteredUser(@ModelAttribute("insertRegisteredUser") UserRegister userRegister)
    {
        userService.cerateNewRegisteredUser(userRegister);
        return "saved";
    }

    @GetMapping("/getAllRegisteredUser")
    public @ResponseBody List<UserRegister> getAllStudent()
    {
        List<UserRegister> ss=userService.getAllRegisteredUser();
        return ss;
    }

    @GetMapping("/getOneRegisteredUser/{id}")
    @ResponseBody
    public UserRegister getById(@PathVariable int id)
    {
        return userService.getById(id);
    }

    @GetMapping("/deleteRegisteredUser/{id}")
    @ResponseBody
    public String deleteStudent(@PathVariable int id)
    {
        userService.deleteRegistereduser(id);

        return "deleted";
    }

    @PostMapping("/updateRegisteredUser")
    @ResponseBody
    public String updateStudent(@ModelAttribute("updateRegisteredUser") UserRegister userRegister)
    {
        userService.updateRegisteredUser(userRegister);

        return "updated";
    }

}
