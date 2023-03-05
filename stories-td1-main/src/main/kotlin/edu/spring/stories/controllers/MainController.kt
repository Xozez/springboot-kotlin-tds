package edu.spring.stories.controllers

import edu.spring.stories.entities.Developer
import edu.spring.stories.repositories.DeveloperRepository
import edu.spring.stories.repositories.StoryRepository
import edu.spring.stories.repositories.TagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView

@Controller
class MainController {

    @Autowired
    lateinit var developerRepository: DeveloperRepository;

    @Autowired
    lateinit var storyRepository: StoryRepository;

    @Autowired
    lateinit var tagRepository: TagRepository;


    @RequestMapping(path=["/",""])
    fun indexAction(): String {
        return "partials/index"
    }

    @PostMapping("/developer/add")
    fun masterAddAction(@RequestParam firstname: String, @RequestParam lastname: String) : RedirectView  {
        var developer = Developer(firstname,lastname);
        developerRepository.save(developer);

        return RedirectView("/");
    }
}