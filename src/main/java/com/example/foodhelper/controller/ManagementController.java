package com.example.foodhelper.controller;

import com.example.foodhelper.service.ManagementService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@PreAuthorize("hasAuthority('ADMIN')")
public class ManagementController {

    private final ManagementService accountsAdministrationService;

    public ManagementController(ManagementService accountsAdministrationService) {
        this.accountsAdministrationService = accountsAdministrationService;
    }

    @GetMapping("/accounts-management")
    public String manageAccounts(Model model) {

        var accounts = accountsAdministrationService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "admin-page";
    }

    @GetMapping("/change-active/{id}")
    public String changeIsAccountActive(@PathVariable Long id) {
        accountsAdministrationService.changeIsAccountActive(id);
        return "redirect:/accounts-management";
    }

    @GetMapping("/promote/{id}")
    public String promoteAccount(@PathVariable Long id) {
        accountsAdministrationService.promoteAccount(id);
        return "redirect:/accounts-management";
    }

    @GetMapping("/demote/{id}")
    public String demoteAccount(@PathVariable Long id) {
        accountsAdministrationService.demoteAccount(id);
        return "redirect:/accounts-management";
    }

    @GetMapping("/delete-account/{id}")
    public String deleteAccount(@PathVariable Long id) {
        accountsAdministrationService.deleteAccount(id);
        return "redirect:/accounts-management";
    }
}
