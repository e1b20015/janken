package oit.is.z0088.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z0088.kaizi.janken.model.Entry;
import oit.is.z0088.kaizi.janken.model.User;
import oit.is.z0088.kaizi.janken.model.UserMapper;
import oit.is.z0088.kaizi.janken.model.Match;
import oit.is.z0088.kaizi.janken.model.MatchMapper;
import oit.is.z0088.kaizi.janken.model.MatchInfo;
import oit.is.z0088.kaizi.janken.model.MatchInfoMapper;

/**
 * Sample21Controller
 *
 * クラスの前に@Controllerをつけていると，HTTPリクエスト（GET/POSTなど）があったときに，このクラスが呼び出される
 */
@Controller
public class JankenController {

  @Autowired
  private Entry entry;

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchinfoMapper;

  @GetMapping("/janken/{param1}")
  public String janken(@PathVariable String param1, ModelMap model) {
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

    ArrayList<Match> matches = matchMapper.selectAllMatches();
    model.addAttribute("matches", matches);
    return "janken.html";
  }

  @GetMapping("/janken")
  public String entryJanken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);
    model.addAttribute("username", loginUser);

    ArrayList<User> users = userMapper.selectAllUsers();
    model.addAttribute("users", users);
    ArrayList<Match> matches = matchMapper.selectAllMatches();
    model.addAttribute("matches", matches);

    return "janken.html";
  }

  @PostMapping("/janken")
  public String jankenpost(@RequestParam String username, ModelMap model) {
    model.addAttribute("username", username);
    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam Integer id, ModelMap model, Principal prin) {
    String loginUser = prin.getName();
    User aite = userMapper.selectById(id);

    model.addAttribute("aite", aite);
    model.addAttribute("username", loginUser);

    return "match.html";
  }

  @GetMapping("/fight")
  public String matchJanken(@RequestParam Integer param1, ModelMap model, Principal prin) {
    // グー：１，チョキ：２，パー：３
    String hand = null, result = null;
    if (param1 == 1) {
      hand = "Gu";
      result = "draw...";
    } else if (param1 == 2) {
      hand = "Choki";
      result = "You Lose...";
    } else if (param1 == 3) {
      hand = "Pa";
      result = "You Win!";
    }
    // 使いまわしのプログラムから抜き出してinsertしたい
    // ここで値を登録するとthymeleafが受け取り，htmlで処理することができるようになる
    model.addAttribute("hand", hand);
    model.addAttribute("result", result);
    User jibun = userMapper.selectByName(prin.getName());
    Match match = new Match();
    match.setUser1(jibun.getId());
    match.setUser2(1);// CPU
    match.setUser1Hand(hand);
    match.setUser2Hand("Gu");// CPUの手
    matchMapper.insertMatches(match);

    ArrayList<Match> matches = matchMapper.selectAllMatches();
    model.addAttribute("matches", matches);

    return "match.html";

  }

  @GetMapping("/wait")
  public String waitJanken(@RequestParam Integer param1, @RequestParam Integer id, ModelMap model, Principal prin) {
    // グー：１，チョキ：２，パー：３
    String te = null;
    if (param1 == 1) {
      te = "Gu";
    } else if (param1 == 2) {
      te = "Choki";
    } else if (param1 == 3) {
      te = "Pa";
    }

    User jibun = userMapper.selectByName(prin.getName());
    MatchInfo info = new MatchInfo();
    info.setUser1(jibun.getId());
    info.setUser2(id);// CPU
    info.setUser1Hand(te);
    info.setIsActive(true);
    // match.setUser2Hand("Gu");// matchinfoにはいらない

    // autowiredの存在を忘れていた．
    matchinfoMapper.insertMatchInfo(info);

    ArrayList<Match> matchinfo = matchinfoMapper.selectAllByMatchInfo();
    model.addAttribute("matchinfo", matchinfo);
    model.addAttribute("username", prin.getName());

    return "wait.html";

  }

}
