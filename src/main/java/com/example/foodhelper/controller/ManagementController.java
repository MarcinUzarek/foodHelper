package com.example.foodhelper.controller;

import com.example.foodhelper.model.dto.AccountsAdministrationDTO;
import com.example.foodhelper.service.AccountsAdministrationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
@PreAuthorize("hasAuthority('ADMIN')")
public class ManagementController {

    private final AccountsAdministrationService accountsAdministrationService;

    public ManagementController(AccountsAdministrationService accountsAdministrationService) {
        this.accountsAdministrationService = accountsAdministrationService;
    }

    @GetMapping("/accounts-management")
    public String manageAccounts(Model model) {

        var accounts = accountsAdministrationService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "admin-page";
    }

    @GetMapping("/change-active/{email}")
    public String changeIsAccountActive(@PathVariable String email) {
        accountsAdministrationService.changeIsAccountActive(email);
        return "redirect:/accounts-management";
    }

    @GetMapping("/promote/{email}")
    public String promoteAccount(@PathVariable String email) {
        accountsAdministrationService.promoteAccount(email);
        return "redirect:/accounts-management";
    }

    @GetMapping("/demote/{email}")
    public String demoteAccount(@PathVariable String email) {
        accountsAdministrationService.demoteAccount(email);
        return "redirect:/accounts-management";
    }

    @GetMapping("/delete-account/{email}")
    public String deleteAccount(@PathVariable String email) {
        accountsAdministrationService.deleteAccount(email);
        return "redirect:/accounts-management";
    }
}
