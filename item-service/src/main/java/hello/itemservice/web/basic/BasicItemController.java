package hello.itemservice.web.basic;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findbyId(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addform";
    }

//    @PostMapping("/add")
//    public String save(@RequestParam String itemName,
//                       @RequestParam int price,
//                       @RequestParam Integer quantity,
//                       Model model) {
//
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//        itemRepository.save(item);
//
//        model.addAttribute("item", item);
//
//        return "basic/item";
//    }

//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
//
////        Item item = new Item();
////        item.setItemName(itemName);
////        item.setPrice(price);
////        item.setQuantity(quantity);
////        @ModelAttribute가 이거 위에 다 해줌. @RequestParam 안써도 가능.
//
//        itemRepository.save(item);
//
////        model.addAttribute("item", item);  얘도 ModelAttribute가 자동으로 알아서 해줘서 필요없음.
//
//        return "basic/item";
//    }

//  @PostMapping("/add")
//    public String addItemV3(@ModelAttribute Item item, Model model) {
//
//        itemRepository.save(item);
//
////        model.addAttribute("item", item);  얘도 ModelAttribute가 자동으로 알아서 해줘서 필요없음.
//
//        return "basic/item";
//    }

//    @PostMapping("/add")
//    public String addItem(Item item) {
//        itemRepository.save(item);
//        return "redirect:/basic/items/" + item.getId();
//    }

    @PostMapping("/add")
    public String addItem(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addFlashAttribute("itemId",savedItem.getId());
        redirectAttributes.addFlashAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findbyId(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, Model model, @ModelAttribute Item item) {
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";
    }

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }

}
