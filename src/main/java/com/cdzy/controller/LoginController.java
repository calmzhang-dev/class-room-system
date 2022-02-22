package com.cdzy.controller;

import com.cdzy.pojo.Classroom;
import com.cdzy.pojo.Lecture;
import com.cdzy.pojo.Student;
import com.cdzy.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;


@CrossOrigin
@Controller
@RequestMapping("/Home")
public class LoginController {

    @Autowired
    private LoginService loginService;

    //登录
    @RequestMapping("/Login")
    @ResponseBody
    public String login(String name,String password){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String,Object> allInfo = new HashMap<String,Object>();
            //学生信息
            Student student2 = loginService.login(name,password);
            System.out.println("有人访问："+student2);
            Student student = new Student(student2.getId(),student2.getName(),student2.getState(),student2.getPicture());
            //教室信息
            ArrayList<Classroom> list = loginService.allClassRoom();
            allInfo.put("studentInfo",student);
            allInfo.put("classInfo",list);

            return objectMapper.writeValueAsString(allInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return "登录失败";
        }
    }

    //注册
    @RequestMapping("/Registered")
    @ResponseBody
    public String registered(String name,String password){
        try {
            int rs = loginService.registered(name, password, null);
            System.out.println(rs);
            if(rs>0){
                ObjectMapper objectMapper = new ObjectMapper();
                HashMap<String,Object> allInfo = new HashMap<String,Object>();
                //学生信息
                Student student2 = loginService.userQuery(name,password);
                System.out.println(student2);
                Student student = new Student(student2.getId(),student2.getName(),student2.getState(),student2.getPicture());
                //教室信息
                ArrayList<Classroom> list = loginService.allClassRoom();
                allInfo.put("studentInfo",student);
                allInfo.put("classInfo",list);
                System.out.println(allInfo);
                return objectMapper.writeValueAsString(allInfo);

            }

        }catch (Exception e){
            return "注册失败";
        }
        return "注册失败";
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(String name){
        try {
            loginService.deleteUser(name);
            System.out.println("注销");
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString("注销");
        }catch (Exception e){
            return "注销失败";
        }
    }

    //修改个人信息
    @RequestMapping("/ModifyStudent")
    @ResponseBody
    public String ModifyStudent(int id, String name, String password,String picture){

        try {
            loginService.modifyStudent(id,name,password,picture);
            Student student2 = loginService.login( name,password);
            Student student = new Student(student2.getId(),student2.getName(),student2.getState(),student2.getPicture());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(student);
        }catch (Exception e){
            return "修改失败";
        }
    }

    //预约教室
    @RequestMapping("/OrderClassroom")
    @ResponseBody
    public String OrderClassroom(int id,String classRoomName){
        loginService.orderClassroom(id,classRoomName);
        //学生信息
        Student student2 = loginService.studentInfo(id);
        Student student = new Student(student2.getId(),student2.getName(),student2.getState(),student2.getPicture());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(student);
        } catch (Exception e) {
            e.printStackTrace();
            return "预约失败";
        }
    }

    //修改教室
    @RequestMapping("/ModifyClassroom")
    @ResponseBody
    public String modifyClassroom(int id,String classRoomName){
        ObjectMapper objectMapper = new ObjectMapper();
        loginService.modifyClassroom(id,classRoomName);
        //教室信息
        ArrayList<Classroom> list = loginService.allClassRoom();
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
            return "修改失败";
        }
    }

    //新增教室
    @RequestMapping("/AddClassroom")
    @ResponseBody
    public String addClassroom(String classRoomName){
        ObjectMapper objectMapper = new ObjectMapper();
        loginService.addClassroom(classRoomName);
        //教室信息
        ArrayList<Classroom> list = loginService.allClassRoom();
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
            return "新增失败";
        }
    }

    @RequestMapping("/addleature")
    @ResponseBody
    public String addleature(String lectureinfo, String classroomname){
        try {
            loginService.addleature(lectureinfo, classroomname);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString("添加讲座成功");
        }catch (Exception e){
            return "添加讲座失败";
        }
    }

    @RequestMapping("/updateleature")
    @ResponseBody
    public String updateleature(String lectureinfo, String classroomname){
        try {
            loginService.updateleature(lectureinfo, classroomname);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString("修改讲座成功");
        }catch (Exception e){
            return "修改讲座失败";
        }
    }

    @RequestMapping("/deleteleature")
    @ResponseBody
    public String deleteleature(String lectureinfo){
        try {
            loginService.deleteleature(lectureinfo);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString("删除讲座成功");
        }catch (Exception e){
            return "删除讲座失败";
        }
    }

    @RequestMapping("/selectlecture")
    @ResponseBody
    public String selectlecture() {
        try {
            ArrayList<Lecture> lecture2 = loginService.selectlecture();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(lecture2);
        }catch (Exception e){
            return "查询失败";
        }
    }
}
