package org.demo.client.module.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String viewAdmin() {
        return "admin/admin";
    }
}
