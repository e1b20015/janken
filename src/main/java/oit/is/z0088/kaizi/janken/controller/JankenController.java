package oit.is.z0088.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Sample21Controller
 *
 * クラスの前に@Controllerをつけていると，HTTPリクエスト（GET/POSTなど）があったときに，このクラスが呼び出される
 */
@Controller
public class JankenController {

  /**
   * sample21というGETリクエストがあったら sample21()を呼び出し，sample21.htmlを返す
   */
  @GetMapping("/janken")
  public String sample21() {
    return "janken.html";
  }

  @PostMapping("/janken")
  public String jankenpost(@RequestParam String name, ModelMap model) {
    String username = name;
    model.addAttribute("username", username);
    return "janken.html";
  }

  @GetMapping("/rock/{param1}")
  public String rock(@PathVariable String param1, ModelMap model) {
    String hand = param1;
    model.addAttribute("hand", hand);
    return "janken.html";
  }

  @GetMapping("/scissor/{param1}")
  public String scissor(@PathVariable String param1, ModelMap model) {
    String hand = param1;
    model.addAttribute("hand", hand);
    return "janken.html";
  }

  @GetMapping("/paper/{param1}")
  public String paper(@PathVariable String param1, ModelMap model) {
    String hand = param1;
    model.addAttribute("hand", hand);
    return "janken.html";
  }

}
