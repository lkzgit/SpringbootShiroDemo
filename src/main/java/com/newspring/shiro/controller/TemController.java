package com.newspring.shiro.controller;

import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Controller
public class TemController {

    @RequestMapping("/toindex")
    public String index(org.springframework.ui.Model model) {
        model.addAttribute("name", "springboot");
        return "/index";
    }

    @RequestMapping("/toadd")
    public String add() {
        return "user/add";
    }

    @RequestMapping("/toupdate")
    public String update() {
        return "user/update";
    }

    @RequestMapping(value = "/findFile", method = RequestMethod.GET)
    public void findFile(HttpServletRequest request, HttpServletResponse response) {

        ServletOutputStream out = null;
        FileInputStream ips = null;

        try {
            java.io.File file = ResourceUtils.getFile("classpath:templates/text.txt");
            if (!file.exists()) {
                System.out.println("文件不存在");
                return;
            }
            String name = file.getName();
            String newName = UUID.randomUUID().toString() + name;
            System.out.println(newName);
            ips = new FileInputStream(file);
            response.setContentType("multipart/form-data");
            //为文件重新设置名字，采用数据库内存储的文件名称
            response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(newName.getBytes("UTF-8"), "ISO8859-1") + "\"");
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (Exception o) {
            o.printStackTrace();
            System.out.println("IO出现异常");
        } finally {
            try {
                out.close();
                ips.close();
            } catch (IOException o) {
                System.out.println("关闭异常");
                o.fillInStackTrace();
            }
        }
    }


}
