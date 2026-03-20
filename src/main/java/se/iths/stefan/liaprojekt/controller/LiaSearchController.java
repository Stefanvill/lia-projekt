package se.iths.stefan.liaprojekt.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.iths.stefan.liaprojekt.model.LiaSearch;
import se.iths.stefan.liaprojekt.service.LiaSearchService;

@Controller
@RequestMapping("/lia")
public class LiaSearchController {
    private final LiaSearchService service;

    public LiaSearchController(LiaSearchService service) {
        this.service = service;
    }

    @GetMapping
    public String getAllPosts(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || "anonymousUser".equals(auth.getName())) {
            return "redirect:/login";
        }

        // Använd username istället för Long.parseLong
        String username = auth.getName();

        // Uppdatera din service-metod till att ta String username
        model.addAttribute("lia", service.getPostsByUsername(username));
        return "lia/lia";
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("liaSearch", new LiaSearch());
        return "lia/create-lia-post";
    }

    @PostMapping
    public String createSearch(@Valid @ModelAttribute LiaSearch liaSearch,
                               BindingResult bindingResult,
                               Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "lia/create-lia-post";
        }
        service.createSearch(liaSearch, authentication.getName());
        return "redirect:/lia";
    }

    @PostMapping("/{id}")
    public String updateLiaSearch(@PathVariable Long id,
                                  @ModelAttribute LiaSearch liaSearch,
                                  Authentication authentication) {
        service.updateSearch(id, liaSearch, authentication.getName());
        return "redirect:/lia";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable Long id,
                                 Model model,
                                 Authentication authentication) {
        LiaSearch liaSearch = service.getPost(id, authentication.getName());
        model.addAttribute("lia", liaSearch);
        return "lia/update-lia-post";
    }

    @GetMapping("/{id}")
    public String postDetail(@PathVariable Long id,
                             Model model,
                             Authentication authentication) {
        LiaSearch liaSearch = service.getPost(id, authentication.getName());
        model.addAttribute("lia", liaSearch);
        return "lia/post-detail";
    }

    @PostMapping("/{id}/delete")
    public String deleteLiaSearch(@PathVariable Long id, Authentication authentication) {
        service.deleteSearch(id, authentication.getName());
        return "redirect:/lia";
    }

}
