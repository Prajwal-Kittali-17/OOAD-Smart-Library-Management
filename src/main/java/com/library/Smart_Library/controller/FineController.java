package com.library.Smart_Library.controller;

import com.library.Smart_Library.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Owner: Pranav S
 * SRN: NOT-PROVIDED
 * Purpose: Finance module controller for fine report requests.
 * GRASP: Controller - accepts UI event and delegates fine logic to FineService.
 * Pattern: Singleton service collaborator from Spring container.
 */
@Controller
public class FineController {

  @Autowired
  private FineService fineService;

  /**
   * Loads the overdue fine report page.
   * GRASP: Controller by preparing presentation data without business math.
   */
  @GetMapping("/fines")
  public String viewFines(Model model) {
    model.addAttribute("fines", fineService.getTrackedFines());
    model.addAttribute("dailyRate", 10);
    model.addAttribute("currentDate", java.time.LocalDate.now());
    return "view-fines";
  }

  /**
   * Updates fine status through the UI to complete finance tracking lifecycle.
   */
  @PostMapping("/fines/{fineId}/pay")
  public String markFinePaid(@PathVariable Long fineId, RedirectAttributes redirectAttributes) {
    boolean paid = fineService.markFineAsPaid(fineId);
    if (paid) {
      redirectAttributes.addFlashAttribute("fineMessage", "Fine marked as PAID.");
    } else {
      redirectAttributes.addFlashAttribute("fineError", "Unable to update fine status.");
    }
    return "redirect:/fines";
  }
}
