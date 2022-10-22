package oit.is.z0088.kaizi.janken.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z0088.kaizi.janken.model.Entry;

/**
 * Sample21Controller
 *
 * クラスの前に@Controllerをつけていると，HTTPリクエスト（GET/POSTなど）があったときに，このクラスが呼び出される
 */
@Controller
public class JankenController {

  @Autowired
  private Entry entry;

  /*
   * @GetMapping("/janken")
   * public String sample21() {
   * return "janken.html";
   * }
   */

  @PostMapping("/janken")
  public String jankenpost(@RequestParam String name, ModelMap model) {
    String username = name;
    model.addAttribute("username", username);
    return "janken.html";
  }

  @GetMapping("/janken1/{param1}")
  public String rock(@PathVariable String param1, ModelMap model) {
    int i = Integer.parseInt(param1);
    String hand = null, result = null;

    if (i == 1) {
      hand = "Gu";
      result = "draw";
    } else if (i == 2) {
      hand = "Choki";
      result = "You Lose";
    } else if (i == 3) {
      hand = "Pa";
      result = "You Win!";
    }
    model.addAttribute("hand", hand);
    model.addAttribute("result", result);

    return "janken.html";
  }

  @GetMapping("/janken")
  public String entryJanken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);

    return "janken.html";
  }

}
