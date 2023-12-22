package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.example.Tree;
import org.example.TreeService;

import java.util.List;

@Controller
public class TreeController {

    private final TreeService treeService;

    @Autowired
    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/enter-numbers";
    }

    @GetMapping("/enter-numbers")
    public String enterNumbers() {
        return "enterNumbers";
    }

    @PostMapping("/process-numbers")
    public ModelAndView processNumbers(@RequestParam List<Integer> numbers) {
        Tree savedTree = treeService.saveTree(numbers);
        ModelAndView modelAndView = new ModelAndView("processNumbers");
        modelAndView.addObject("tree", savedTree);
        return modelAndView;
    }

    @GetMapping("/previous-trees")
    public ModelAndView getPreviousTrees() {
        List<Tree> trees = treeService.getAllTreesSortedByNewest();
        ModelAndView modelAndView = new ModelAndView("previousTrees");
        modelAndView.addObject("trees", trees);
        return modelAndView;
    }
}